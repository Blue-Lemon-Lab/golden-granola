package baseball


class Baseball(private val total: Int = 3) {
    val generatedNumbers: List<Int> = (0..9).toList().shuffled().slice(0 until total)
}


fun main() {
    println("0~9 사이의 숫자를 입력하세요.")
    println("입력방법 : [숫자1 숫자2 숫자3] ← 숫자사이 공백 필수")
    val input = readln()
    println(input)

    val game = Baseball()
    val generatedNumbers = game.generatedNumbers
    println(generatedNumbers)
}