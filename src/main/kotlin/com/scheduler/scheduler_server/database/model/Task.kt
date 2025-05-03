package com.scheduler.scheduler_server.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tasks")
data class Task(
    val title: String,
    val description: String?,
    val difficulty: Int,
    val priority: Int,
    val duration: Int,
    val category: String?,
    val startAt: String,
    val finishAt: String,
    val isDone: Boolean = false,
    val isNotified: Boolean = false,
    val userId: ObjectId,
    @Id val id: ObjectId = ObjectId.get()
)
