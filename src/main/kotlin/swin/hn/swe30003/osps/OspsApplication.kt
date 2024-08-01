package swin.hn.swe30003.osps

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import swin.hn.swe30003.osps.entity.ParkingArea
import swin.hn.swe30003.osps.repository.ParkingAreaRepository

@SpringBootApplication
class OspsApplication
fun main(args: Array<String>) {
	runApplication<OspsApplication>(*args)
}
