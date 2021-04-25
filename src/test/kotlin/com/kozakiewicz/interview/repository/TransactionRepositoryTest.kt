package com.kozakiewicz.interview.repository

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBObject
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
internal class TransactionRepositoryTest {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var transactionRepository: TransactionRepository

    @BeforeAll
    fun setUpClass() {
        createManyObjects(
                arrayOf(4, "400", "Karol", 1, "Styczen", "01.01.2021"),
                arrayOf(1, "123000", "Jan", 3, "Nowak", "12.10.2021"),
                arrayOf(3, "400", "Jan", 3, "Nowak", "13.10.2021"),
                arrayOf(2, "43000", "Adam", 5, "Kowalski", "01.03.2021")
        )

    }

    @Test
    fun shouldFindAllTransactions() {
        val findAll = transactionRepository.findAll()
        assertEquals(findAll.size, 4)
    }

    @Test
    fun shouldFindAllTransactionsForGivenCustomerIDs() {
        val findAllByCustomerIdIn = transactionRepository.findAllByCustomerIdIn(arrayListOf(1, 2, 3))
        assertEquals(findAllByCustomerIdIn.size, 3)
    }

    private fun createManyObjects(vararg data: Array<Any>) {
        for (array: Array<Any> in data) {
            createObject(array)
        }
    }

    private fun createObject(values: Array<Any>) {
        val dbObject: DBObject = BasicDBObjectBuilder.start()
                .add("transaction_id", values[0])
                .add("transaction_amount", values[1])
                .add("customer_first_name", values[2])
                .add("customer_id", values[3])
                .add("customer_last_name", values[4])
                .add("transaction_date", values[5])
                .get()
        mongoTemplate.save(dbObject, "transactions")
    }
}