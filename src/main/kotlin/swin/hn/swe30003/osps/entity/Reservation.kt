package swin.hn.swe30003.osps.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,

    val parkingSlotNumber: Int,
    val parkingArea: String,
    val createdAt: LocalDateTime,
    var paidAt: LocalDateTime?,
    var returnSlotAt: LocalDateTime?
) {

}
