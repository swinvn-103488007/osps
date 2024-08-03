package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.PaymentMethod
import swin.hn.swe30003.osps.constant.PARKING_SLOT_PRICE
import swin.hn.swe30003.osps.entity.Invoice
import swin.hn.swe30003.osps.entity.ParkingSlotId
import swin.hn.swe30003.osps.entity.Reservation
import swin.hn.swe30003.osps.entity.receipt.Receipt
import swin.hn.swe30003.osps.repository.*
import swin.hn.swe30003.osps.service.payment_service.BankTransferPaymentService
import swin.hn.swe30003.osps.service.payment_service.CashPaymentService
import swin.hn.swe30003.osps.service.payment_service.PaymentService
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class ReservationManagerService(
    private val reservationRepo: ReservationRepository,
    private val customerRepo: CustomerRepository,
    private val parkingSlotRepo: ParkingSlotRepository,
    private val invoiceRepository: InvoiceRepository,
    private val receiptService: ReceiptService
) {

    // store an updatable PaymentService
    private lateinit var paymentService: PaymentService
    fun setUpPaymentService(customerId: Long, selectedPaymentMethod: PaymentMethod) {
        paymentService = when (selectedPaymentMethod) {
            PaymentMethod.CASH -> {
                CashPaymentService()
            }

            PaymentMethod.BANK_TRANSFER -> {
                val customer = customerRepo.findById(customerId).getOrNull()
                if (customer?.bankAccount == null) {
                    throw Exception("Cannot setup Bank Transfer Payment Method because bank account is empty")
                }
                BankTransferPaymentService()
            }
        }
    }
    fun createReservation(customerId: Long, areaId: String, parkingSlotNumber: Int, time: LocalDateTime): Reservation {
        val customer = customerRepo.findById(customerId).getOrNull()
            ?: throw Exception("Cannot find the customer making reservation")

        val slot = parkingSlotRepo.findById(ParkingSlotId(areaId, parkingSlotNumber)).getOrNull()
            ?: throw Exception("Cannot find the parking slot for reserving")

        if (!slot.isAvailable) {
            throw Exception("This parking slot is already reserved")
        }
        slot.isAvailable = false
        parkingSlotRepo.save(slot)

        val reservation = Reservation(
            customer = customer, parkingArea = slot.parkingArea.id,
            parkingSlotNumber = slot.id.number, createdAt = time, paidAt = null, checkoutAt = null)
        reservationRepo.save(reservation)

        val invoice = Invoice(
            reservation = reservation,
            amount = PARKING_SLOT_PRICE
        )
        invoiceRepository.save(invoice)

        return reservation
    }

    fun releaseParkingSlot(parkingSlotId: ParkingSlotId) {
        val parkingSlot = parkingSlotRepo.findById(parkingSlotId).getOrNull()
            ?: throw Exception("Cannot find parking slot with given ID ")
        parkingSlot.isAvailable = true
        parkingSlotRepo.save(parkingSlot)
    }
    fun payReservation(reservationId: Long): Receipt {
        val reservation = reservationRepo.findById(reservationId).getOrNull()
            ?: throw Exception("Cannot find reservation to pay")
        if (reservation.paidAt != null) {
            throw Exception("This reservation is already paid")
        }
        val invoice = invoiceRepository.getInvoiceByReservationId(reservationId)
            ?: throw Exception("Cannot find invoice with such ID")
        val receipt = paymentService.pay(invoice)
        receiptService.saveReceipt(receipt)
        return receipt
    }

    fun getReservationsByCustomerInPeriod(
        userId: Long,
        fromDateTime: LocalDateTime,
        toDateTime: LocalDateTime
    ): List<Reservation> {
        return reservationRepo.getReservationByCustomerInPeriod(userId, fromDateTime, toDateTime)
    }

    fun getReservationByIdAndCustomerId(
        userId: Long,
        reservationId: Long
    ): Reservation? {
        return reservationRepo.findByIdAndCustomerId(userId, reservationId)
    }

    fun updateReservationCheckoutTime(reservation: Reservation) {
        reservation.checkoutAt = LocalDateTime.now()
        reservationRepo.save(reservation)
    }


}