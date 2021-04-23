package com.kozakiewicz.interview.util

import com.kozakiewicz.interview.entity.FeeWage
import com.kozakiewicz.interview.entity.Transaction
import com.kozakiewicz.interview.repository.FeeWageRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class CommisionCalculator(private val feeWageRepository: FeeWageRepository) {

    private lateinit var feeWages: List<FeeWage>

    @PostConstruct
    fun getFeeWages() {
        feeWages = feeWageRepository.findAllByOrderByTransactionLessThanValueAsc()
    }

    fun calculateCommision(transactions: List<Transaction>) {
    }
}