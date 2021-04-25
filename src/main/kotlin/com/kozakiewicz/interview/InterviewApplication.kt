package com.kozakiewicz.interview

import com.kozakiewicz.interview.entity.Transaction
import com.kozakiewicz.interview.repository.TransactionRepository
import com.kozakiewicz.interview.util.CommissionCalculator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class InterviewApplication

fun main(args: Array<String>) {
    runApplication<InterviewApplication>(*args)
}

@RestController
class CommissionCalculatorRestController(
    private val transactionRepository: TransactionRepository,
    private val commissionCalculator: CommissionCalculator
) {

    @GetMapping("/all")
    fun getAllTransactions(): ResponseEntity<List<Transaction>> {
        val transactions = transactionRepository.findAll()
        return ResponseEntity.ok(transactions)
    }

    @GetMapping("/api/commission")
    fun calculateCommissionFor(@RequestParam(name = "customer_id") value: String): ResponseEntity<List<Transaction>> {
        if (value == "ALL" || value.isEmpty()) {
            val transactions = transactionRepository.findAll()
            return ResponseEntity.ok(transactions)
        }
        val customerIDs = value.split(",").map { it.toInt() }
        val findAllByCustomerIdIn = transactionRepository.findAllByCustomerIdIn(customerIDs)
        commissionCalculator.calculateCommission(findAllByCustomerIdIn)
        return ResponseEntity.ok(findAllByCustomerIdIn)
    }
}
