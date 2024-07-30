package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import swin.hn.swe30003.osps.entity.Admin

@Repository
interface AdministratorRepository : JpaRepository<Admin, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Administrator c WHERE c.username = :username")
    fun isCustomerExist(@Param("username") username: String): Boolean
}
