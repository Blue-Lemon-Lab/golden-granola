package baseball

const val LAST_INNING = 9

data class GameResult(val strike: Int, val ball: Int, val out: Int, val inputNumbers: List<Int>)

class BaseballGame(private val total: Int = 3) {
    private val generatedNumbers: List<Int> = (0..9).toList().shuffled().slice(0 until total)
    var inning: Int = 0
    val records: MutableList<Map<String, GameResult>> = mutableListOf()


    private fun updateRecords(gameResult: GameResult) {
        val recordMap = mapOf("${inning}이닝" to gameResult)
        records.add(recordMap)
    }

    fun printRecord(inning: Int, gameRecordMap: Map<String, GameResult>) {
        val (strike, ball, out, inputNumbers) = gameRecordMap["${inning}이닝"] ?: GameResult(0, 0, 0, emptyList())
        when {
            out == 0 -> println("✔️${inning}이닝 \n→ 입력숫자 ${inputNumbers.joinToString(" ")}\n→ Strike $strike | Ball $ball  \uD83D\uDC4D\uD83C\uDFFB")
            out != 0 -> println("✔️${inning}이닝 \n→ 입력숫자 ${inputNumbers.joinToString(" ")}\n→ 아웃 ⚾️")
        }
    }

    fun checkNumbers(inputNumbers: List<Int>) {
        var strikes = 0
        var ball = 0
        var out = 0

        for (i in inputNumbers.indices) {
            when {
                inputNumbers[i] == generatedNumbers[i] -> strikes++
                inputNumbers[i] != generatedNumbers[i] && generatedNumbers.contains(inputNumbers[i]) -> ball++
            }
        }
        if (strikes == 0 && ball == 0) out = 1
        inning++

        updateRecords(GameResult(strikes, ball, out, inputNumbers))
    }
}

fun askToContinue(strike: Int): Boolean {
    while (true) {
        if (strike == 3) {
            println("경기를 종료합니다.")
            return false
        } else println("다음 이닝으로 넘어갑니다. 진행하시겠습니까?(Y/n)")

        val answer = readln()
        when {
            answer == "Y" || answer == "y" || answer.isEmpty() -> return true
            answer == "n" || answer == "N" -> return false
            else -> println("입력이 잘못되었습니다.")
        }
    }
}


fun main() {
    val game = BaseballGame()
    var isStarted = true

    while (isStarted && game.inning < LAST_INNING) {
        println("0 ~ 9 사이의 숫자를 입력하세요.")
        if (game.inning == 0) println("입력방법 : [숫자1 숫자2 숫자3] ← 숫자사이 공백 필수")

        val inputNumbers = readln().split(" ").map { it.toInt() }

        game.checkNumbers(inputNumbers)
        game.records.mapIndexed() { index, record ->
            game.printRecord(index + 1, record)
        }

        if (game.inning == LAST_INNING) println("모든 이닝이 끝나서 경기를 종료합니다.")
        else {
            val currentInningStrike = game.records.last()["${game.inning}이닝"]?.strike ?: 0
            isStarted = askToContinue(currentInningStrike)
        }
    }
}