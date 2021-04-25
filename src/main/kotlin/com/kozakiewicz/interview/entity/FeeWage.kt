package com.kozakiewicz.interview.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "fee_wages")
class FeeWage (

    @Id
    val id: ObjectId = ObjectId.get(),
    @Field("transaction_value_less_than")
    val transactionLessThanValue: Number,
    @Field("fee_percentage_of_transaction_value")
    val feePercentage: String,

)