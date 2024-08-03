package swin.hn.swe30003.osps.responses_data

data class ReservationResponseData(
    val id: Long,
    val customer: CustomerResponseData,
    val parkingArea: String,
    val parkingSlotNumber: Int,
    val createdAt: String,
    var paidAt: String,
    var checkoutAt: String
)