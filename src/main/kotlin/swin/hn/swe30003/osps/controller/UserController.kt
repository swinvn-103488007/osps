import org.springframework.web.bind.annotation.*
import swin.hn.swe30003.osps.service.CustomerService

@RestController
@RequestMapping("/users")
class UserController(
    private val administratorService: AdministratorService,
    private val customerService: CustomerService
) {


}
