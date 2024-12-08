package day06

import java.io.File
import kotlin.collections.indices

fun main() {
    println(partOne())
    println(partTwo())

}
fun partOne(): Int {
    val data = File("day06.txt").readLines()
    val map = data.map { it.toCharArray() }
    val height = map.size
    val width = map[0].size

    val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
    var currDir = 0
    var pos = Pair(0, 0)

    outer@ for (y in map.indices) {
        for (x in map[y].indices) {
            when (map[y][x]) {
                '^' -> {
                    pos = Pair(x, y)
                    currDir = 0
                    break@outer
                }
                '>' -> {
                    pos = Pair(x, y)
                    currDir = 1
                    break@outer
                }
                'v' -> {
                    pos = Pair(x, y)
                    currDir = 2
                    break@outer
                }
                '<' -> {
                    pos = Pair(x, y)
                    currDir = 3
                    break@outer
                }
            }
        }
    }

    val visited = mutableSetOf(pos)

    while (true) {
        val (dx, dy) = directions[currDir]
        val frontPos = Pair(pos.first + dx, pos.second + dy)

        if (frontPos.first !in 0 until width || frontPos.second !in 0 until height) {
            break
        }

        if (map[frontPos.second][frontPos.first] == '#') {
            currDir = (currDir + 1) % 4
        } else {
            pos = frontPos
            visited.add(pos)
        }
    }

    return visited.size
}


fun partTwo(): Int {
    val data = File("day06.txt").readLines()
    val map = data.map { it.toCharArray() }
    val height = map.size
    val width = map[0].size

    val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
    var currDir = 0
    var start = Pair(0, 0)

    outer@ for (y in map.indices) {
        for (x in map[y].indices) {
            when (map[y][x]) {
                '^' -> {
                    start = Pair(x, y)
                    currDir = 0
                    break@outer
                }
                '>' -> {
                    start = Pair(x, y)
                    currDir = 1
                    break@outer
                }
                'v' -> {
                    start = Pair(x, y)
                    currDir = 2
                    break@outer
                }
                '<' -> {
                    start = Pair(x, y)
                    currDir = 3
                    break@outer
                }
            }
        }
    }

    fun causesLoop(obs: Pair<Int, Int>): Boolean {
        val visited = mutableSetOf<Triple<Pair<Int, Int>, Int, Pair<Int, Int>>>()
        var pos = start
        var dir = currDir

        while (true) {
            val state = Triple(pos, dir, obs)
            if (state in visited) return true
            visited.add(state)

            val (dx, dy) = directions[dir]
            val frontPosition = Pair(pos.first + dx, pos.second + dy)

            if (frontPosition.first !in 0 until width || frontPosition.second !in 0 until height) {
                return false
            }

            if (frontPosition == obs || map[frontPosition.second][frontPosition.first] == '#') {
                dir = (dir + 1) % 4
            } else {
                pos = frontPosition
            }
        }
    }

    val poss = mutableSetOf<Pair<Int, Int>>()
    for (y in 0 until height) {
        for (x in 0 until width) {
            if (map[y][x] == '.' && Pair(x, y) != start) {
                poss.add(Pair(x, y))
            }
        }
    }

    return poss.count { causesLoop(it) }
}