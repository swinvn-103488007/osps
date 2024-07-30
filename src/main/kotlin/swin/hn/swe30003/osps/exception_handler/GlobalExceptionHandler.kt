package swin.hn.swe30003.osps.exception_handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.converter.HttpMessageNotReadableException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST, "Invalid request body", ex.message)
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    // Handle other exceptions as needed
}
