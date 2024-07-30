import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.repository.AdministratorRepository

@Service
class AdministratorService(private val administratorRepository: AdministratorRepository) {

    fun findAll(): List<Admin> = administratorRepository.findAll()

    fun findById(id: Long): Admin? = administratorRepository.findById(id).orElse(null)

    fun save(administrator: Admin): Admin = administratorRepository.save(administrator)

    fun deleteById(id: Long) = administratorRepository.deleteById(id)
}
