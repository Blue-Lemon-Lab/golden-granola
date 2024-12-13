import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class WordResponse(val word: String, val category: String)

fun fetchRandomWord(): WordResponse {
    val url = URL("https://www.wordgamedb.com/api/v1/words/random")
    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "GET"

        val response = inputStream.bufferedReader().use { it.readText() }
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(response)
    }
}

class HangmanGame(private val word: String, private val category: String) {
    private val hangmanPics = listOf(
        """
          +---+
          |   |
              |
              |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
              |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
          |   |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|   |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|\  |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|\  |
         /    |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|\  |
         / \  |
              |
        =========""",

        )

    private val guessedLetters = mutableSetOf<Char>()
    private var remainingLives = hangmanPics.size - 1

    fun play() {
        while (remainingLives > 0) {
            println("[[[ Category: $category ]]]")
            println(hangmanPics[hangmanPics.size - 1 - remainingLives])
            println("\n단어: " + word.map { if (it in guessedLetters) it else '_' }.joinToString(" "))

            print("알파벳을 입력해주세요.: ")
            val input = readLine()

            if (!isValidInput(input)) continue

            val guess = input!![0].lowercaseChar()
            guessedLetters.add(guess)

            if (guess in word) {
                guessedLetters.add(guess)
                if (word.all { it in guessedLetters }) {
                    println("축하해요! 단어를 맞췄습니다 ! 🎉: $word")
                    break
                }
            } else {
                remainingLives--
                println("❌❌❌ 추측 실패! ❌❌❌\n👉👉남은 기회: $remainingLives")
            }
        }

        if (remainingLives == 0) {
            println(hangmanPics.last())
            println("Game Over! 정답은 ** $word ** 였습니다.")
        }
    }

    private fun isValidInput(input: String?): Boolean {
        if (input.isNullOrEmpty() || input.length != 1) {
            println("😡 하나의 알파벳만 입력할 수 있습니다.")
            return false
        }

        if (!input[0].isLowerCase()) {
            println("😡 알.파.벳을 입력해주세요.")
            return false
        }

        val guess = input[0].lowercaseChar()
        if (guess in guessedLetters) {
            println("😡 이전에 입력한 적 있는 알파벳 입니다.")
            return false
        }

        return true
    }
}



fun main() {
    val wordResponse = fetchRandomWord()
    val game = HangmanGame(wordResponse.word, wordResponse.category)
    game.play()
}
