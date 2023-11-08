package service

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory

class MetricService {

    // This function collects different kinds of metrics from the application
    private suspend fun collectMetrics(): Map<String, Any> {
        delay(500)
        logger.info("Collecting metrics")
        return mapOf(
            "cpuUsage" to (0..100).random(),
            "memoryUsage" to (0..100).random(),
            "activeUsers" to (0..1000).random()
        )
    }
    // Simulate a function that sends metrics to a monitoring service
    suspend fun sendMetrics() {
        val metrics = collectMetrics()
        // Simulate network delay
        delay(500)
        logger.info("Metrics sent at ${System.currentTimeMillis()}: $metrics")
    }

    suspend fun sendMetricsWithException() {
        // Simulate network delay
        delay(500)
        throw Error("Error on network")
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}