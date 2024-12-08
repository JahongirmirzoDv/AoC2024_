package day05

import java.io.File

fun main() {
    partOne()
    partTwo()
}

fun partTwo() {
    val data = File("day05.txt").readText().trim()
    val (rule, update) = data.split("\n\n")
    val order = rule.lines().map { it.split("|").let { (x, y) -> x.toInt() to y.toInt() } }

    val updates = update.lines().map { it.split(",").map(String::toInt) }

    val (_, inc) = updates.partition { i ->
        val n = order.filter { (x, y) -> x in i && y in i }
        n.all { (x, y) -> i.indexOf(x) < i.indexOf(y) }
    }
    val corr = inc.map { i ->
        val n = order.filter { (x, y) -> x in i && y in i }
        sort(i.toSet(), n)
    }

    val sum = corr.sumOf { i ->
        val n = i.size / 2
        i[n]
    }

    println(sum)
}
fun sort(nodes: Set<Int>, rules: List<Pair<Int, Int>>): List<Int> {
    val d = mutableMapOf<Int, Int>().withDefault { 0 }
    val list = mutableMapOf<Int, MutableList<Int>>()

    nodes.forEach { node ->
        d[node] = 0
        list[node] = mutableListOf()
    }
    rules.forEach { (x, y) ->
        list[x]?.add(y)
        d[y] = d.getValue(y) + 1
    }

    val sort = mutableListOf<Int>()
    val is0 = ArrayDeque<Int>().apply {
        addAll(d.filterValues { it == 0 }.keys)
    }

    while (is0.isNotEmpty()) {
        val curr = is0.removeFirst()
        sort.add(curr)
        list[curr]?.forEach { i ->
            d[i] = d.getValue(i) - 1
            if (d.getValue(i) == 0) {
                is0.addLast(i)
            }
        }
    }

    return sort
}

fun partOne(){
    val data = File("day05.txt").readText().trim()
    val (rule, update) = data.split("\n\n")
    val ordered = rule.lines().map { it.split("|").let { (x, y) -> x.toInt() to y.toInt() } }

    val updates = update.lines().map { it.split(",").map(String::toInt) }

    val corr = updates.filter { i ->
        val r = ordered.filter { (x, y) -> x in i && y in i }
        r.all { (x, y) -> i.indexOf(x) < i.indexOf(y) }
    }

    val sum = corr.sumOf { update ->
        val n = update.size / 2
        update[n]
    }
    println(sum)
}