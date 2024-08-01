package swin.hn.swe30003.osps.entity

import jakarta.persistence.*
import java.time.LocalDateTime
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
    @JoinColumns(
        JoinColumn(name = "slot_number", referencedColumnName = "number"),
        JoinColumn(name = "parking_area_id", referencedColumnName = "parkingArea"),
    )
//    @JoinColumn(name = "parking_area_id", referencedColumnName = "parkingArea")
    val parkingSlot: ParkingSlot,

    val creationTime: LocalDateTime,
    var paidTime: LocalDateTime?,
    var returnSlotTime: LocalDateTime?
) {

}
