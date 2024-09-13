package lotto

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class LottoDayCalculator {
    fun generateLottoNumber(): List<Int> {
        val numbers: List<Int> = (1..45).toList()
        return numbers.shuffled().take(6)
    }

    fun getLottoDayOfThisWeek(): LocalDateTime {
        val today = LocalDate.now()
        val diff = DayOfWeek.SATURDAY.value - today.dayOfWeek.value
        val lottoDay = today.plusDays(diff.toLong()).atTime(20, 0) // 이번주 토요일 8시
        return lottoDay
    }

    fun getDiffFromLottoDay(lottoDay: LocalDateTime): Triple<Long, Long, Long> {
        val today = LocalDateTime.now()
        val diff = Duration.between(today, lottoDay)
        val days = diff.toHours() / 24
        val hours = diff.toHours() % 24
        val minutes = diff.toMinutes() % 60

        return Triple(days, hours, minutes)
    }

    fun formatter(timeData: Triple<Long, Long, Long>): String {
        val (days, hours, minutes) = timeData
        return when {
            days == 0L && hours == 0L -> "로또를 뽑을 수 있는 시간은 앞으로 ${minutes}분 남았습니다."
            days == 0L -> "로또를 뽑을 수 있는 시간은 앞으로 ${hours}시간 ${minutes}분 남았습니다."
            else -> "로또를 뽑을 수 있는 시간은 앞으로 ${days}일 ${hours}시간 ${minutes}분 남았습니다."
        }
    }

    fun getLottoWinningProbability(lottoNumbers: List<Int>): String {
        var probability = lottoNumbers.joinToString("").toLong()
        while (probability > 100) {
            probability %= 77
        }

        return when {
            probability >= 90 -> "🎉 대박! 당신의 확률은 ${probability}%에요! 로또의 신이 당신을 응원하고 있어요! 🌟💖"
            probability >= 80 -> "😄 훌륭해요! 당신의 확률은 ${probability}%에요! 행운이 당신의 편이에요! ✨🍀"
            probability >= 70 -> "🎈 멋져요! 당신의 확률은 ${probability}%에요! 계속 힘내세요, 좋은 일이 일어날 거예요! 🌟😃"
            probability >= 60 -> "🌟 좋습니다! 당신의 확률은 ${probability}%에요! 운이 따를 거예요, 조금만 더 힘내세요! 💪🏻🍀"
            probability >= 50 -> "👍🏻 괜찮아요! 당신의 확률은 ${probability}%에요! 반반의 확률이지만, 긍정적인 마음으로 도전하세요! 🌈"
            probability >= 40 -> "💖 아직 희망이 있어요! 당신의 확률은 ${probability}%에요. 희망을 잃지 말고 계속 응원할게요! 🌟😉"
            probability >= 30 -> "💪🏻 그래도 괜찮아요! 당신의 확률은 ${probability}%에요. 작은 기적을 믿어보세요! ✨😄"
            probability >= 20 -> "💖 괜찮아요! 당신의 확률은 ${probability}%에요. 노력은 결코 헛되지 않아요! 응원할게요! 🚀🍀"
            probability >= 10 -> "💫 포기하지 마세요! 당신의 확률은 ${probability}%에요. 작은 기적이 일어날 수 있어요! 화이팅! 🙏🏻💪🏻"
            else -> "희망은 항상 있어요! 당신의 확률은 ${probability}%에요. 믿음과 응원으로 큰 기적을 만들어보세요! "

        }
    }
}


fun main() {
    val calculator = LottoDayCalculator()

    while (true) {
        val lottoNumbers = calculator.generateLottoNumber()
        val lottoDay = calculator.getLottoDayOfThisWeek()
        val timeLeftMessage = calculator.formatter(calculator.getDiffFromLottoDay(lottoDay))
        val probabilityMessage = calculator.getLottoWinningProbability(lottoNumbers)

        println("반드시 당첨될 로또 번호 : ${lottoNumbers.joinToString(" ")}")
        println(timeLeftMessage)
        println()
        println(probabilityMessage)
        println()

        println("한 번 더 뽑아보실??(y/n)")
        val value = readln().trim()
        if (value == "n") return
    }
}