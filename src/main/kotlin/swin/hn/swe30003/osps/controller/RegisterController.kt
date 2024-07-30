package swin.hn.swe30003.osps.controller

import AdministratorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.entity.User
import swin.hn.swe30003.osps.service.CustomerService

@RestController
@RequestMapping("/register")
class RegisterController(
    private val customerService: CustomerService,
    private val adminService: AdministratorService
) {
    @PostMapping("")
    fun registerUser(@RequestBody user: User, @RequestParam role: String): ResponseEntity<Any> {
        return try {
            when (role) {
                "customer" -> {
                    val customer = Customer(user.username, user.password)
                    customerService.save(customer)
                }
                "admin" -> {
                    val admin = Admin(user.username, user.password)
                    adminService.save(admin)
                }
            }

            ResponseEntity.ok("Successfully registered")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        }
    }
}