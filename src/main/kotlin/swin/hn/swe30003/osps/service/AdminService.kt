package swin.hn.swe30003.osps.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.entity.ParkingSlot
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.repository.*
import swin.hn.swe30003.osps.responses_data.ParkingSlotResponse
import swin.hn.swe30003.osps.responses_data.ReservationRevenueResponse
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class AdminService(
): UserService<Admin>() {

    @Autowired
    private lateinit var parkingSlotRepo: ParkingSlotRepository

    @Autowired
    private lateinit var parkingAreaRepo: ParkingAreaRepository

    @Autowired
    private lateinit var reservationRepo: ReservationRepository

    @Autowired
    fun setCustomerRepository(adminRepository: AdminRepository) {
        this.repo = adminRepository
    }

    private val adminRepo
        get() = repo as AdminRepository
//    override fun registerNewUser(username: String, password: String): Admin {
//        if (adminRepo.findAdminByName(username) != null) {
//            throw Exception("Customer with $username already exists: ")
//        }
//        return adminRepo.save(Admin(username, password))
//    }

    override fun loginWithCredentials(_name: String, _pwd: String): Admin {
        val foundAdmin = adminRepo.findAdminByName(_name) ?: throw Exception("Can not find such admin with such name")
        if (adminRepo.checkAdminPassword(foundAdmin.id, _pwd)) {
            throw Exception("Wrong password")
        }
        return foundAdmin
    }

    override fun findUserByUserName(username: String): Admin? {
        return adminRepo.findAdminByName(username)
    }

    override fun saveUser(username: String, password: String): Admin {
        return adminRepo.save(Admin(username, password))
    }

    override fun checkPassword(userId: Long, password: String): Boolean {
        return adminRepo.checkAdminPassword(userId, password)
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

    fun removeParkingSlot(parkingSlotId: ParkingSlotId): ParkingSlotResponse{
        val removeSlot = parkingSlotRepo.findById(parkingSlotId).getOrNull()
            ?: throw Exception("Cannot find the parking area with the given ID")
        val removedSlotData = ParkingSlotResponse(removeSlot.id.parkingArea, removeSlot.id.number, isAvailable = false)
        parkingSlotRepo.deleteById(parkingSlotId)
        return removedSlotData
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
