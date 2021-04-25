package com.kozakiewicz.interview.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "transactions")
class Transaction (

    @Id
    val id: ObjectId = ObjectId.get(),
    @Field("transaction_id")
    val transactionId: Number,
    @Field("transaction_amount")
    val transactionAmount: String,
    @Field("customer_first_name")
    val customerFirstName: String,
    @Field("customer_id")
    val customerId: Number,
    @Field("customer_last_name")
    val customerLastName: String,
    @Field("transaction_date")
    val transactionDate: String

)