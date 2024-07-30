package swin.hn.swe30003.osps.exception_handler

import org.springframework.http.HttpStatus

data class ApiError(
    val status: HttpStatus,
    val message: String,
    val debugMessage: String?
)
