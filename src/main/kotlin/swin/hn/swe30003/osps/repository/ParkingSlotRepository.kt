package swin.hn.swe30003.osps.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import swin.hn.swe30003.osps.entity.ParkingSlot

@Repository
interface ParkingSlotRepository: JpaRepository<ParkingSlot, Long> {

    @Query("SELECT p FROM ParkingSlot p WHERE p.isAvailable = true")
    fun getAvailableSlots(): List<ParkingSlot>
}