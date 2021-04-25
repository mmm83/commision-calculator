package com.kozakiewicz.interview.util

import com.kozakiewicz.interview.entity.Transaction
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import

@Import(CommissionCalculatorTestContextConfiguration::class)
@DataMongoTest
internal class CommissionCalculatorTest {

    @Autowired
    private lateinit var commissionCalculator: CommissionCalculator

    @Test
    fun calculateCommission() {
        val result = commissionCalculator.calculateCommission(
            arrayListOf<Transaction>(
                Transaction(ObjectId.get(), 1, "100", "Ed", 1, "White", "11.12.2020 14:57:22"),
                Transaction(ObjectId.get(), 2, "200", "Ed", 1, "White", "12.12.2020 10:27:01")
            )
        )
        assertAll(
            { assertEquals(result.size, 1) },
            { assertEquals(result[0].firstName, "Ed") },
            { assertEquals(result[0].lastName, "White") },
            { assertEquals(result[0].customerId, 1) },
            { assertEquals(result[0].numberOfTransactions, 2) },
            { assertEquals(result[0].totalValueOfTransactions, 300f) },
            { assertEquals(result[0].transactionsFeeValue, 6f) },
            { assertEquals(result[0].lastTransactionDate, "12.12.2020 10:27:01") }
        )

    }

}