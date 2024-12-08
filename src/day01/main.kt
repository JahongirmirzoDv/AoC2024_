package day01

import java.io.File
import kotlin.math.abs

fun main() {
    partOneAndTwo()
}

fun partOneAndTwo(){
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