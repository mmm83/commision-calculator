package com.kozakiewicz.interview.util

import com.kozakiewicz.interview.repository.FeeWageRepository
import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate

@TestConfiguration
class CommissionCalculatorTestContextConfiguration {

    @Autowired
    private lateinit var feeWageRepository: FeeWageRepository

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Bean
    fun commissionCalculator(): CommissionCalculator{
        createFeeWages(
            arrayOf(100, "5,0"),
            arrayOf(200, "4"),
            arrayOf(1000, "2")
        )
        val commissionCalculator = CommissionCalculator(feeWageRepository)
        commissionCalculator.getFeeWages()
        return commissionCalculator
    }

    private fun createFeeWages(vararg data: Array<Any>) {
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