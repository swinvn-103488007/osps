package swin.hn.swe30003.osps.exception_handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import swin.hn.swe30003.osps.responses_data.ErrorResponse
import java.time.LocalDateTime

fun commonErrorHandler(
    e: Error, path: String, objMapper: ObjectMapper
): ResponseEntity<String> {
    val errResponse = ErrorResponse(
        time = LocalDateTime.now().toString(),
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        error = HttpStatus.INTERNAL_SERVER_ERROR.toString(),
        message = "An unexpected error happen. Description ${e.message}",
        path = path
    )
    val objectMapper = ObjectMapper()
    val jsonResponse = objectMapper.writeValueAsString(errResponse)
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonResponse)
}

fun commonExceptionHandler(
    ex: Exception, path: String, objMapper: ObjectMapper
): ResponseEntity<String> {
    val errResponse = ErrorResponse(
        time = LocalDateTime.now().toString(),
        status = HttpStatus.BAD_REQUEST.value(),
        error = HttpStatus.BAD_REQUEST.toString(),
        message = "Exception: ${ex.message}",
        path = path
    )
    val jsonResponse = objMapper.writeValueAsString(errResponse)
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonResponse)
}