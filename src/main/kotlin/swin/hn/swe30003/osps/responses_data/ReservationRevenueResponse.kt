package swin.hn.swe30003.osps.responses_data

import swin.hn.swe30003.osps.constant.PARKING_SLOT_PRICE

data class ReservationRevenueResponse (
    val parkingArea: String,
    val totalReservation: Int,
    val revenue: Double = PARKING_SLOT_PRICE * totalReservation
)