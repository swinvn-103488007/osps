package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import swin.hn.swe30003.osps.entity.Customer

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
    // You can define custom query methods here
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.username = :username")
    fun isCustomerExist(@Param("username") username: String): Boolean

    @Query("SELECT c FROM Customer c WHERE c.username = :username")
    fun findCustomerByUserName(@Param("username") username: String): List<Customer>

    @Query("SELECT c.password FROM Customer c WHERE c.name = :name")
    fun getCustomerPassword(@Param("username") username: String): String?
}
