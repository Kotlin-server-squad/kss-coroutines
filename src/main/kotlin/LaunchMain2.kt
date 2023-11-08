import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.MDC
import service.MetricService
import service.NotificationService
import util.TracingService

class LaunchMain2

fun main(args: Array<String>) {
    // Aggregate total balance for the user
    runBlocking(Dispatchers.Default + MDCContext()) {
        MDC.put("traceId", TracingService.generateTraceId())
        MDC.put("correlationId", TracingService.generateCorrelationId())

        val metricService = MetricService()
        val notificationService = NotificationService()

        metricService.sendMetricsWithException()
        notificationService.sendNotifications()
    }
}
