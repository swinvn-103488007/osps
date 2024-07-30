package swin.hn.swe30003.osps.entity

import jakarta.persistence.*
import java.util.*

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,

    @ManyToOne
    @JoinColumn(name = "parking_slot_id")
    val parkingSlot: ParkingSlot,

    val reservationTime: Date
)
