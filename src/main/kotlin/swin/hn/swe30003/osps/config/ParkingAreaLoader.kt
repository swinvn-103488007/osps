package swin.hn.swe30003.osps.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import swin.hn.swe30003.osps.entity.ParkingArea
import swin.hn.swe30003.osps.repository.ParkingAreaRepository

@Configuration
class ParkingAreaLoader(
    private val parkingAreaRepository: ParkingAreaRepository
) {

    // Note: load three default parking area of size 50 if not exist
    @Bean
    fun loadParkingAreas() = CommandLineRunner {
        val parkingAreas = listOf(
            ParkingArea(id = "A", size = 50),
            ParkingArea(id = "B", size = 50),
            ParkingArea(id = "C", size = 50)
        )

        parkingAreas.forEach { area ->
            if (!parkingAreaRepository.existsById(area.id)) {
                parkingAreaRepository.save(area)
            }
        }
    }
}
