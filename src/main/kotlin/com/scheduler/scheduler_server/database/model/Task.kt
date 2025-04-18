package com.scheduler.scheduler_server.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("tasks")
data class Task(
    val title: String,
    val description: String?,
    val difficulty: Int,
    val priority: Int,
    val duration: Int,
    val category: String?,
    val startAt: Instant,
    val finishAt: Instant,
    val userId: ObjectId,
    @Id val id: ObjectId = ObjectId.get()
)
