package swin.hn.swe30003.osps.responses_data

import java.time.LocalDateTime

data class ReservationResponseData(
    val id: Long,
    val customer: CustomerResponseData,
    val parkingArea: String,
    val parkingSlotNumber: Int,
    val createdAt: LocalDateTime,
    var paidAt: LocalDateTime?,
    var returnSlotAt: LocalDateTime?
)