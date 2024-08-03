package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.service.AdminService
import swin.hn.swe30003.osps.service.CustomerService

@RestController
@RequestMapping("/login")
class LoginController(
    private val customerService: CustomerService,
    private val adminService: AdminService,
    private val objectMapper: ObjectMapper
) {
    @GetMapping("")
    fun login(
        @RequestParam(required = true) username: String,
        @RequestBody(required = true) password: String,
        @RequestParam role: String): ResponseEntity<String> {
        when (role) {
            "customer" -> {
                return try {
                    val successMsg = customerService.validateCustomer(username, password)
                    ResponseEntity.ok(successMsg)
                } catch (e: Exception) {
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
                }
            }
            "admin" -> {
                return try {
                    val successMsg = adminService.validateAdminCredentials(username, password)
                    ResponseEntity.ok(successMsg)
                } catch (e: Exception) {
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
                }
            }
            else -> {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some unexpected error happened")
            }
        }
    }
}