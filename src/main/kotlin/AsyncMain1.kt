import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import service.FinancialReportingService

class AsyncMain1

fun main(args: Array<String>): Unit = runBlocking(Dispatchers.Default) {
    val logger = LoggerFactory.getLogger(AsyncMain1::class.java)
    logger.info("Async first examole")
    val reportService = FinancialReportingService()
    reportService.generateFinancialReport("user1234")
    delay(2000)
}
