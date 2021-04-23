package com.kozakiewicz.interview.repository

import com.kozakiewicz.interview.entity.FeeWage
import org.springframework.data.mongodb.repository.MongoRepository

interface FeeWageRepository : MongoRepository<FeeWage, String> {
    fun findAllByOrderByTransactionLessThanValueAsc(): List<FeeWage>
}
