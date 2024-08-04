package swin.hn.swe30003.osps.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.entity.User
import swin.hn.swe30003.osps.exception_handler.commonErrorHandler
import swin.hn.swe30003.osps.exception_handler.commonExceptionHandler
import swin.hn.swe30003.osps.responses_data.UserResponseData
import swin.hn.swe30003.osps.service.AdminService
import swin.hn.swe30003.osps.service.CustomerService
import swin.hn.swe30003.osps.service.UserService

@RestController
@RequestMapping("/login")
class LoginController(
    val objectMapper: ObjectMapper,
    val context: ApplicationContext

) {
    @PostMapping("")
    fun login(
        @RequestParam(required = true) username: String,
        @RequestBody(required = true) password: String,
        @RequestParam role: String
    ): ResponseEntity<String> {
        try {
            val service = getService(role) ?: throw Exception("Cannot create user of role $role")
            val user = service.loginWithCredentials(username, password)
            System.out.println(user)
            val userResponseData = UserResponseData(user.id, user.username)
            System.out.println(objectMapper.writeValueAsString(userResponseData))
            return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(userResponseData))
        } catch (ex: Exception) {
            System.out.println(ex)
            return commonExceptionHandler(ex, path = "login", objectMapper)
        } catch (e: Error) {
            System.out.println(e)
            return commonErrorHandler(e, path = "login", objectMapper)
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