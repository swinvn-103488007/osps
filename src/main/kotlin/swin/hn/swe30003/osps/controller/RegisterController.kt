package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.service.AdminService
import swin.hn.swe30003.osps.service.CustomerService

@RestController
@RequestMapping("/register")
class RegisterController(
    private val customerService: CustomerService,
    private val adminService: AdminService,
    private val objectMapper: ObjectMapper
) {
    @PostMapping("")
    fun registerUser(
        @RequestParam(name = "username",required = true) username: String,
        @RequestBody(required = true) password: String,
        @RequestParam(name = "role", required = true) role: String,
        @RequestParam(name = "bankAccount") bankAccount: String?
    ): ResponseEntity<String> {
        return try {
            when (role) {
                "customer" -> {
                    val customer = Customer(username, password, bankAccount)
                    customerService.registerCustomer(customer)
                }
                "admin" -> {
                    val admin = Admin(username, password)
                    adminService.registerAdmin(admin)
                }
            }

            ResponseEntity.ok("Successfully registered $username as $role")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}