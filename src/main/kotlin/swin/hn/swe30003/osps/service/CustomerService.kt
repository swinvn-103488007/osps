package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.repository.CustomerRepository

@Service
class CustomerService(
    private val customerRepo: CustomerRepository,
    private val parkingMapService: ParkingMapService
) {

//    fun findAll(): List<Customer> = customerRepo.findAll()
//
//    fun findById(id: Long): Customer? = customerRepo.findById(id).orElse(null)

    fun save(customer: Customer): Customer {
        if (customerRepo.findCustomerByName(customer.username) != null) {
            throw Exception("Username already exists: ${customer.username}")
        }
        return customerRepo.save(customer)
    }

    fun validateCustomer(_name: String, _pwd: String): String {
        val foundCustomer = customerRepo.findCustomerByName(_name) ?: throw Exception("Can not find such user with such name")
        if (customerRepo.checkCustomerCredential(foundCustomer.id, _pwd)) {
            throw Exception("Wrong password")
        }
        return "Successfully log in as $_name"
    }
}

