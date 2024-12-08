package day07

import java.io.File
import kotlin.math.pow

fun main() {
    println(partOne())
    println(partTwo())
}

fun partTwo(): Long {
    val data = File("day07.txt").readText()
    val eq = parse(data)
    return eq.sumOf { (t, n) ->
        if (checkTwo(t, n)) t else 0L
    }
}

fun checkTwo(target: Long, numbers: List<Long>): Boolean{
    val count = numbers.size - 1
    val comb = 3.0.pow(count).toInt()

    for (i in 0 until comb) {
        var res = numbers[0]
        var curr = i
        for (k in 0 until count) {
            val operator = curr % 3
            val number = numbers[k + 1]
            res = when (operator) {
                0 -> res + number
                1 -> res * number
                2 -> (res.toString() + number.toString()).toLong()
                else -> res
            }
            curr /= 3
        }
        if (res == target) return true
    }
    return false
}

fun partOne(): Long {
    val data = File("day07.txt").readText()
    val eq = parse(data)
    return eq.sumOf { (t, n) ->
        if (check(t, n)) t else 0L
    }
}

fun parse(input: String): List<Pair<Long, List<Long>>> {
    return input.lines().filter { it.isNotBlank() }.map { line ->
        val p = line.split(":")
        val t = p[0].trim().toLong()
        val num = p[1].trim().split(" ").map { it.toLong() }
        t to num
    }
}

fun check(target: Long, numbers: List<Long>): Boolean {
    val count = numbers.size - 1
    val total = 1 shl count

    for (i in 0 until total) {
        var res = numbers[0]
        for (k in 0 until count) {
            val op = (i shr k) and 1
            val num = numbers[k + 1]
            res = if (op == 0) res + num else res * num
        }
        if (res == target) return true
    }
    return false
}
