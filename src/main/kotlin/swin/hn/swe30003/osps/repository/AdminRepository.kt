package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import swin.hn.swe30003.osps.entity.Admin

@Repository
interface AdminRepository : JpaRepository<Admin, Long> {
    @Query("SELECT a FROM Admin a WHERE a.username = :username")
    fun findAdminByName(@Param("username") username: String): Admin?

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admin a WHERE a.id = :id AND a.password = :pwd")
    fun checkAdminPassword(@Param("id") userId: Long, @Param("pwd") pwd: String): Boolean
}
