package swin.hn.swe30003.osps.entity.receipt

import jakarta.persistence.*
import swin.hn.swe30003.osps.entity.Reservation
import java.time.LocalDateTime

@MappedSuperclass
abstract class Receipt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "reservation_id")
    val reservation: Reservation,

    val price: Double,
    val paidTime: LocalDateTime,
    val description: String
)
