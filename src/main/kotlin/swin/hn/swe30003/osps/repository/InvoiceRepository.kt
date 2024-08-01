package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import swin.hn.swe30003.osps.entity.Invoice

interface InvoiceRepository: JpaRepository<Invoice, Long> {
}