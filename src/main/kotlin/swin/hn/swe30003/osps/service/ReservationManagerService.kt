package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.entity.Reservation
import swin.hn.swe30003.osps.repository.CustomerRepository
import swin.hn.swe30003.osps.repository.ParkingSlotRepository
import swin.hn.swe30003.osps.repository.ReservationRepository
import java.time.LocalDateTime
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class ReservationManagerService (
    private val reservationRepo: ReservationRepository,
    private val customerRepo: CustomerRepository,
    private val parkingSlotRepo: ParkingSlotRepository
) {

//    fun createReservation (customer: Customer, slot: ParkingSlot, time: LocalDateTime): Reservation
//    fun createReservation (customer: String, slot: ParkingSlot, time: LocalDateTime): Reservation

    fun createReservation (customerId: Long, parkingSlotId: ParkingSlotId, time: LocalDateTime): Reservation {
        val customer = customerRepo.findById(customerId).getOrNull()
            ?: throw Exception("Cannot find the customer making reservation")

        val slot = parkingSlotRepo.findById(parkingSlotId).getOrNull()
            ?: throw Exception("Cannot find the parking slot for reserving")

        return Reservation(customer = customer, parkingSlot = slot, creationTime = time, paidTime = null, returnSlotTime = null)
    }

    fun saveReservation(reservation: Reservation) {
        reservationRepo.save(reservation)
    }

    fun getReservationByCustomerInPeriod(
        userId: Long,
        fromDateTime: LocalDateTime,
        toDateTime: LocalDateTime
    ): List<Reservation> {
        return reservationRepo.getReservationByCustomerInPeriod(userId, fromDateTime, toDateTime)
    }
}