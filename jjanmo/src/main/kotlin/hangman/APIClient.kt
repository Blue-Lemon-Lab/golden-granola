package hangman

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.net.URI

object APIClient {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    suspend fun fetchWord(length: Number = 5): String = withContext(Dispatchers.IO) {
        val url : URL = URI("https://random-word-api.herokuapp.com/word?length=$length").toURL()
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return@withContext if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val parsed: List<String> = jsonParser.decodeFromString(response)
            parsed.first()
        } else {
            "Error: ${connection.responseCode}"
        }
    }
}