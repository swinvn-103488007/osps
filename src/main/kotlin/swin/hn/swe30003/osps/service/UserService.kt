package swin.hn.swe30003.osps.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.User

@Service
abstract class UserService<T: User> {

    protected lateinit var repo: JpaRepository<T, Long>
    abstract fun findUserByUserName(username: String): T?
    abstract fun saveUser(username: String, password: String): T
    abstract fun checkPassword(userId: Long, password: String): Boolean
    fun registerNewUser(username: String, password: String): T {
        val user = this.findUserByUserName(username)
        if (user != null) {
             throw Exception("Customer with $username already exists: ")
        }
        return saveUser(username, password)
    }

    fun loginWithCredentials(_name: String, _pwd: String): T {
        val user = findUserByUserName(_name) ?: throw Exception("Can not find username $_name")
        if (!checkPassword(user.id, _pwd)) {
            throw Exception("Wrong password")
        }
        return user
    }

}