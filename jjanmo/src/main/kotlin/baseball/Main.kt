package baseball

data class GameResult(val strike: Int, val ball: Int, val out: Int)

class Baseball(private val total: Int = 3) {
    val generatedNumbers: List<Int> = (0..9).toList().shuffled().slice(0 until total)
    private var inning: Int = 0

    fun checkNumbers(inputNumbers: List<Int>): GameResult {
        var strikes = 0
        var ball = 0
        var out = 0
        for (i in inputNumbers.indices) {
            when {
                inputNumbers[i] == generatedNumbers[i] -> strikes++
                inputNumbers[i] != generatedNumbers[i] && generatedNumbers.contains(inputNumbers[i]) -> ball++
            }
        }
        if (strikes == 0 && ball == total) out = 1
        inning++
        return GameResult(strikes, ball, out)
    }

    fun printGameResult(result: GameResult) {
        println("$inning 이닝")
        when {
            result.ball == 0 -> println("Strike $result.strike Ball $result.ball")
            result.ball != 0 -> println("$inning Out")
        }
    }
}


fun main() {
    println("0~9 사이의 숫자를 입력하세요.")
    println("입력방법 : [숫자1 숫자2 숫자3] ← 숫자사이 공백 필수")
    val inputNumbers = readln().split(" ").map { it.toInt() }


    val game = Baseball()
    println(inputNumbers)
    println(game.generatedNumbers)

    val result = game.checkNumbers(inputNumbers)
    game.printGameResult(result)

}