import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.service.CustomerService
import swin.hn.swe30003.osps.service.ParkingMapService
import swin.hn.swe30003.osps.service.ReservationManagerService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.PrimitiveIterator

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val objectMapper: ObjectMapper,
    private val reservationManagerService: ReservationManagerService
) {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    @PostMapping("/{userId}/reserve-parking-slot")
    fun reserveParkingSlot(
        @PathVariable("userId", required = true) userId: Long,
        @RequestBody slotId: Long,
        @RequestBody timeString: String
    ): ResponseEntity<String> {
        return try {
            val reservationTime = LocalDateTime.parse(timeString, formatter)
            val reservation = reservationManagerService.createReservation(customerId = userId, slotId = slotId, reservationTime)
            reservationManagerService.saveReservation(reservation)

            val responseBody = objectMapper.writeValueAsString(reservation)
            ResponseEntity(responseBody, HttpStatus.CREATED)
        } catch (e: DateTimeParseException) {
            ResponseEntity("Wrong date time format: ${e.message}", HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Exception: ${e.message}", HttpStatus.BAD_REQUEST)
        } catch (e: Error) {
            ResponseEntity("An unexpected error happened: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{userId}/reservations")
    fun getHistoryReservation(
        @PathVariable("userId", required = true) userId: Long,
        @RequestParam("from") from: String,
        @RequestParam("to") to: String
    ): ResponseEntity<String> {
        return try {
            val fromDateTime = LocalDate.parse(from).atStartOfDay()
            val toDateTime = LocalDate.parse(to).atStartOfDay()
            val reservations = reservationManagerService.getReservationByCustomerInPeriod(userId, fromDateTime, toDateTime)
            val responseBody = objectMapper.writeValueAsString(reservations)
            ResponseEntity(responseBody, HttpStatus.OK)
        } catch (e: DateTimeParseException) {
            ResponseEntity("Wrong date time format exception: ${e.message}", HttpStatus.BAD_REQUEST)
        }

    }
}
