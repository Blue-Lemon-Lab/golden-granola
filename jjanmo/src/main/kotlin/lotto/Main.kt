package lotto

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

class LottoDayCalculator {
    fun generateLottoNumber(): List<Int> {
        val lotto = mutableListOf<Int>()
        while (lotto.size < 6) {
            val newNumber = Random.nextInt(1, 45)
            if (!lotto.contains(newNumber)) {
                lotto.add(newNumber)
            }
        }
        return lotto
    }

    fun getLottoDayOfThisWeek(): LocalDateTime {
        val today = LocalDate.now()
        val diff = DayOfWeek.SATURDAY.value - today.dayOfWeek.value
        val lottoDay = today.plusDays((diff.toLong())).atTime(20, 0) // 이번주 토요일 8시
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

        if (days == 0L) return "로또를 뽑을 수 있는 시간은 앞으로 ${hours}시간 ${minutes}분 남았습니다."
        if (hours == 0L) return "로또를 뽑을 수 있는 시간은 앞으로 ${minutes}분 남았습니다."
        return "로또를 뽑을 수 있는 시간은 앞으로 ${days}일 ${hours}시간 ${minutes}분 남았습니다."
    }
}


fun main() {
    val calculator = LottoDayCalculator()
    val lottoNumbers = calculator.generateLottoNumber().joinToString(" ")

    val lottoDay = calculator.getLottoDayOfThisWeek()
    val timeLeft = calculator.formatter(calculator.getDiffFromLottoDay(lottoDay))

    println("반드시 당첨될 로또 번호 : $lottoNumbers")
    println(timeLeft)
    println("아무 때나 오지 않는 일확천금의 기회, 지금 당장 출발하세요. 😼")
}