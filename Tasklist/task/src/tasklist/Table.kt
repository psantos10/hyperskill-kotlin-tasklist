package tasklist

const val TABLE_ROW_SEPARATOR = "+----+------------+-------+---+---+--------------------------------------------+"
const val TABLE_COLUMN_SEPARATOR = "|"

class Table {
    fun printHeader() {
        println(TABLE_ROW_SEPARATOR)
        print(TABLE_COLUMN_SEPARATOR)
        print(" ${"N".padEnd(3, ' ')}")
        print(TABLE_COLUMN_SEPARATOR)
        print("Date".center(12, ' '))
        print(TABLE_COLUMN_SEPARATOR)
        print("Time".center(7, ' '))
        print(TABLE_COLUMN_SEPARATOR)
        print("P".center(3, ' '))
        print(TABLE_COLUMN_SEPARATOR)
        print("D".center(3, ' '))
        print(TABLE_COLUMN_SEPARATOR)
        print("Task".padStart("Task".length + 19).padEnd("Task".length + 19 + 21, ' '))
        println(TABLE_COLUMN_SEPARATOR)
        println(TABLE_ROW_SEPARATOR)
    }

    fun printFirstRowLine(id: Int, task: Task) {
        val time = "${String.format("%02d", task.dateTime.hour)}:${String.format("%02d", task.dateTime.minute)}"
        val priorityColor = getPriorityColor(task.priority)
        val dueTagColor = getDueTagColor(task.dueTag())

        print(TABLE_COLUMN_SEPARATOR)
        print(" ${id.toString().padEnd(3, ' ')}")
        print(TABLE_COLUMN_SEPARATOR)
        print(task.dateTime.date.toString().center(12, ' '))
        print(TABLE_COLUMN_SEPARATOR)
        print(time.center(7, ' '))
        print(TABLE_COLUMN_SEPARATOR)
        print(" $priorityColor ")
        print(TABLE_COLUMN_SEPARATOR)
        print(" $dueTagColor ")
        print(TABLE_COLUMN_SEPARATOR)
        print(task.flattenLines().first().padEnd(44, ' '))
        println(TABLE_COLUMN_SEPARATOR)
    }

    fun printRestRowLines(task: Task) {
        task.flattenLines().drop(1).forEach {
            print(TABLE_COLUMN_SEPARATOR)
            print(" ${" ".padEnd(3, ' ')}")
            print(TABLE_COLUMN_SEPARATOR)
            print(" ".center(12, ' '))
            print(TABLE_COLUMN_SEPARATOR)
            print(" ".center(7, ' '))
            print(TABLE_COLUMN_SEPARATOR)
            print(" ".center(3, ' '))
            print(TABLE_COLUMN_SEPARATOR)
            print(" ".center(3, ' '))
            print(TABLE_COLUMN_SEPARATOR)
            print(it.padEnd(44, ' '))
            println(TABLE_COLUMN_SEPARATOR)
        }

        println(TABLE_ROW_SEPARATOR)
    }

    private fun getPriorityColor(priority: Char): String {
        return when (priority) {
            'C' -> "\u001B[101m \u001B[0m"
            'H' -> "\u001B[103m \u001B[0m"
            'N' -> "\u001B[102m \u001B[0m"
            else -> "\u001B[104m \u001B[0m"
        }
    }

    private fun getDueTagColor(dueTag: Char): String {
        return when (dueTag) {
            'O' -> "\u001B[101m \u001B[0m"
            'T' -> "\u001B[103m \u001B[0m"
            else -> "\u001B[102m \u001B[0m"
        }
    }

    private fun String.center(width: Int, padChar: Char = ' '): String {
        val padding = (width - this.length) / 2
        val padStart = this.padStart(this.length + padding, padChar)

        return padStart.padEnd(width, padChar)
    }
}