package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.repository.CustomerRepository
import kotlin.jvm.optionals.getOrNull

@Service
class CustomerService(
    private val customerRepo: CustomerRepository,
) {

    fun registerCustomer(customer: Customer): Customer {
        if (customerRepo.findCustomerByName(customer.username) != null) {
            throw Exception("Username ${customer.username} already exists: ")
        }
        return customerRepo.save(customer)
    }

    fun updateDataExistedCustomer(customer: Customer) {
        if (customerRepo.findById(customer.id).getOrNull() == null) {
            throw Exception("User ${customer.username} is not registered")
        }
        customerRepo.save(customer)
    }
    fun validateCustomer(_name: String, _pwd: String): String {
        val foundCustomer = customerRepo.findCustomerByName(_name) ?: throw Exception("Can not find such user with such name")
        if (customerRepo.checkCustomerCredential(foundCustomer.id, _pwd)) {
            throw Exception("Wrong password")
        }
        return "Successfully log in as $_name"
    }

    fun getCustomerById(id: Long): Customer? {
        return customerRepo.findById(id).getOrNull()
    }
}

