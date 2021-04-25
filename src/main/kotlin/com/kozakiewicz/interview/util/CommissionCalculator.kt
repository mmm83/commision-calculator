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

    fun calculateCommission(transactions: List<Transaction>): List<CommissionSummary> {
        val totalValueOfTransactions = transactions.map { it.transactionAmount.toFloat() }.sum()
        val commissionSummary = CommissionSummary(
            firstName = transactions[0].customerFirstName,
            lastName = transactions[0].customerLastName,
            customerId = transactions[0].customerId,
            numberOfTransactions = transactions.size,
            totalValueOfTransactions = totalValueOfTransactions,
            transactionsFeeValue = calculateFee(totalValueOfTransactions),
            lastTransactionDate = getLastDate(transactions)
        )
        return arrayListOf<CommissionSummary>(commissionSummary)
    }

    private fun getLastDate(transactions: List<Transaction>): String{
        return transactions.maxByOrNull { toDate(it.transactionDate) }!!.transactionDate
    }

    private fun toDate(dateString: String): LocalDate{
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return LocalDate.parse(dateString, formatter)
    }

    private fun calculateFee(totalValueOfTransactions: Float): Float{
        for (feeWage in feeWages){
            if (totalValueOfTransactions < feeWage.transactionLessThanValue.toFloat()){
                return totalValueOfTransactions * feeWage.feePercentage.toFloat() * 0.01f
            }
        }
        return totalValueOfTransactions * feeWages.last().feePercentage.toFloat() * 0.01f
    }
}