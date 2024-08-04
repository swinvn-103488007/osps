package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.exception_handler.commonErrorHandler
import swin.hn.swe30003.osps.exception_handler.commonExceptionHandler
import swin.hn.swe30003.osps.service.AdminService
import java.time.LocalDateTime

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = ["*"])
class AdminController (
    private val adminService: AdminService,
    private val objectMapper: ObjectMapper
) {
    @PostMapping("/{adminId}/add-parking-slot")
    fun addParkingSlot(
        @Param("areaId") areaId: String,
        @Param("slotNumber") slotNumber: Int,
        @PathVariable("adminId") adminId: Long
    ): ResponseEntity<String> {
        return try {
            if (!adminService.checkAdminExistById(adminId)) {
                throw Exception("Invalid attempt to modify parking map")
            }
            val parkingSlotId = ParkingSlotId(areaId, slotNumber)
            val createdSlot = adminService.addParkingSlot(parkingSlotId)
            val jsonBody = """
                {
                    "message": "Successfully create parking slot ${createdSlot.label}"
                }
            """.trimIndent()
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBody)
        } catch(ex: Exception) {
            commonExceptionHandler(ex, path = "/admin/add-parking-slot", objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "/admin/add-parking-slot", objectMapper)
        }
    }

    @GetMapping("{adminId}/revenue-by-area")
    fun getRevenueByAreasInAPeriod(
        @PathVariable("adminId") adminId: Long,
        @Param("from") from: String,
        @Param("to") to: String,
        @Param("areaIds") areaIds: String
    ): ResponseEntity<String> {
        return try {
            val fromDateTime = LocalDateTime.parse(from)
            val toDateTime = LocalDateTime.parse(to)
            if (!adminService.checkAdminExistById(adminId)) {
                throw Exception("Invalid attempt to modify parking map")
            }
            val reservationRevenueReports = adminService.reportReservationRevenueByArea(
                areaIds = areaIds,
                from = fromDateTime,
                to = toDateTime
            )
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(reservationRevenueReports))
        } catch (e: Exception) {
            commonExceptionHandler(e, path = "admin/${adminId}/revenue-by-area", objectMapper)
        } catch (e: Error) {
            commonErrorHandler(e, path = "customer/${adminId}/revenue-by-area", objectMapper)
        }
    }
}