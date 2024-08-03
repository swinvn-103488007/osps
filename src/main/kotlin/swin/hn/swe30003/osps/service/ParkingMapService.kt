package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.ParkingArea
import swin.hn.swe30003.osps.entity.ParkingSlot
import swin.hn.swe30003.osps.repository.ParkingAreaRepository
import swin.hn.swe30003.osps.repository.ParkingSlotRepository

@Service
class ParkingMapService(
    private val parkingSlotRepo: ParkingSlotRepository,
    private val parkingAreaRepo: ParkingAreaRepository
) {
    fun getAvailableParkingSlots(): List<ParkingSlot> {
        return parkingSlotRepo.getAvailableSlots()
    }

    fun getAllParkingSlots(): List<ParkingSlot> {
        return parkingSlotRepo.findAll()
    }

    fun getAllParkingArea(): List<ParkingArea> {
        return parkingAreaRepo.findAll()
    }
}