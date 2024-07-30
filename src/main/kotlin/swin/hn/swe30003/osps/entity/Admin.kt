package swin.hn.swe30003.osps.entity

import jakarta.persistence.Entity

@Entity
class Admin(
    username: String,
    password: String
) : User(username = username, password = password) {
    // Additional fields and methods specific to Administrator
}