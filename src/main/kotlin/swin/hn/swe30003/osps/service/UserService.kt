package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.Admin
import swin.hn.swe30003.osps.entity.User

//@Service
//class UserService() {
//    // private val userRepo: UserRepository
//    fun save(user: User): User {
//        if (adminRepo.findAdminByName(admin.username) != null) {
//            throw Exception("Error: Username already exists: ${admin.username}")
//        }
//        return adminRepo.save(admin)
//    }
//
//    fun validateUserCredential(_name: String, _pwd: String): String {
//        val foundAdmin = adminRepo.findAdminByName(_name) ?: throw Exception("Can not find admin with username $_name")
//        if (adminRepo.checkAdminCredential(foundAdmin.id, _pwd)) {
//            throw Exception("Wrong password")
//        }
//        return "Successfully log in as $_name"
//    }
//}