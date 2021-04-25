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
    fun shouldCalculateCommissionForSingleCustomer() {
        val result = commissionCalculator.calculateCommission(
            arrayListOf<Transaction>(
                Transaction(ObjectId.get(), 1, "100,0", "Ed", 1, "White", "11.12.2020 14:57:22"),
                Transaction(ObjectId.get(), 2, "200,0", "Ed", 1, "White", "12.12.2020 10:27:01")
            )
        )
        assertAll(
            { assertEquals(result.firstName, "Ed") },
            { assertEquals(result.lastName, "White") },
            { assertEquals(result.customerId, 1) },
            { assertEquals(result.numberOfTransactions, 2) },
            { assertEquals(result.totalValueOfTransactions, "300,00") },
            { assertEquals(result.transactionsFeeValue, "6,00") },
            { assertEquals(result.lastTransactionDate, "12.12.2020 10:27:01") }
        )

    }

    @Test
    fun shouldCalculateCommissionForManyCustomers() {
        val result = commissionCalculator.calculateAllCommissions(
            arrayListOf<Transaction>(
                Transaction(ObjectId.get(), 1, "10,0", "Customer1", 1, "LastName1", "11.12.2020 14:57:22"),
                Transaction(ObjectId.get(), 2, "2,0", "Customer2", 2, "LastName2", "12.12.2019 10:27:01"),
                Transaction(ObjectId.get(), 3, "100,09", "Customer3", 3, "LastName3", "11.12.2015 14:57:22"),
                Transaction(ObjectId.get(), 4, "200,0", "Customer2", 2, "LastName2", "12.12.2000 10:27:01"),
                Transaction(ObjectId.get(), 5, "0,50", "Customer4", 4, "LastName4", "01.01.2020 14:57:22"),
                Transaction(ObjectId.get(), 6, "2003,01", "Customer3", 3, "LastName3", "12.12.2018 10:27:01")
            )
        )
        assertAll(
            { assertEquals(result.size, 4) },
            { assertEquals(result[0].firstName, "Customer1") },
            { assertEquals(result[0].lastName, "LastName1") },
            { assertEquals(result[0].customerId, 1) },
            { assertEquals(result[0].numberOfTransactions, 1) },
            { assertEquals(result[0].totalValueOfTransactions, "10,00") },
            { assertEquals(result[0].transactionsFeeValue, "0,50") },
            { assertEquals(result[0].lastTransactionDate, "11.12.2020 14:57:22") },
            { assertEquals(result[1].firstName, "Customer2") },
            { assertEquals(result[1].lastName, "LastName2") },
            { assertEquals(result[1].customerId, 2) },
            { assertEquals(result[1].numberOfTransactions, 2) },
            { assertEquals(result[1].totalValueOfTransactions, "202,00") },
            { assertEquals(result[1].transactionsFeeValue, "4,04") },
            { assertEquals(result[1].lastTransactionDate, "12.12.2019 10:27:01") },
            { assertEquals(result[2].firstName, "Customer3") },
            { assertEquals(result[2].lastName, "LastName3") },
            { assertEquals(result[2].customerId, 3) },
            { assertEquals(result[2].numberOfTransactions, 2) },
            { assertEquals(result[2].totalValueOfTransactions, "2103,10") },
            { assertEquals(result[2].transactionsFeeValue, "42,06") },
            { assertEquals(result[2].lastTransactionDate, "12.12.2018 10:27:01") },
            { assertEquals(result[3].firstName, "Customer4") },
            { assertEquals(result[3].lastName, "LastName4") },
            { assertEquals(result[3].customerId, 4) },
            { assertEquals(result[3].numberOfTransactions, 1) },
            { assertEquals(result[3].totalValueOfTransactions, "0,50") },
            { assertEquals(result[3].transactionsFeeValue, "0,02") },
            { assertEquals(result[3].lastTransactionDate, "01.01.2020 14:57:22") }
        )
    }

}