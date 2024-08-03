package swin.hn.swe30003.osps.responses_data.receipt_response_datas

import swin.hn.swe30003.osps.entity.receipt.BankTransferReceipt
import swin.hn.swe30003.osps.responses_data.ReservationResponseData
import java.nio.DoubleBuffer
import java.time.LocalDateTime

// reservation = reservation, price = price, paidTime = paidTime, description = description, bankAccount: String
data class BankTransferReceiptResponseData(
    val id: Long,
    val reservation:ReservationResponseData,
    val price: Double,
    val paidTime: LocalDateTime,
    val description: String,
    val bankAccount: String
) {

}