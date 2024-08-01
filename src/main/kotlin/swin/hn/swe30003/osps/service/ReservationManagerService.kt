package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.entity.Reservation
import swin.hn.swe30003.osps.repository.CustomerRepository
import swin.hn.swe30003.osps.repository.ParkingSlotRepository
import swin.hn.swe30003.osps.repository.ReservationRepository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class ReservationManagerService (
    private val reservationRepo: ReservationRepository,
    private val customerRepo: CustomerRepository,
    private val parkingSlotRepo: ParkingSlotRepository
) {

    fun createReservation (customerId: Long, areaId: String, parkingSlotNumber: Int, time: LocalDateTime): Reservation {
        val customer = customerRepo.findById(customerId).getOrNull()
            ?: throw Exception("Cannot find the customer making reservation")

        val slot = parkingSlotRepo.findById(ParkingSlotId(areaId, parkingSlotNumber)).getOrNull()
            ?: throw Exception("Cannot find the parking slot for reserving")

        slot.isAvailable = false

        return Reservation(
            customer = customer, parkingArea = slot.parkingArea.id,
            parkingSlotNumber = slot.id.number, createdAt = time, paidAt = null, returnSlotAt = null)
    }

    fun saveReservation(reservation: Reservation) {
        reservationRepo.save(reservation)
    }

    fun getReservationsByCustomerInPeriod(
        userId: Long,
        fromDateTime: LocalDateTime,
        toDateTime: LocalDateTime
    ): List<Reservation> {
        return reservationRepo.getReservationByCustomerInPeriod(userId, fromDateTime, toDateTime)
    }

    fun getReservationByIdAndCustomerId(
        userId: Long,
        reservationId: Long
    ): Reservation? {
        return reservationRepo.findByIdAndCustomerId(userId, reservationId)
    }


}