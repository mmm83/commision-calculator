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
internal class FeeWageRepositoryTest {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var feeWageRepository: FeeWageRepository

    @BeforeAll
    fun setUpClass() {
        createManyObjects(
                arrayOf(100, "5"),
                arrayOf(1000, "2"),
                arrayOf(200, "4"),
        )

    }

    @Test
    fun shouldGetFeeWagesSortedByTransactionValueField() {
        val findAll = feeWageRepository.findAllByOrderByTransactionLessThanValueAsc()
        assertEquals(findAll[0].transactionLessThanValue, 100)
        assertEquals(findAll[1].transactionLessThanValue, 200)
        assertEquals(findAll[2].transactionLessThanValue, 1000)
    }

    private fun createManyObjects(vararg data: Array<Any>) {
        for (array: Array<Any> in data) {
            createObject(array)
        }
    }

    private fun createObject(values: Array<Any>) {
        val dbObject: DBObject = BasicDBObjectBuilder.start()
                .add("transaction_value_less_than", values[0])
                .add("fee_percentage_of_transaction_value", values[1])
                .get()
        mongoTemplate.save(dbObject, "fee_wages")
    }
}