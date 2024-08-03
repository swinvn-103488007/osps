package swin.hn.swe30003.osps.service.payment_service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Invoice
import swin.hn.swe30003.osps.entity.receipt.Receipt

@Service
interface PaymentService {
    fun pay(invoice: Invoice): Receipt
}