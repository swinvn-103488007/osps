package swin.hn.swe30003.osps.service.payment_service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.constant.PARKING_SLOT_PRICE
import swin.hn.swe30003.osps.entity.Invoice
import swin.hn.swe30003.osps.entity.receipt.BankTransferReceipt
import java.time.LocalDateTime

@Service
class BankTransferPaymentService(): PaymentService {
    override fun pay(invoice: Invoice): BankTransferReceipt {
        val bankAcc = invoice.reservation.customer.bankAccount ?: throw Exception("Can not find customer's bank account to proceed")
        val paidTime = LocalDateTime.now()
        invoice.reservation.paidAt = paidTime
        return BankTransferReceipt(
            reservation = invoice.reservation,
            price = PARKING_SLOT_PRICE,
            paidTime = paidTime,
            description = "Paid by online bank transfer through bank account $bankAcc",
            bankAccount = bankAcc
        )
    }
    // private val bankAccount: String

}