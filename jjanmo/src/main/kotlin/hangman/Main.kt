package hangman

import kotlinx.coroutines.runBlocking

data class Result(
    val character: Char,
    val isRight: Boolean,
)

const val MAX_WRONG_COUNT = 7

class Hangman(private val word: String) {
    private var wrongCount = 0
    private var results: List<Result> = word.toList().map { Result(it, false) }

    val isEnd: Boolean get() = wrongCount == MAX_WRONG_COUNT
    val isGameCompleted: Boolean get() = results.filter { it.isRight }.size == word.length

    fun checkResults(input: Char): String {
        val prevRightCount = results.filter { it.isRight }.size // 이전 글자 맞춘 개수
        results = results.map {
            if (it.character == input.lowercaseChar()) {
                Result(input, true)
            } else it
        }
        val currentRightCount = results.filter { it.isRight }.size // 현재 글자 맞춘 개수

        return if (prevRightCount == currentRightCount) {
            wrongCount++
            "wrong"
        } else "correct"
    }


    fun displayResults() {
        print("WORD :")
        results.map {
            print(if (it.isRight) " ${it.character}" else " ⎽")
        }
        println() // 한 줄 띄어쓰기
    }

    fun drawHangMan() {
        println("    ⎡‾‾‾‾‾⎤")
        println("    ⎜     ⎟")
        when (wrongCount) {
            0 -> {
                println("          ⎟")
                println("          ⎟")
                println("          ⎟")
                println("          ⎟")
                println()
            }

            1 -> {
                println("    O     ⎟")
                println("          ⎟")
                println("          ⎟")
                println("          ⎟")
                println()
            }

            2 -> {
                println("    O     ⎟")
                println("    ⎟     ⎟")
                println("          ⎟")
                println("          ⎟")
                println()
            }

            3 -> {
                println("    O     ⎟")
                println("   ⎧⎟     ⎟")
                println("          ⎟")
                println("          ⎟")
                println()
            }

            4 -> {
                println("    O     ⎟")
                println("   ⎧⎟⎫    ⎟")
                println("          ⎟")
                println("          ⎟")
                println()
            }

            5 -> {
                println("    O     ⎟")
                println("   ⎧⎟⎫    ⎟")
                println("   ⎛      ⎟")
                println("          ⎟")
                println()
            }

            6 -> {
                println("    O      ⎟")
                println("   ⎧⎟⎫     ⎟")
                println("   ⎛ ⎞     ⎟")
                println("           ⎟")
                println()
            }

            7 -> {
                println("    O      ⎟")
                println("   ⎻⎻⎻     ⎟")
                println("   ⎧⎟⎫     ⎟")
                println("   ⎛ ⎞     ⎟")
                println("           ⎟")
                println(" 댕강! 댕강! ")
                println()
            }
        }
    }
}

fun inputWithValidation(patternRegex: String, preMessage: String, errorMessage: String): String {
    val pattern = Regex(patternRegex)
    println(preMessage)
    while (true) {
        val input = readln()
        if (pattern.matches(input)) return input
        else println(errorMessage)
    }
}

fun main() {
    println("행맨 게임을 시작합니다.")
    val numberOfWord = inputWithValidation(
        "^[1-9]$",
        "게임에서 사용할 단어수를 1~9 사이의 숫자로 입력하세요.",
        "형식에 맞지 않습니다. 다시 입력하세요."
    )

    val word = runBlocking {
        APIClient.fetchWord(numberOfWord.toInt())
    }

    val game = Hangman(word)

    while (true) {
        val inputCharacter = inputWithValidation(
            "^[a-zA-Z]$",
            "a~z 까지의 알파벳 중 1개를 입력하세요.",
            "형식에 맞지 않습니다. 알파벳 1개를 다시 입력하세요."
        )

        val inputResult = game.checkResults(inputCharacter[0])
        if(inputResult == "correct") println("와우 👍🏻")
        else println("땡 😵")

        game.displayResults()
        game.drawHangMan()

        if (game.isGameCompleted) {
            println("🎊 축하합니다. 단어를 완성하였습니다. 🎉")
            return
        }
        if (game.isEnd) {
            println("☠️ 게임 오버! ☠️")
            println("정답은 [$word] 입니다.")
            return
        }
    }
}