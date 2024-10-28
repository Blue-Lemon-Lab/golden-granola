class HangmanGame(private val wordApiClient: WordApiClient) {
    private lateinit var currentWord: String
    private lateinit var guessedLetters: MutableSet<Char>
    private var lifeCounts: Int = 6
    private var wantPlay: Boolean = true

    fun start() {
        println("""
▗▖ ▗▖ ▗▄▖ ▗▖  ▗▖ ▗▄▄▖    ▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖
▐▌ ▐▌▐▌ ▐▌▐▛▚▖▐▌▐▌       ▐▛▚▞▜▌▐▌ ▐▌▐▛▚▖▐▌
▐▛▀▜▌▐▛▀▜▌▐▌ ▝▜▌▐▌▝▜▌    ▐▌  ▐▌▐▛▀▜▌▐▌ ▝▜▌
▐▌ ▐▌▐▌ ▐▌▐▌  ▐▌▝▚▄▞▘    ▐▌  ▐▌▐▌ ▐▌▐▌  ▐▌
        """.trimIndent())
        println("넌 언제나 단어의 소중함에 대해 알지 못했어...\n")
        println("그럼 게임을 시작하지\n")
        while (wantPlay) {
            playGame()
            println("\n한번 더 도전할텐가? (yes/no)")
            val input = readlnOrNull()?.lowercase()
            if (input == "yes") wantPlay = true
            else wantPlay = false
        }
        println("앞으로는 단어를 소중히 여기도록 해라!")
    }

    private fun playGame() {
        setupNewGame()

        while (lifeCounts > 0 && !isWordGuessed()) {
            displayGameState()
            processGuess()
        }

        if (isWordGuessed()) {
            println("\n축하한다 단어를 맞췄어. 단어는 이거야: $currentWord")
        } else {
            println("\n아쉽군. 넌 실패했어. 단어는 이거야: $currentWord")
        }
    }

    private fun setupNewGame() {
        println("\n난이도를 선택해라. (easy/normal/hard):")
        val difficulty = getDifficultyFromUser()
        currentWord = wordApiClient.getRandomWord(difficulty.wordLength).lowercase()
        guessedLetters = mutableSetOf()
        lifeCounts = 6
    }

    private fun getDifficultyFromUser(): GameDifficulty {
        while (true) {
            try {
                val input = readLine()?.uppercase() ?: continue
                return GameDifficulty.valueOf(input)
            } catch (e: IllegalArgumentException) {
                println("똑바로 쳐라. easy, normal, hard:")
            }
        }
    }

    private fun displayGameState() {
        println("\n단어: ${getDisplayWord()}")
        println("시도해본 단어: ${guessedLetters.joinToString()}")
        println("남은 목숨: $lifeCounts")
    }

    private fun getDisplayWord(): String {
        return currentWord.map { letter ->
            if (letter in guessedLetters) letter else '_'
        }.joinToString(" ")
    }

    private fun processGuess() {
        println("\n문자를 입력해라:")
        val guess = readLine()?.toLowerCase()?.firstOrNull()

        if (guess == null) {
            println("똑바로 입력 못해?")
            return
        }

        if (guess in guessedLetters) {
            println("이미 입력 했던 문자야")
            return
        }

        guessedLetters.add(guess)

        if (guess !in currentWord) {
            println("틀렸어!")
            lifeCounts--
        } else {
            println("잘했군 맞았어!")
        }
    }

    private fun isWordGuessed(): Boolean {
        return currentWord.all { it in guessedLetters }
    }
}
