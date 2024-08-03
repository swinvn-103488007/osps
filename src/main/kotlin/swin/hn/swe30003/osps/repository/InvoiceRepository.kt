package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import swin.hn.swe30003.osps.entity.Invoice

interface InvoiceRepository: JpaRepository<Invoice, Long> {
    @Query("SELECT i FROM Invoice i WHERE i.reservation.id = :reservationId")
    fun getInvoiceByReservationId(@Param("reservationId") reservationId: Long): Invoice?
}