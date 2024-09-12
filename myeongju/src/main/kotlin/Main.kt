import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import kotlin.random.Random


fun getNextLotteryDrawDate(now: LocalDateTime): LocalDateTime {
    // 토요일 20시 35분 > 오늘이 토요일이 아닌 경우만 다음주로 세팅
    var nextDraw = now.with(LocalTime.of(20, 35))
    if (now.dayOfWeek != DayOfWeek.SATURDAY) {
        nextDraw = nextDraw.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
    }
    return nextDraw
}

fun generateLotteryNumbers(): List<Int> {
    // 1~45까지 무작위로 섞은 뒤 앞에서 6개 뽑음 > 정렬
    return (1..45).shuffled(Random).take(6).sorted()
}

fun main() {
    // 0. 시간대 한국으로 지정
    val koreaZoneId = ZoneId.of("Asia/Seoul")
    
    // 1. 지금 시간으로부터 가장 가까운 추첨일 계산
    val now = LocalDateTime.now(koreaZoneId)
    val nextLotteryDrawDate = getNextLotteryDrawDate(now)
    
    // 2. 추첨일까지 남은 시간 계산
    val remainingTime = Duration.between(now, nextLotteryDrawDate)
    
    // 3. 추천 로또번호 생성
    val lotteryNumbers = generateLotteryNumbers()
    
    // 결과 출력
    println("다음 로또 추첨일: ${nextLotteryDrawDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"))}")
    println("지금부터 남은 시간: ${remainingTime.toDays()}일 ${remainingTime.toHoursPart()}시간 ${remainingTime.toMinutesPart()}분")
    println("추천 로또 번호: ${lotteryNumbers.joinToString(", ")}")
}