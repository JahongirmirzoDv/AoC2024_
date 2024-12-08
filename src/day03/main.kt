package day03

import java.io.File

fun main() {
    partOne("day03.txt")
    val dd = partTwo("day03.txt")
    println(dd)
}

fun partOne(filePath: String) {
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

fun partTwo(filePath: String): Int {
    val data = File(filePath).readText()

    val pattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

    val matches = pattern.findAll(data)

    val total = matches.sumOf {
        val (x, y) = it.destructured
        x.toInt() * y.toInt()
    }

    return total
}