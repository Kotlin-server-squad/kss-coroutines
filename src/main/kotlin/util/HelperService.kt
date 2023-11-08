package util

import java.time.Instant
import java.util.*
import kotlin.random.Random

object RandomDelayService {
    fun getRandomDelay(min: Long = 1000, max: Long = 2500, step: Long = 500) =
        (Random.nextLong((max - min) / step + 1) * step) + min


}

object TracingService{
    fun generateTraceId(): String {
        val timestamp = Instant.now().toEpochMilli()
        val customIdentifier = "TRACE"
        return "$customIdentifier-$timestamp"
    }

    fun generateCorrelationId(): String {
        return UUID.randomUUID().toString()
    }
}