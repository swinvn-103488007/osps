package swin.hn.swe30003.osps.entity

import jakarta.persistence.*

@Entity
class ParkingArea(
    @Id
    val id: String,

    @Column(nullable = false)
    val size: Int,

    @OneToMany(mappedBy = "parkingArea", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val parkingSlots: Set<ParkingSlot> = emptySet()
)
