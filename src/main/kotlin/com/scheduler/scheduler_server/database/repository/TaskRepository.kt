package com.scheduler.scheduler_server.database.repository

import com.scheduler.scheduler_server.database.model.Task
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskRepository : MongoRepository<Task, ObjectId> {
    fun findByUserId(userId: ObjectId): List<Task>
}