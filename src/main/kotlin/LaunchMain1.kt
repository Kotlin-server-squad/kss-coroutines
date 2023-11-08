import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.MDC
import service.MetricService
import service.NotificationService
import util.TracingService

class LaunchMain1

fun main(args: Array<String>) {
    // Aggregate total balance for the user
    MDC.put("traceId", TracingService.generateTraceId())
    MDC.put("correlationId", TracingService.generateCorrelationId())
    runBlocking(Dispatchers.Default + MDCContext()) {
        val metricService = MetricService()
        val notificationService = NotificationService()
        metricService.sendMetrics()
        notificationService.sendNotifications()
    }
}
