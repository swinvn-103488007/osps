package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import swin.hn.swe30003.osps.entity.Reservation
import java.time.LocalDateTime

@Repository
interface ReservationRepository: JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :userId " +
            "AND r.reservationTime >= :fromDateTime AND r.reservationTime <= :toDateTime")
    fun getReservationByCustomerInPeriod(
        @Param("userId") userId: Long,
        @Param("fromDateTime") fromDatetime: LocalDateTime,
        @Param("toDateTime") toDatetime: LocalDateTime,
    ): List<Reservation>
}