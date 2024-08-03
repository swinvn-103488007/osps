package swin.hn.swe30003.osps.entity.receipt

import jakarta.persistence.Entity
import swin.hn.swe30003.osps.entity.Reservation
import java.time.LocalDateTime

@Entity
class CashReceipt(
    reservation: Reservation, price: Double, paidTime: LocalDateTime, description: String
) : Receipt(
    reservation = reservation, price = price, paidTime = paidTime, description = description
)
