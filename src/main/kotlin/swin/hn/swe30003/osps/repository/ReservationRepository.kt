package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.entity.Reservation
import java.time.LocalDateTime

@Repository
interface ReservationRepository: JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :userId " +
            "AND r.createdAt >= :fromDateTime AND r.createdAt<= :toDateTime")
    fun getReservationByCustomerInPeriod(
        @Param("userId") userId: Long,
        @Param("fromDateTime") fromDatetime: LocalDateTime,
        @Param("toDateTime") toDatetime: LocalDateTime,
    ): List<Reservation>

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :userId AND r.id = :reservationId")
    fun findByIdAndCustomerId(
        @Param("userId") userId: Long,
        @Param("reservationId") reservationId: Long
    ): Reservation?

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.parkingArea = :parkingArea " +
            "AND r.createdAt >= :fromDateTime AND r.createdAt<= :toDateTime")
    fun countReservationByAreaInPeriod(
        @Param("parkingArea") parkingArea: String,
        @Param("fromDateTime") fromDatetime: LocalDateTime,
        @Param("toDateTime") toDatetime: LocalDateTime,
    ): Int

}