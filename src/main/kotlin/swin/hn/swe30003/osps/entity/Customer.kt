package swin.hn.swe30003.osps.entity

import jakarta.persistence.*
@Entity
class Customer(
    username: String,
    password: String
) : User(username = username, password = password) {
    // Additional fields and methods specific to Customer
    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val reservations: Set<Reservation> = emptySet()
}