package swin.hn.swe30003.osps.entity

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import swin.hn.swe30003.osps.common.UserRole

@Entity
class Customer(
    username: String,
    password: String
) : User(username = username, password = password) {
    // Additional fields and methods specific to Customer

}