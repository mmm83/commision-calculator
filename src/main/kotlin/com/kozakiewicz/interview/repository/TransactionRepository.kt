package com.kozakiewicz.interview.repository

import com.kozakiewicz.interview.entity.Transaction
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepository : MongoRepository<Transaction, String> {
    fun findAllByCustomerIdIn(values: List<Number>): List<Transaction>
}
