package swin.hn.swe30003.osps.responses_data

import java.time.LocalDateTime

data class ErrorResponse(
    val time: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
