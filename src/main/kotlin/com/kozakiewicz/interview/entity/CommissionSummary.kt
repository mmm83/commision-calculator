package com.kozakiewicz.interview.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "commission_summary")
class CommissionSummary (

    @Id
    val id: ObjectId = ObjectId.get(),
    val firstName: String,
    val lastName: String,
    val customerId: Number,
    val numberOfTransactions: Number,
    val totalValueOfTransactions: String,
    val transactionsFeeValue: String,
    val lastTransactionDate: String,
)