package swin.hn.swe30003.osps.entity

import jakarta.persistence.*

@MappedSuperclass
abstract class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

)
