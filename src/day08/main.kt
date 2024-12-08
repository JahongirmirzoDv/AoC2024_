package day08

import java.io.File

fun inBound(x: Int, y: Int, map: List<String>): Boolean {
    return x in map.indices && y in map[x].indices
}

fun sSum(a: Pair<Int, Int>, b: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(a.first + b.first, a.second + b.second)
}

fun sDif(a: Pair<Int, Int>, b: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(a.first - b.first, a.second - b.second)
}

fun partOne(map: List<String>, nodes: Map<Char, List<Pair<Int, Int>>>): Int {
    val node1 = mutableSetOf<Pair<Int, Int>>()

    for ((_, pos) in nodes) {
        for (i in pos.indices) {
            for (j in i + 1 until pos.size) {
                val a = pos[i]
                val b = pos[j]
                val v = sDif(b, a)

                var pos = sDif(a, v)
                if (inBound(pos.first, pos.second, map)) {
                    node1.add(pos)
                }
                pos = sSum(b, v)
                if (inBound(pos.first, pos.second, map)) {
                    node1.add(pos)
                }
            }
        }
    }

    return node1.size
}

fun partTwo(map: List<String>, nodes: Map<Char, List<Pair<Int, Int>>>): Int {
    val node2 = mutableSetOf<Pair<Int, Int>>()

    for ((_, pos) in nodes) {
        for (i in pos.indices) {
            for (j in i + 1 until pos.size) {
                val a = pos[i]
                val b = pos[j]
                val v = sDif(b, a)

                var pos = a
                while (inBound(pos.first, pos.second, map)) {
                    node2.add(pos)
                    pos = sDif(pos, v)
                }

                pos = b
                while (inBound(pos.first, pos.second, map)) {
                    node2.add(pos)
                    pos = sSum(pos, v)
                }
            }
        }
    }

    return node2.size
}

fun main() {
    val data = File("day08.txt").readLines()
    val n = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    data.forEachIndexed { i, row ->
        row.forEachIndexed { j, char ->
            if (char != '.') {
                n.computeIfAbsent(char) { mutableListOf() }.add(Pair(i, j))
            }
        }
    }

    println(partOne(data, n))
    println( partTwo(data, n))
}
