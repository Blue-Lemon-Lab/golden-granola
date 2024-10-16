import kotlin.random.Random

fun main() {
    val answer = generateAnswer()
    var attempts = 0

    println("⚾ 숫자 야구 게임을 시작합니다! 3자리 숫자를 맞춰보세요. ⚾")

    while (true) {
        print("숫자를 입력하세요: ")
        val input = readLine()?.trim() // 콘솔 입력 읽어서 input 할당

        // 입력값 유효성 체크
        if (input == null || input.length != 3 || !input.all { it.isDigit() }) {
            println("🚨 유효한 3자리 숫자를 입력해주세요.🚨")
            continue
        }

        attempts++
        val (strike, ball) = checkGuess(answer, input)

        // strik 가 3인 경우, 모두 맞췄다 판단하고 종료
        if (strike == 3) {
            println("🎉 정답입니다! 시도 횟수: $attempts 🎉")
            break
        } else {
            println("⚾️ $strike 스트라이크 ,⚾️ $ball 볼")
        }
    }
}

fun generateAnswer(): String {
    // 0~9 숫자 무작위로 섞고 3개 선택
    val digits = (0..9).shuffled().take(3)
    return digits.joinToString("")
}

fun checkGuess(answer: String, guess: String): Pair<Int, Int> {
    // 정답 체크
    var strike = 0
    var ball = 0

    for (i in guess.indices) {
        when {
            guess[i] == answer[i] -> strike++
            guess[i] in answer -> ball++
        }
    }
    return Pair(strike, ball)
}
