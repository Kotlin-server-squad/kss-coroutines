import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.MDC
import service.AccountAggregatorService
import service.BankService
import util.TracingService

class AsyncMain2

fun main(args: Array<String>) {
    runBlocking(Dispatchers.Default + MDCContext()) {
        val bankServices = listOf(BankService(), BankService(), BankService())
        val accountAggregatorService = AccountAggregatorService(bankServices)
        MDC.put("traceId", TracingService.generateTraceId())
        MDC.put("correlationId", TracingService.generateCorrelationId())
        val totalBalance = accountAggregatorService.getTotalBalance("userId123")
        println("Total Account Balance: $totalBalance")
    }
}
