package swin.hn.swe30003.osps.entity

import jakarta.persistence.*

@Entity
class ParkingSlot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var available: Boolean = true
)