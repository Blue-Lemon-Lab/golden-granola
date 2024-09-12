import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

fun getNextDrawDate(): LocalDateTime {
    val seoul = ZoneId.of("Asia/Seoul")
    var currentDate = LocalDateTime.now(seoul)

    // 다음 토요일로 이동
    while (currentDate.dayOfWeek != DayOfWeek.SATURDAY) {
        currentDate = currentDate.plusDays(1)
    }

    // 추첨일 토요일 20:35로 설정
    return currentDate.withHour(20).withMinute(35).withSecond(0).withNano(0)
}

data class RemainingTime(val days: Int, val hours: Int, val minutes: Int)

fun getRemainingTime(drawDate: LocalDateTime): RemainingTime {
    val seoul = ZoneId.of("Asia/Seoul")
    val currentDate = LocalDateTime.now(seoul)
    val duration = ChronoUnit.MINUTES.between(currentDate, drawDate)

    val days = duration / (24 * 60)
    val hours = (duration % (24 * 60)) / 60
    val minutes = duration % 60

    return RemainingTime(days.toInt(), hours.toInt(), minutes.toInt())
}

fun generateLottoNumbers(): List<Int> {
    return (1..45).shuffled().take(6).sorted()
}
fun main() {
    val nextDrawDate = getNextDrawDate()
    val remainingTime = getRemainingTime(nextDrawDate)
    val lottoNumbers = generateLottoNumbers()

    println("가장 가까운 추첨일은 ${nextDrawDate.format(DateTimeFormatter.ofPattern("M월 d일"))} 입니다.")
    println("남은 시간은 ${remainingTime.days}일 ${remainingTime.hours}시간 ${remainingTime.minutes}분입니다.")
    println("추천하는 로또번호는 ${lottoNumbers.joinToString(", ")} 입니다.")
}