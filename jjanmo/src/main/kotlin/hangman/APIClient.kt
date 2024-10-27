package hangman

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.net.URI

object APIClient {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    suspend fun fetchWord(length: Int = 5): String = withContext(Dispatchers.IO) {
        val url : URL = URI("https://random-word.ryanrk.com/api/en/word/random?length=$length").toURL()
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return@withContext if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val parsed: List<String> = jsonParser.decodeFromString(response)
            print(parsed)
            parsed.first().lowercase()
        } else {
            "Error: ${connection.responseCode}"
        }
    }
}