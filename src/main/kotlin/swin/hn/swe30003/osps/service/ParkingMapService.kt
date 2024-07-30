package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.ParkingSlot
import swin.hn.swe30003.osps.repository.ParkingSlotRepository

@Service
class ParkingMapService(private val parkingSlotRepo: ParkingSlotRepository) {
    fun getAvailableParkingSlots(): List<ParkingSlot> {
        return parkingSlotRepo.getAvailableSlots()
    }
}