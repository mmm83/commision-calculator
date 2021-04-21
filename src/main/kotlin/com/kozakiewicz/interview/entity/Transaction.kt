package com.kozakiewicz.interview.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "transactions")
class Transaction (

    @Id
    val id: ObjectId = ObjectId.get(),
    val transaction_id: Number,
    val transaction_amount: String,
    val customer_first_name: String,
    val customer_id: Number,
    val customer_last_name: String,
    val transaction_date: String

)