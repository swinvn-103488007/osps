package swin.hn.swe30003.osps.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.repository.CustomerRepository
import kotlin.jvm.optionals.getOrNull

@Service
class CustomerService: UserService<Customer>() {

    @Autowired
    fun setCustomerRepository(customerRepository: CustomerRepository) {
        this.repo = customerRepository
    }
    private val customerRepo
        get() = repo as CustomerRepository
    override fun findUserByUserName(username: String): Customer? {
        return customerRepo.findCustomerByName(username)
    }

    override fun saveUser(username: String, password: String): Customer {
        return customerRepo.save(Customer(username, password, null))
    }

    override fun checkPassword(userId: Long, password: String): Boolean {
        return customerRepo.checkCustomerPassword(userId, password)
    }

    fun updateDataOfExistedCustomer(customer: Customer) {
        if (repo.findById(customer.id).getOrNull() == null) {
            throw Exception("User ${customer.username} is not registered")
        }
        repo.save(customer)
    }

    fun getCustomerById(id: Long): Customer? {
        return repo.findById(id).getOrNull()
    }
}


