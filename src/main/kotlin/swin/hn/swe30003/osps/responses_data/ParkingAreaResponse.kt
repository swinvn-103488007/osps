package swin.hn.swe30003.osps.responses_data

data class ParkingAreaResponse(
    val id: String,
    val slots: List<ParkingSlotResponse>
)