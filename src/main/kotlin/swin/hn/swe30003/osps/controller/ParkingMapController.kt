package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swin.hn.swe30003.osps.exception_handler.commonErrorHandler
import swin.hn.swe30003.osps.exception_handler.commonExceptionHandler
import swin.hn.swe30003.osps.responses_data.ParkingAreaResponse
import swin.hn.swe30003.osps.responses_data.ParkingSlotResponse
import swin.hn.swe30003.osps.service.ParkingMapService
import java.util.PrimitiveIterator

@RestController
@RequestMapping("/parking-map")
class ParkingMapController(
    private val objectMapper: ObjectMapper,
    private val parkingMapService: ParkingMapService
) {
    @GetMapping("")
    fun getParkingMap(

    ): ResponseEntity<String> {
        return try {
            val allAreas = parkingMapService.getAllParkingArea()
            val responseBody = allAreas.map{area ->
                ParkingAreaResponse(
                    id = area.id,
                    slots = area.parkingSlots.map { slot ->
                        ParkingSlotResponse(
                            area = slot.id.parkingArea,
                            number = slot.id.number,
                            isAvailable = slot.isAvailable
                        )
                    }
                )
            }
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(responseBody))
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "/parking-map",objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "/parking-map",objectMapper)
        }
    }

    @GetMapping("/available-slots")
    fun getAvailableParkingSlots(
    ): ResponseEntity<String> {
        return try {
            val availableSlots = parkingMapService.getAvailableParkingSlots()
            val responseBody = availableSlots.map { slot -> {
                ParkingSlotResponse(
                    area = slot.id.parkingArea,
                    number = slot.id.number,
                    isAvailable = slot.isAvailable
                )
            } }
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(responseBody))
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "/parking-map/availableSlots",objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "/parking-map/availableSlots",objectMapper)
        }
    }
}