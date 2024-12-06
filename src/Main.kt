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

//    val f = "day04.txt"
//    val grid = File(f).readLines()
//    val word = "XMAS"
//    println(countWord(grid, word))
//    println(countXmas(grid))

//    dayFifePartOne()
//    dayFifePartTwo()

    val result = partOne()
    println(result)

    val result2 = partwo()
    println(result2)
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


fun partwo(): Int {
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

fun dayFifePartTwo() {
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

fun dayFifePartOne(){
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