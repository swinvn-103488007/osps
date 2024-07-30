package swin.hn.swe30003.osps.entity

import jakarta.persistence.*

@Entity
class Receipt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "reservation_id")
    val reservation: Reservation,

    val amount: Double
)
