package day04

import java.io.File

fun main() {
    val f = "day04.txt"
    val grid = File(f).readLines()
    val word = "XMAS"
    println(partOne(grid, word))
    println(partTwo(grid))
}

fun partTwo(grid: List<String>): Int {
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

fun partOne(grid: List<String>, word: String): Int {
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