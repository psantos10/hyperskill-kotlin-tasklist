package tasklist

import java.io.File
import com.squareup.moshi.*
import kotlinx.datetime.LocalDateTime

class TaskRepository {
    private val tasks = mutableListOf<Task>()

    init {
        val jsonFile = File("tasklist.json")
        if (jsonFile.exists()) {
            val json = jsonFile.readText()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(LocalDateTimeJsonAdapter())
                .build()

            val adapter = moshi.adapter(Array<Task>::class.java)

            val taskArray = adapter.fromJson(json)
            if (taskArray != null) {
                tasks.addAll(taskArray)
            }
        }
    }

    val size: Int
        get() = tasks.size

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun getTask(index: Int): Task {
        return tasks[index]
    }

    fun getTasks(): List<Task> {
        return tasks
    }

    fun removeTask(index: Int) {
        tasks.removeAt(index)
    }

    fun updateTask(index: Int, task: Task) {
        tasks[index] = task
    }

    fun isEmpty(): Boolean {
        return tasks.isEmpty()
    }

    fun commit() {
        val jsonFile = File("tasklist.json")

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(LocalDateTimeJsonAdapter())
            .build()

        val adapter = moshi.adapter(Array<Task>::class.java)
        val json = adapter.toJson(tasks.toTypedArray())

        jsonFile.writeText(json)
    }
}