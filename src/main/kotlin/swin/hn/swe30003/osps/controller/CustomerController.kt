package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.responses_data.CustomerResponseData
import swin.hn.swe30003.osps.responses_data.ErrorResponse
import swin.hn.swe30003.osps.responses_data.ReservationResponseData
import swin.hn.swe30003.osps.service.ReservationManagerService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val objectMapper: ObjectMapper,
    private val reservationManagerService: ReservationManagerService
) {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // Note: create reservation endpoint
    @PostMapping("/{userId}/reserve-parking-slot")
    fun reserveParkingSlot(
        @PathVariable("userId", required = true) userId: Long,
        @RequestParam("areaId") areaId: String,
        @RequestParam("slotNumber") slotNumber: Int
    ): ResponseEntity<String> {

        return try {
            val reservationTime = LocalDateTime.now()
            val reservation = reservationManagerService.createReservation(
                customerId=userId, areaId=areaId, parkingSlotNumber=slotNumber, time=reservationTime
            )
            reservationManagerService.saveReservation(reservation)
            val jsonString = """
                {
                    "message": "Successfully created reservation",
                    "details": {
                        "customer": ${reservation.customer.username},
                        "parkingSlot": ${reservation.parkingArea}${reservation.parkingSlotNumber},
                        "createdAt": ${reservation.createdAt},
                    },     
                }
            """.trimIndent()
            ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString)
        } catch (e: DateTimeParseException) {
            ResponseEntity("Wrong date time format: ${e.message}", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Exception: ${e.message}", HttpStatus.BAD_REQUEST)
        } catch (e: Error) {
            ResponseEntity("An unexpected error happened: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // Note: get all reservations of customer in a specific period
//    @GetMapping("/{userId}/reservations")
//    fun getHistoryReservation(
//        @PathVariable("userId", required = true) userId: Long,
//        @RequestParam("from") from: String,
//        @RequestParam("to") to: String
//    ): ResponseEntity<String> {
//        return try {
//            val fromDateTime = LocalDate.parse(from).atStartOfDay()
//            val toDateTime = LocalDate.parse(to).atStartOfDay()
//            val reservations = reservationManagerService.getReservationsByCustomerInPeriod(userId, fromDateTime, toDateTime)
//
////            val reservationsJsonStr = objectMapper.writeValueAsString(reservations)
//            val jsonString = """
//                {
//                    "customer": {
//                        "id": ${reservations[0].customer.id},
//                        "name": ${reservations[0].customer.username},
//                    },
//                    "from": $fromDateTime,
//                    "to: $toDateTime,
//                    "reservations": $reservationsJsonStr,
//                }
//            """.trimIndent()
//
//            ResponseEntity
//                .status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(jsonString)
//        } catch (e: DateTimeParseException) {
//            val jsonString = """{
//                "Error": "Wrong date time format exception: ${e.message}"
//            }
//            """.trimIndent()
//            ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(jsonString)
//        } catch (e: Exception) {
//            ResponseEntity("Exception: ${e.message}", HttpStatus.BAD_REQUEST)
//        } catch (e: Error) {
//            ResponseEntity("An unexpected error happened: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
//        }
//    }

    // Note: get a reservation of customer by reservation id
    @GetMapping("{userId}/reservation/{reservationId}")
    fun getReservationOfCustomerById(
        @PathVariable("userId", required = true) userId: Long,
        @PathVariable("reservationId", required = true) reservationId: Long
    ): ResponseEntity<String> {
        return try {
            val reservation = reservationManagerService.getReservationByIdAndCustomerId(userId, reservationId)
            if (reservation != null) {
                val response = ReservationResponseData(
                    id = reservation.id,
                    customer = CustomerResponseData(reservation.customer.id, reservation.customer.username),
                    parkingArea = reservation.parkingArea,
                    parkingSlotNumber = reservation.parkingSlotNumber,
                    createdAt = reservation.createdAt,
                    paidAt = reservation.paidAt,
                    returnSlotAt = reservation.returnSlotAt,
                )
                val jsonResponse = objectMapper.writeValueAsString(response)
                ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse)
            } else {
                val errResponse = ErrorResponse(
                    time = LocalDateTime.now(),
                    status = HttpStatus.NOT_FOUND.value(),
                    error = HttpStatus.NOT_FOUND.toString(),
                    message = "Cannot found reservation with given User ID and Reservation ID",
                    path = "customer/${userId}/reservation/${reservationId}"
                )
                val jsonResponse = objectMapper.writeValueAsString(errResponse)
                ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse)
            }
        } catch (e: Error) {
            val errResponse = ErrorResponse(
                time = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.toString(),
                message = "An unexpected error happen. Description ${e.message}",
                path = "customer/${userId}/reservation/${reservationId}"
            )
            val jsonResponse = objectMapper.writeValueAsString(errResponse)
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResponse)
        }
    }

    // Note: checkout a reservation
    @PutMapping("/{userId}/checkout-reservation/{reservationId}")
    fun checkoutReservation(
        @PathVariable("userId", required = true) userId: Long,
        @PathVariable("reservationId", required = true) reservationId: Long
    ): ResponseEntity<String> {
//        reservationManagerService.checkoutReservation()
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("body")
    }
}
