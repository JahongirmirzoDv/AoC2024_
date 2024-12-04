import java.io.File
import kotlin.math.abs


fun main(){
//    day01()

//    val data = File("day02.txt").readLines().map { it.split(" ").map(String::toInt) }
//    var s = data.count { report(it) }
//    val a = data.count { reportTwo(it) }
//    println(s)
//    println(a)

//    mullItOver("day03.txt")
//    val dd = mullItOverPartTwo("day03.txt")
//    println(dd)

    val f = "day04.txt"
    val grid = File(f).readLines()
    val word = "XMAS"
    println(countWord(grid, word))
    println(countXmas(grid))
}

fun countXmas(grid: List<String>): Int {
    val rows = grid.size
    val cols = grid[0].length
    var count = 0

    fun isWord(r: Int, c: Int): Boolean {
        if (r - 1 < 0 || r + 1 >= rows || c - 1 < 0 || c + 1 >= cols) return false

        val tLeft = grid[r - 1][c - 1]
        val tRight = grid[r - 1][c + 1]
        val center = grid[r][c]
        val bLeft = grid[r + 1][c - 1]
        val bRight = grid[r + 1][c + 1]

        if (center != 'A') return false

        val digValid = (tLeft == 'M' && bRight == 'S') || (tLeft == 'S' && bRight == 'M')
        val digReverseValid = (tRight == 'M' && bLeft == 'S') || (tRight == 'S' && bLeft == 'M')

        return digValid && digReverseValid
    }

    for (r in 0 until rows) {
        for (c in 0 until cols) {
            if (isWord(r, c)) {
                count++
            }
        }
    }
    return count
}

fun countWord(grid: List<String>, word: String): Int {
    val rows = grid.size
    val cols = grid[0].length
    val directions = listOf(
        Pair(0, 1),
        Pair(0, -1),
        Pair(1, 0),
        Pair(-1, 0),
        Pair(1, 1),
        Pair(1, -1),
        Pair(-1, 1),
        Pair(-1, -1)
    )

    fun isWordAt(r: Int, c: Int, dr: Int, dc: Int): Boolean {
        for (i in word.indices) {
            val newR = r + i * dr
            val newC = c + i * dc
            if (newR !in 0 until rows || newC !in 0 until cols || grid[newR][newC] != word[i]) {
                return false
            }
        }
        return true
    }

    var count = 0
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            for ((dr, dc) in directions) {
                if (isWordAt(r, c, dr, dc)) {
                    count++
                }
            }
        }
    }
    return count
}

fun mullItOver(filePath: String){
    val data = File(filePath).readText()

    val mul = Regex("""mul\((\d+),(\d+)\)""")
    val d0 = Regex("""do\(\)""")
    val dont = Regex("""don't\(\)""")

    var isEnable = true
    var total = 0

    val matches = Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""").findAll(data)

    for (i in matches) {
        when {
            d0.matches(i.value) -> isEnable = true
            dont.matches(i.value) -> isEnable = false
            mul.matches(i.value) && isEnable -> {
                val (x, y) = mul.matchEntire(i.value)!!.destructured
                total += x.toInt() * y.toInt()
            }
        }
    }

    println("p1 - $total")
}

fun mullItOverPartTwo(filePath: String): Int {
    val data = File(filePath).readText()

    val pattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

    val matches = pattern.findAll(data)

    val total = matches.sumOf {
        val (x, y) = it.destructured
        x.toInt() * y.toInt()
    }

    return total
}

fun report(report: List<Int>): Boolean {
    val diff = report.zipWithNext { a, b -> b - a }
    val inc = diff.all { it in 1..3 }
    val dec = diff.all { it in -3..-1 }
    return inc || dec
}

fun reportTwo(report: List<Int>): Boolean {
    if (report(report)) return true

    for (i in report.indices) {
        val rep = report.toMutableList().apply { removeAt(i) }
        if (report(rep)) return true
    }
    return false
}


fun day01(){
    val data = File("Day01.txt").readLines().map {
        val pair = it.split(" ")
        pair.first().toInt() to pair.last().toInt()
    }
    val left = data.map { it.first }.sorted()
    val right = data.map { it.second }.sorted()

    var total = 0
    data.indices.forEach { index ->
        total += abs(left[index] - right[index])
    }
    println("$total - total")

    left.sumOf { locationId ->
        locationId * right.count { it == locationId }
    }.also { println(it) }
}