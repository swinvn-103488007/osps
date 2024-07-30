package swin.hn.swe30003.osps.controller

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
    private val adminService: AdminService
) {
    @PostMapping("")
    fun registerUser(
        @RequestParam(required = true) username: String,
        @RequestBody(required = true) password: String,
        @RequestParam(required = true) role: String
    ): ResponseEntity<String> {
        return try {
            when (role) {
                "customer" -> {
                    val customer = Customer(username, password)
                    customerService.save(customer)
                }
                "admin" -> {
                    val admin = Admin(username, password)
                    adminService.save(admin)
                }
            }

            ResponseEntity.ok("Successfully registered $username as $role")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}