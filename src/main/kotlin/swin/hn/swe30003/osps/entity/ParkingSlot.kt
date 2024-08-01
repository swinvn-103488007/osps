package swin.hn.swe30003.osps.entity

import jakarta.persistence.*

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ParkingSlotId(
    val parkingArea: String,
    val number: Int
): Serializable

@Entity
class ParkingSlot(

    @EmbeddedId
    val id: ParkingSlotId,

    @MapsId("parkingArea")
    @ManyToOne
    @JoinColumn(name = "parking_area_id", referencedColumnName = "id", insertable = false, updatable = false)
    val parkingArea: ParkingArea,
    var isAvailable: Boolean = true,
) {
    val label: String
        get() = "${parkingArea.id}${id.number}"

    override fun toString(): String {
        return "ParkingSlot($label, isAvailable=$isAvailable)"
    }
}

