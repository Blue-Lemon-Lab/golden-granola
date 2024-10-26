import java.net.HttpURLConnection
import java.net.URL

object APIClient {
    suspend fun fetchWord(length:Number = 5): String {
        // 메인 스레드에서 API 호출 (비동기 방식 아님)
        val url = URL("https://random-word-api.herokuapp.com/word?length=$length")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        return if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            connection.inputStream.bufferedReader().use { it.readText() }
        } else {
            "Error: ${connection.responseCode}"
        }
    }
}