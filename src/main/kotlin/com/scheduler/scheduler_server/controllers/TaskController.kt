package com.scheduler.scheduler_server.controllers

import com.scheduler.scheduler_server.controllers.TaskController.TaskResponse
import com.scheduler.scheduler_server.database.model.Task
import com.scheduler.scheduler_server.database.repository.TaskRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val repository: TaskRepository,
    private val taskRepository: TaskRepository
) {

    data class TaskRequest(
        val id: String?,
        @field:NotBlank(message = "Title for the task can't be blank")
        val title: String,
        val description: String?,
        val difficulty: Int,
        val priority: Int,
        val duration: Int,
        val category: String?,
        @field:NotBlank(message = "StartAt for the task can't be blank")
        val startAt: String,
        @field:NotBlank(message = "FinishAt for the task can't be blank")
        val finishAt: String,
        val isDone: Boolean = false,
        val isNotified: Boolean = false
    )

    data class TaskResponse(
        val id: String,
        val title: String,
        val description: String?,
        val difficulty: Int,
        val priority: Int,
        val duration: Int,
        val category: String?,
        val startAt: String,
        val finishAt: String,
        val isDone: Boolean = false,
        val isNotified: Boolean = false
    )

    @PostMapping
    fun save(
        @Valid @RequestBody body: TaskRequest
    ): TaskResponse {
        val userId = SecurityContextHolder.getContext().authentication.principal as String
        val task = repository.save(
            Task(
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                description = body.description,
                difficulty = body.difficulty,
                priority = body.priority,
                duration = body.duration,
                category = body.category,
                startAt = body.startAt,
                finishAt = body.finishAt,
                isDone = body.isDone,
                isNotified = body.isNotified,
                userId = ObjectId(userId)
            )
        )

        return task.toResponse()
    }

    @GetMapping
    fun findByUserId(): List<TaskResponse> {
        val userId = SecurityContextHolder.getContext().authentication.principal as String
        return repository.findByUserId(ObjectId(userId)).map {
            it.toResponse()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val task = taskRepository.findById(ObjectId(id)).orElseThrow {
            IllegalArgumentException("Task not found")
        }
        val userId = SecurityContextHolder.getContext().authentication.principal as String
        if(task.userId.toHexString() == userId) {
            repository.deleteById(ObjectId(id))
        }
    }
}

private fun Task.toResponse(): TaskResponse {
    return TaskResponse(
        id = id.toHexString(),
        title = title,
        description = description,
        difficulty = difficulty,
        priority = priority,
        duration = duration,
        category = category,
        startAt = startAt,
        finishAt = finishAt,
        isDone = isDone,
        isNotified = isNotified
    )
}