import kotlin.random.Random

fun main() {
    var attempts = 0

    println("⚾️숫자 야구 게임을 시작합니다! 9회 안에 맞춰보세요💪")
    println("게임 레벨을 선택하세요. 레벨에 따라 맞춰야할 3가지 숫자 범위가 달라집니다.")
    println("1. Easy(1 ~ 5 숫자)\n2. Normal(1 ~ 9 숫자)\n3. Hard(0 ~ 20)")

    val level = chooseLevel()
    val secretNumber = generateSecretNumber(level)
    println("레벨을 세팅했습니다. 게임을 시작합니다!")

    while (true) {
        val guessList = mutableListOf<Int>()

        for (i in 1..3) {
            while (true) {
                print("$i 번째 숫자를 입력하세요: ")
                val guess = readln().toIntOrNull()
                if (guess != null && isValidInput(guess, level)) {
                    guessList.add(guess)
                    break
                } else {
                    println("유효하지 않은 입력입니다. 다시 입력해주세요.")
                }
            }
        }

        attempts++
        val (strikes, balls) = calculateResult(secretNumber, guessList)

        println("결과: 🤗$strikes 스트라이크, 🤔$balls 볼")

        if (attempts == 9) {
            println("아쉽네요 졌어요: $secretNumber")
            return
        }

        if (strikes == 3) {
            println("축하합니다! $attempts 번 만에 맞추셨습니다.")
            println("비밀 번호는 ${secretNumber.joinToString(", ")} 였습니다.")
            break
        }
    }
}

fun chooseLevel(): Int {
    while (true) {
        println("1, 2, 3 중 하나의 숫자를 입력하세요:")
        val level = readln().toIntOrNull()
        if (level != null && level in 1..3) {
            return level
        }
        println("잘못된 입력입니다. 다시 시도해주세요.")
    }
}


fun generateSecretNumber(level: Int): List<Int> {
    val range = when (level) {
        1 -> 1..5
        2 -> 1..9
        3 -> 1..20
        else -> throw IllegalArgumentException("Invalid level")
    }

    val secretSet = mutableSetOf<Int>()
    while (secretSet.size < 3) {
        secretSet.add(range.random())
    }

    return secretSet.toList()
}

fun isValidInput(input: Int, level: Int): Boolean {
    return when (level) {
        1 -> input in 1..5
        2 -> input in 1..9
        3 -> input in 1..20
        else -> false
    }
}

fun calculateResult(secret: List<Int>, guess: List<Int>): Pair<Int, Int> {
    var strikes = 0
    var balls = 0

    for (i in secret.indices) {
        when {
            secret[i] == guess[i] -> strikes++
            guess.contains(secret[i]) -> balls++
        }
    }

    return Pair(strikes, balls)
}