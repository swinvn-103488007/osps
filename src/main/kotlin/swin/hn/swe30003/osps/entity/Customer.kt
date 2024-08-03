package swin.hn.swe30003.osps.entity

import jakarta.persistence.*
@Entity
class Customer(
    username: String,
    password: String,
    var bankAccount: String?
) : User(username = username, password = password) {

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val reservations: Set<Reservation> = emptySet()
}