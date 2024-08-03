package swin.hn.swe30003.osps.responses_data.receipt_response_datas

import swin.hn.swe30003.osps.responses_data.ReservationResponseData
import java.time.LocalDateTime

//abstract class Receipt(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0,
//
//    @OneToOne
//    @JoinColumn(name = "reservation_id")
//    val reservation: Reservation,
//
//    val price: Double,
//    val paidTime: LocalDateTime,
//    val description: String
//)
data class CashReceiptResponseData(
    val id: Long,
    val reservation:ReservationResponseData,
    val price: Double,
    val paidTime: LocalDateTime,
    val description: String,
)