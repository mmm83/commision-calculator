package com.kozakiewicz.interview.repository

import com.kozakiewicz.interview.entity.Transaction
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepository: MongoRepository<Transaction, String> {
    fun findOneById(id: ObjectId): Transaction
}