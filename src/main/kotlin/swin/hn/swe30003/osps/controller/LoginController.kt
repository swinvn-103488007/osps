package swin.hn.swe30003.osps.controller

import AdministratorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.entity.User
import swin.hn.swe30003.osps.service.CustomerService
import java.lang.Error

@RestController
@RequestMapping("/login")
class LoginController(
    private val customerService: CustomerService,
    private val adminService: AdministratorService
) {
    @GetMapping("")
    fun login(@RequestBody _user: User, @RequestParam _role: String): ResponseEntity<String> {
        // private val userService.validate(username, password)
        when (_role) {
            "customer" -> {
                 try {
                     customerService.validateCustomer(_user.username, _user.password)
                 } catch (e: Error) {

                 }

            }
            "admin" -> {
                // adminService.validateAdmin(user)
            }
        }
    }
}