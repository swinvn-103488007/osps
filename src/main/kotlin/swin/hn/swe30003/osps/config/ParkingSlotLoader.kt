package swin.hn.swe30003.osps.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import swin.hn.swe30003.osps.entity.ParkingSlot
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.repository.ParkingAreaRepository
import swin.hn.swe30003.osps.repository.ParkingSlotRepository

@Configuration
class ParkingSlotLoader(
    private val parkingAreaRepository: ParkingAreaRepository,
    private val parkingSlotRepository: ParkingSlotRepository
) {

    // Note: create the amount of parking slots match the number of remaining slot for each parking area
    @Bean
    fun loadParkingSlots() = CommandLineRunner {
        parkingAreaRepository.findAll().forEach { area ->
            val slots = (1..50).map { slotNumber ->
                ParkingSlot(id = ParkingSlotId(area.id, slotNumber), parkingArea = area, isAvailable = true)
            }
            parkingSlotRepository.saveAll(slots)
        }
    }
}
