package day02

import java.io.File

fun main() {
    val data = File("day02.txt").readLines().map { it.split(" ").map(String::toInt) }
    var s = data.count { partOne(it) }
    val a = data.count { partTwo(it) }
    println(s)
    println(a)
}

fun partOne(report: List<Int>): Boolean {
    val diff = report.zipWithNext { a, b -> b - a }
    val inc = diff.all { it in 1..3 }
    val dec = diff.all { it in -3..-1 }
    return inc || dec
}

fun partTwo(report: List<Int>): Boolean {
    if (partOne(report)) return true

    for (i in report.indices) {
        val rep = report.toMutableList().apply { removeAt(i) }
        if (partOne(rep)) return true
    }
    return false
}