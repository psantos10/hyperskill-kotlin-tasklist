package tasklist

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class TaskListApplication {
    private val repository = TaskRepository()

    fun run() {
        var action: String

        do {
            println("Input an action (add, print, edit, delete, end):")
            action = readln().lowercase().trim()

            when (action) {
                "add" -> addNewTask()
                "print" -> printTasks()
                "edit" -> editTask()
                "delete" -> deleteTask()
                "end" -> {
                    repository.commit()
                    println("Tasklist exiting!")
                }
                else -> println("The input action is invalid")
            }
        } while (action != "end")
    }

    private fun addNewTask() {
        val priority: Char = promptForPriority()
        val date: LocalDate = promptForDate()
        val dateTime: LocalDateTime = promptForTime(date)
        val lines: List<String> = promptForTaskLines()

        val newTask = Task(priority, dateTime, lines)

        repository.addTask(newTask)
    }

    private fun editTask() {
        printTasks()

        if (repository.isEmpty()) return

        while (true) {
            try {
                val input = promptForTaskNumber()

                inputFieldToEditLoop@ while (true) {
                    println("Input a field to edit (priority, date, time, task):")
                    val field = readln().trim().lowercase()
                    val taskIndex = input - 1

                    when (field) {
                        "priority" -> {
                            val priority: Char = promptForPriority()
                            val task = repository.getTask(taskIndex)
                            val newTask = Task(priority, task.dateTime, task.lines)

                            repository.updateTask(taskIndex, newTask)

                            println("The task is changed")
                            return
                        }
                        "date" -> {
                            val task = repository.getTask(taskIndex)

                            val date: LocalDate = promptForDate()
                            val time: LocalDateTime = task.dateTime
                            val localDateTime = toLocalDateTime(date, time)

                            val newTask = Task(task.priority, localDateTime, task.lines)
                            repository.updateTask(taskIndex, newTask)

                            println("The task is changed")
                            return
                        }
                        "time" -> {
                            val task = repository.getTask(taskIndex)

                            val date: LocalDate = task.dateTime.date
                            val time: LocalDateTime = promptForTime(date)
                            val localDateTime = toLocalDateTime(date, time)

                            val newTask = Task(task.priority, localDateTime, task.lines)
                            repository.updateTask(taskIndex, newTask)

                            println("The task is changed")
                            return
                        }
                        "task" -> {
                            val task = repository.getTask(taskIndex)

                            val lines: List<String> = promptForTaskLines()
                            val newTask = Task(task.priority, task.dateTime, lines)

                            repository.updateTask(taskIndex, newTask)

                            println("The task is changed")
                            return
                        }
                        else -> println("Invalid field")
                    }
                }
            } catch (e: Exception) {
                println("Invalid task number")
            }
        }
    }

    private fun deleteTask() {
        printTasks()

        if (repository.isEmpty()) return

        while (true) {
            try {
                val input = promptForTaskNumber()

                repository.removeTask(input - 1)
                println("The task is deleted")
                return
            } catch (e: Exception) {
                println("Invalid task number")
            }
        }
    }

    private fun toLocalDateTime(date: LocalDate, time: LocalDateTime): LocalDateTime {
        return LocalDateTime(date.year, date.monthNumber, date.dayOfMonth, time.hour, time.minute)
    }

    private fun promptForTaskNumber(): Int {
        println("Input the task number (1-${repository.size}):")
        val input = readln().toInt()

        if (input !in 1..repository.size) throw Exception("Invalid task number")

        return input
    }

    private fun promptForTaskLines(): List<String> {
        while (true) {
            println("Input a new task (enter a blank line to end):")
            val lines = generateSequence { readlnOrNull()?.trim() }
                .takeWhile { it.isNotBlank() }
                .toList()

            if (lines.isEmpty()) println("The task is blank")

            return lines
        }
    }

    private fun promptForPriority(): Char {
        val validPriorities = listOf('C', 'H', 'N', 'L')

        while (true) {
            println("Input the task priority (C, H, N, L):")
            val input = readln().trim().uppercase()

            if (input.length == 1 && input[0] in validPriorities) {
                return input[0]
            }
        }
    }

    private fun promptForDate(): LocalDate {
        while (true) {
            println("Input the date (yyyy-mm-dd):")
            try {
                val (inputYear, inputMonth, inputDay) = readln().trim().split('-').map { it.toInt() }
                return LocalDate(inputYear, inputMonth, inputDay)
            } catch (e: Exception) {
                println("The input date is invalid")
            }
        }
    }

    private fun promptForTime(date: LocalDate): LocalDateTime {
        while (true) {
            println("Input the time (hh:mm):")
            try {
                val (hours, minutes) = readln().trim().split(':').map { it.toInt() }
                return LocalDateTime(date.year, date.monthNumber, date.dayOfMonth, hours, minutes)
            } catch (e: Exception) {
                println("The input time is invalid")
            }
        }
    }

    private fun printTasks() {
        if (repository.isEmpty()) {
            println("No tasks have been input")
            return
        }

        val table = Table()
        table.printHeader()

        repository.getTasks().forEachIndexed { index, task ->
            table.printFirstRowLine(index + 1, task)
            table.printRestRowLines(task)
        }
    }
}