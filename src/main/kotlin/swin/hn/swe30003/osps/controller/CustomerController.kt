package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.PaymentMethod
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.entity.receipt.BankTransferReceipt
import swin.hn.swe30003.osps.entity.receipt.CashReceipt
import swin.hn.swe30003.osps.responses_data.UserResponseData
import swin.hn.swe30003.osps.responses_data.ReservationResponseData
import swin.hn.swe30003.osps.responses_data.receipt_response_datas.BankTransferReceiptResponseData
import swin.hn.swe30003.osps.responses_data.receipt_response_datas.CashReceiptResponseData
import swin.hn.swe30003.osps.service.CustomerService
import swin.hn.swe30003.osps.service.ReservationManagerService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import swin.hn.swe30003.osps.exception_handler.*

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = ["*"])
class CustomerController(
    private val objectMapper: ObjectMapper,
    private val reservationManagerService: ReservationManagerService,
    private val customerService: CustomerService
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
            val jsonString = """
                {
                    "message": "Successfully created reservation",
                    "details": {
                        "customer": "${reservation.customer.username}",
                        "parkingSlot": "${reservation.parkingArea}${reservation.parkingSlotNumber}",
                        "createdAt": "${reservation.createdAt}"
                    }
                }
            """.trimIndent()
            ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString)
        } catch (e: DateTimeParseException) {
            ResponseEntity("Wrong date time format: ${e.message}", HttpStatus.BAD_REQUEST)
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "customer/${userId}/reserve-parking-slot",objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "customer/${userId}/reserve-parking-slot",objectMapper)
        }
    }

    // Note: create reservation endpoint
    @PutMapping("/{userId}/checkout-reservation")
    fun checkoutParkingSlot(
        @PathVariable("userId", required = true) userId: Long,
        @Param("reservationId") reservationId: Long
    ): ResponseEntity<String> {

        return try {
            val reservation = reservationManagerService.getReservationByIdAndCustomerId(userId, reservationId) ?:
            throw Exception("You can not checkout reservation you didn't create")

            if (reservation.checkoutAt != null) {
                throw Exception("You checked out this reservation before")
            }

            val parkingSlotId = ParkingSlotId(reservation.parkingArea, reservation.parkingSlotNumber)
            reservationManagerService.releaseParkingSlot(parkingSlotId)
            reservationManagerService.updateReservationCheckoutTime(reservation)

            val jsonString = """
                {
                    "message": "Successfully checked out reservation",
                    "details": {
                        "customer": "${reservation.customer.username}",
                        "parkingSlot": "${reservation.parkingArea}${reservation.parkingSlotNumber}",
                        "checkoutAt": "${reservation.checkoutAt}"
                    }
                }
            """.trimIndent()
            ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString)
        } catch (e: DateTimeParseException) {
            ResponseEntity("Wrong date time format: ${e.message}", HttpStatus.BAD_REQUEST)
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "customer/${userId}/reserve-parking-slot",objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "customer/${userId}/reserve-parking-slot",objectMapper)
        }
    }

    // Note: get all reservations of customer in a specific period
    @GetMapping("/{userId}/reservations")
    fun getHistoryReservation(
        @PathVariable("userId", required = true) userId: Long,
        @RequestParam("from") from: String,
        @RequestParam("to") to: String
    ): ResponseEntity<String> {
        return try {
            val fromDateTime = LocalDateTime.parse(from)
            val toDateTime = LocalDateTime.parse(to)
            val reservations = reservationManagerService.getReservationsByCustomerInPeriod(userId, fromDateTime, toDateTime)

            val reservationsResponseData = reservations.map{ reservation ->
                ReservationResponseData(
                    id = reservation.id,
                    customer = UserResponseData(reservation.customer.id, reservation.customer.username),
                    parkingArea = reservation.parkingArea,
                    parkingSlotNumber = reservation.parkingSlotNumber,
                    createdAt = reservation.createdAt.toString(),
                    paidAt = reservation.paidAt.toString(),
                    checkoutAt = reservation.checkoutAt.toString()
                )
            }
            val jsonString = """
                {
                    "customer": {
                        "id": "${reservations[0].customer.id}",
                        "name": "${reservations[0].customer.username}"
                    },
                    "from": "$fromDateTime",
                    "to": "$toDateTime",
                    "reservations": ${objectMapper.writeValueAsString(reservationsResponseData)}
                }
            """.trimIndent()

            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString)
        } catch (e: Exception) {
            commonExceptionHandler(e, path = "customer/${userId}/reservations", objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "customer/${userId}/reservations", objectMapper)
        }
    }

    // Note: updateBankAccount info
    @PutMapping("/{userId}/update-bank-account")
    fun updateBankAccount(
        @PathVariable("userId") userId: Long,
        @RequestParam("newValue", required = true) newAcc: String
    ): ResponseEntity<String> {
        if (!validateBankAccountString(newAcc)) {
            throw Exception("Bank account is not in the right format of ^[A-Z]{2,4}BANK\\d{9,12}\\$")
        }
        val customer = customerService.getCustomerById(userId) ?: throw Exception("Cannot find customer with such ID")
        customer.bankAccount = newAcc
        customerService.updateDataOfExistedCustomer(customer)
        val jsonResponse = """
            {
                "message": "Successfully update ${customer.username}'s bank account to ${customer.bankAccount}
            }
        """.trimIndent()
        return try {
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonResponse)
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "customer/${userId}/update-bank-account",objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "update-bank-account", objectMapper)
        }


    }

    // Note: pay a reservation
    @PostMapping("{userId}/pay-reservation/{reservationId}")
    fun payReservation(
        @PathVariable("userId") userId: Long,
        @PathVariable("reservationId") reservationId: Long,
        @RequestParam("payBy", required = true) payBy: String
    ): ResponseEntity<String> {
        return try {
            val paymentMethod = when (payBy) {
                "cash" -> PaymentMethod.CASH
                "bank-transfer" -> PaymentMethod.BANK_TRANSFER
                else -> {
                    throw Exception("Cannot recognize payment method")
                }
            }
            reservationManagerService.setUpPaymentService(userId, paymentMethod)
            val receipt = reservationManagerService.payReservation(reservationId)
            val reservationResponse = ReservationResponseData(
                id = receipt.reservation.id,
                customer = UserResponseData(receipt.reservation.customer.id, receipt.reservation.customer.username),
                parkingArea = receipt.reservation.parkingArea,
                parkingSlotNumber = receipt.reservation.parkingSlotNumber,
                createdAt = receipt.reservation.createdAt.toString(),
                paidAt = receipt.reservation.paidAt.toString(),
                checkoutAt = receipt.reservation.checkoutAt.toString()
            )
            var bodyJson = ""
            when (receipt) {
                is BankTransferReceipt -> {
                    bodyJson = objectMapper.writeValueAsString(
                        BankTransferReceiptResponseData(
                            id = receipt.id,
                            reservation = reservationResponse,
                            price = receipt.price,
                            paidTime = receipt.paidTime,
                            description = receipt.description,
                            bankAccount = receipt.bankAccount
                        )
                    )
                }
                is CashReceipt -> {
                    bodyJson = objectMapper.writeValueAsString(
                        CashReceiptResponseData(
                            id = receipt.id,
                            reservation = reservationResponse,
                            price = receipt.price,
                            paidTime = receipt.paidTime,
                            description = receipt.description,
                        )
                    )
                }
            }
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bodyJson)
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "customer/${userId}/reservation/${reservationId}", objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "customer/${userId}/reservation/${reservationId}",objectMapper)
        }
    }

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
                    customer = UserResponseData(reservation.customer.id, reservation.customer.username),
                    parkingArea = reservation.parkingArea,
                    parkingSlotNumber = reservation.parkingSlotNumber,
                    createdAt = reservation.createdAt.toString(),
                    paidAt = reservation.paidAt.toString(),
                    checkoutAt = reservation.checkoutAt.toString(),
                )
                val jsonResponse = objectMapper.writeValueAsString(response)
                ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse)
            } else {
                throw Exception("Cannot found reservation with given User ID and Reservation ID")
            }
        } catch (ex: Exception) {
            commonExceptionHandler(ex, path = "customer/${userId}/reservation/${reservationId}", objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e = e, path = "customer/${userId}/reservation/${reservationId}", objectMapper)
        }
    }

//    // Note: checkout a reservation
//    @PutMapping("/{userId}/checkout-reservation/{reservationId}")
//    fun checkoutReservation(
//        @PathVariable("userId", required = true) userId: Long,
//        @PathVariable("reservationId", required = true) reservationId: Long
//    ): ResponseEntity<String> {
////        reservationManagerService.checkoutReservation()
//        return ResponseEntity
//            .status(HttpStatus.OK)
//            .contentType(MediaType.APPLICATION_JSON)
//            .body("body")
//    }

    private fun validateBankAccountString(account: String): Boolean {
        val regex = "^[A-Z]{2,4}BANK\\d{9,12}$".toRegex()
        return regex.matches(account)
    }



}
