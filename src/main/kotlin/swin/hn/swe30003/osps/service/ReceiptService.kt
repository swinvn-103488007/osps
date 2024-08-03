package swin.hn.swe30003.osps.service

import org.springframework.stereotype.Service
import swin.hn.swe30003.osps.entity.receipt.BankTransferReceipt
import swin.hn.swe30003.osps.entity.receipt.CashReceipt
import swin.hn.swe30003.osps.entity.receipt.Receipt
import swin.hn.swe30003.osps.repository.BankTransferReceiptRepository
import swin.hn.swe30003.osps.repository.CashReceiptRepository

@Service
class ReceiptService(
    private val cashReceiptRepository: CashReceiptRepository,
    private val bankTransferReceiptRepository: BankTransferReceiptRepository
) {
    fun saveReceipt(receipt: Receipt) {
        when (receipt) {
            is CashReceipt -> {
                cashReceiptRepository.save(receipt)
            }
            is BankTransferReceipt -> {
                bankTransferReceiptRepository.save(receipt)
            }
        }
    }
}