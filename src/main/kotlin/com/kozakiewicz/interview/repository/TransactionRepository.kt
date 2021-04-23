package com.kozakiewicz.interview.repository

import com.kozakiewicz.interview.entity.Transaction
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepository : MongoRepository<Transaction, String> {
    fun findAllByCustomerIdIn(vararg values: Number): List<Transaction>
}
