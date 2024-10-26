package hangman

import APIClient


class Hangman(val word: String) {
    var tryCount = 0


    fun drawHangMan() {

    }
}



suspend fun main() {
        val response = APIClient.fetchWord() // suspend 함수 호출
        println(response)

}