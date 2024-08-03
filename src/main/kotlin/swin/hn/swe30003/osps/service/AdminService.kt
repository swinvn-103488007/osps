package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.entity.ParkingSlot
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.repository.AdminRepository
import swin.hn.swe30003.osps.repository.ParkingAreaRepository
import swin.hn.swe30003.osps.repository.ParkingSlotRepository
import swin.hn.swe30003.osps.repository.ReservationRepository
import swin.hn.swe30003.osps.responses_data.ReservationRevenueResponse
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class AdminService(
    private val adminRepo: AdminRepository,
    private val parkingSlotRepo: ParkingSlotRepository,
    private val parkingAreaRepo: ParkingAreaRepository,
    private val reservationRepo: ReservationRepository
) {

    fun registerAdmin(admin: Admin): Admin {
        if (adminRepo.findAdminByName(admin.username) != null) {
            throw Exception("Error: Username already exists: ${admin.username}")
        }
        return adminRepo.save(admin)
    }

    fun validateAdminCredentials(_name: String, _pwd: String): String {
        val foundAdmin = adminRepo.findAdminByName(_name) ?: throw Exception("Can not find admin with username $_name")
        if (adminRepo.checkAdminCredential(foundAdmin.id, _pwd)) {
            throw Exception("Wrong password")
        }
        return "Successfully log in as $_name"
    }

    fun checkAdminExistById(adminId: Long): Boolean {
        return adminRepo.existsById(adminId)
    }
    fun addParkingSlot(parkingSlotId: ParkingSlotId): ParkingSlot {
        val targetedParkingArea = parkingAreaRepo.findById(parkingSlotId.parkingArea).getOrNull()
            ?: throw Exception("Cannot find the parking area with the given ID")
        if (parkingSlotRepo.existsById(parkingSlotId)) {
            throw Exception("Parking Slot ${parkingSlotId.parkingArea}${parkingSlotId.number} already exist")
        }
        val parkingSlot = ParkingSlot(parkingSlotId, parkingArea = targetedParkingArea, isAvailable = true)
        parkingSlotRepo.save(parkingSlot)
        return parkingSlot
    }

    fun removeParkingSlot(parkingSlotId: ParkingSlotId) {
        parkingAreaRepo.findById(parkingSlotId.parkingArea).getOrNull()
            ?: throw Exception("Cannot find the parking area with the given ID")
        parkingSlotRepo.deleteById(parkingSlotId)
    }

    fun reportReservationRevenueByArea(areaIds: String, from: LocalDateTime, to: LocalDateTime): List<ReservationRevenueResponse> {

        val parkingAreaIds = areaIds.split(",").map { it ->
            it.trim()
        }
        val reservationReport = mutableListOf<ReservationRevenueResponse>()
        parkingAreaRepo.findAllById(parkingAreaIds).forEach { area ->
            val report = ReservationRevenueResponse(
                parkingArea = area.id,
                totalReservation = reservationRepo.countReservationByAreaInPeriod(area.id, from, to),
            )
            reservationReport.add(report)
        }
        return reservationReport
    }
}
