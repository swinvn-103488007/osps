package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Customer
import swin.hn.swe30003.osps.entity.User
import swin.hn.swe30003.osps.repository.CustomerRepository

@Service
class CustomerService(private val customerRepo: CustomerRepository) {

    fun findAll(): List<Customer> = customerRepo.findAll()

    fun findById(id: Long): Customer? = customerRepo.findById(id).orElse(null)

    fun save(customer: Customer): Customer {
        if (customerRepo.isCustomerExist(customer.username)) {
            throw RuntimeException("Error: Username already exists: ${customer.username}")
        }
        return customerRepo.save(customer)
    }

    fun deleteById(id: Long) = customerRepo.deleteById(id)

    fun validateCustomer(_name: String, _pwd: String): String {
        if (validateCustomerName(_name) == null) {
            throw Error("Cannot find customer name")
        }
        if (!validateCustomerPassword(_name, _pwd)) {
            throw Error("Wrong password")
        }

        return "Successfully log in as $_name"
    }

    fun validateCustomerName(_name: String): String? {
        val customerList = customerRepo.findCustomerByUserName(username = _name)
        return if (customerList.isEmpty()) null else customerList[0].username
    }

    fun validateCustomerPassword(_name: String, _pwd: String): Boolean {
        val password = customerRepo.getCustomerPassword(_name)
        return password == _pwd
    }
}

