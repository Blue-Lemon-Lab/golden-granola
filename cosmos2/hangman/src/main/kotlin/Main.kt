// WordApiClient.kt
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request

class WordApiClient {
    private val client = OkHttpClient()
    private val mapper = jacksonObjectMapper()
    private val baseUrl = "https://random-word-api.herokuapp.com/word"

    fun getRandomWord(length: Int): String {
        val request = Request.Builder()
            .url("$baseUrl?length=$length")
            .build()

        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: throw RuntimeException("Failed to get response")

        return mapper.readValue<List<String>>(body).first()
    }
}

// HangmanGame.kt


// Main.kt
fun main() {
    val wordApiClient = WordApiClient()
    val game = HangmanGame(wordApiClient)
    game.start()
}