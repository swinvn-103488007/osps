package swin.hn.swe30003.osps.service.payment_service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.constant.PARKING_SLOT_PRICE
import swin.hn.swe30003.osps.entity.Invoice
import swin.hn.swe30003.osps.entity.receipt.CashReceipt
import java.time.LocalDateTime

@Service
class CashPaymentService: PaymentService {
    override fun pay(invoice: Invoice): CashReceipt {
        val paidTime = LocalDateTime.now()
        invoice.reservation.paidAt = paidTime
        return CashReceipt(
            reservation = invoice.reservation,
            price = PARKING_SLOT_PRICE,
            paidTime = paidTime,
            description = "Paid by cash at parking facilities"
        )
    }
}