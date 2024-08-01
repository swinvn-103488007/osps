package swin.hn.swe30003.osps

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import swin.hn.swe30003.osps.entity.ParkingArea
import swin.hn.swe30003.osps.repository.ParkingAreaRepository

@SpringBootApplication
class OspsApplication {
	@Bean
	fun init(parkingAreaRepository: ParkingAreaRepository) = CommandLineRunner {
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

fun main(args: Array<String>) {
	runApplication<OspsApplication>(*args)
}
