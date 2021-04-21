package com.kozakiewicz.interview

import com.kozakiewicz.interview.entity.Transaction
import com.kozakiewicz.interview.repository.TransactionRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class InterviewApplication

fun main(args: Array<String>) {
	runApplication<InterviewApplication>(*args)
}

@RestController
class MessageResource(private val transactionRepository: TransactionRepository) {

	@GetMapping
	fun index(): List<Message> = listOf(
			Message("3", "Adam", "Adamowski", 11, 1500.00f, 5.33f, "11.12.2020 14:54:31"),
			Message("1", "Marek", "Marecki", 3, 95200.30f, 0.00f, "14.12.2020 11:00:01"),
			Message("2", "Anna", "Annowska", 33, 12531.21f, 0.99f, "31.12.2020 19:21:04")
			)

	@GetMapping("/all")
	fun getAllTransactions(): ResponseEntity<List<Transaction>>{
		val transactions = transactionRepository.findAll()
		return ResponseEntity.ok(transactions)
	}
}

data class Message(
		val id: String?,
		val firstName: String,
		val lastName: String,
		val numberOfTransactions: Number,
		val totalValue: Float,
		val totalFee: Float,
		val lastTransactionDate: String)
