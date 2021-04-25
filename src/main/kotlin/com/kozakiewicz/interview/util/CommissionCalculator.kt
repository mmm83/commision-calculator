package com.kozakiewicz.interview.util

import com.kozakiewicz.interview.entity.CommissionSummary
import com.kozakiewicz.interview.entity.FeeWage
import com.kozakiewicz.interview.entity.Transaction
import com.kozakiewicz.interview.repository.FeeWageRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.annotation.PostConstruct

@Service
class CommissionCalculator(private val feeWageRepository: FeeWageRepository) {

    private lateinit var feeWages: List<FeeWage>

    @PostConstruct
    fun getFeeWages() {
        feeWages = feeWageRepository.findAllByOrderByTransactionLessThanValueAsc()
    }

    fun calculateAllCommissions(transactions: List<Transaction>): List<CommissionSummary> {
        val customerIDs = transactions.map { it.customerId.toInt() }.toSet().toList()
        return calculateCommissions(customerIDs, transactions)
    }

    fun calculateCommissions(customerIDs: List<Int>, transactions: List<Transaction>): List<CommissionSummary> {
        val commissions = ArrayList<CommissionSummary>()
        for (id in customerIDs) {
            val calculateCommission = calculateCommission(transactions.filter { it.customerId == id })
            commissions.add(calculateCommission)
        }
        return commissions
    }

    fun calculateCommission(transactions: List<Transaction>): CommissionSummary {
        val totalValueOfTransactions = transactions.map { fromCommaSeparatedString(it.transactionAmount) }.sum()
        return CommissionSummary(
            firstName = transactions[0].customerFirstName,
            lastName = transactions[0].customerLastName,
            customerId = transactions[0].customerId,
            numberOfTransactions = transactions.size,
            totalValueOfTransactions = String.format("%.2f", totalValueOfTransactions),
            transactionsFeeValue = String.format("%.2f", calculateFee(totalValueOfTransactions)),
            lastTransactionDate = getLastDate(transactions)
        )
    }

    private fun getLastDate(transactions: List<Transaction>): String {
        return transactions.maxByOrNull { toDate(it.transactionDate) }!!.transactionDate
    }

    private fun toDate(dateString: String): LocalDate {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return LocalDate.parse(dateString, formatter)
    }

    private fun calculateFee(totalValueOfTransactions: Float): Float {
        for (feeWage in feeWages) {
            if (totalValueOfTransactions < feeWage.transactionLessThanValue.toFloat()) {
                return totalValueOfTransactions * fromCommaSeparatedString(feeWage.feePercentage) * 0.01f
            }
        }
        return totalValueOfTransactions * fromCommaSeparatedString(feeWages.last().feePercentage) * 0.01f
    }

    private fun fromCommaSeparatedString(stringValue: String): Float {
        return stringValue.replace(",", ".").toFloat()
    }
}