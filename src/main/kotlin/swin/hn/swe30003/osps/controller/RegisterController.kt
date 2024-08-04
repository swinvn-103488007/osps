package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.entity.User
import swin.hn.swe30003.osps.responses_data.UserResponseData
import swin.hn.swe30003.osps.service.AdminService
import swin.hn.swe30003.osps.service.CustomerService
import swin.hn.swe30003.osps.service.UserService

@RestController
@RequestMapping("/register")
class RegisterController(
    private val objectMapper: ObjectMapper,
    private val context: ApplicationContext
) {

    private lateinit var userService: UserService<User>
    @PostMapping("")
    fun registerUser(
        @RequestParam(name = "username",required = true) username: String,
        @RequestBody(required = true) password: String,
        @RequestParam(name = "role", required = true) role: String,
        @RequestParam(name = "bankAccount") bankAccount: String?
    ): ResponseEntity<String> {
        return try {
            val service = getService(role) ?: throw Exception("Cannot create user of role $role")
            val newUser = service.registerNewUser(username, password)
            val userResponseData = UserResponseData(newUser.id, newUser.username)
            ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(userResponseData))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }

    private fun getService(role: String): UserService<*>? {
        return when (role) {
            "customer" -> context.getBean(CustomerService::class.java)
            "admin" -> context.getBean(AdminService::class.java)
            else -> null
        }
    }

}