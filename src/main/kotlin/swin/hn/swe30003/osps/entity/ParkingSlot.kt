package swin.hn.swe30003.osps.entity

import jakarta.persistence.*

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class ParkingSlotId(
    val parkingArea: String,
    val number: Int
): Serializable

@Entity
class ParkingSlot(
    @EmbeddedId
    val id: ParkingSlotId,

    @MapsId("parkingArea")
    @ManyToOne
    @JoinColumn(name = "parking_area_id", nullable = false)
    val parkingArea: ParkingArea,

//    @ManyToOne
//    @JoinColumn(name = "parking_area_id", referencedColumnName = "id", insertable = false, updatable = false)
//    val parkingArea: ParkingArea,

    val isAvailable: Boolean,

) {
    val label: String
        get() = "${id.parkingArea}${id.number}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as ParkingSlot

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "ParkingSlot(id=$id, parkingArea=$parkingArea, isAvailable=$isAvailable, label=$label)"
    }
}

