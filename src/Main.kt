import java.io.File
import kotlin.math.abs


fun main(){
//    day01()

//    val data = File("day02.txt").readLines().map { it.split(" ").map(String::toInt) }
//    var s = data.count { report(it) }
//    val a = data.count { reportTwo(it) }
//    println(s)
//    println(a)

    mullItOver("day03.txt")
    val dd = mullItOverPartTwo("day03.txt")
    println(dd)
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