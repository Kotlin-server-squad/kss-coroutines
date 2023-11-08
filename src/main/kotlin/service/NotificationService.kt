package service

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.coroutines.coroutineContext


// Define a generic notification data class
data class Notification(val userId: Int, val message: String, val type: NotificationType)

// Define the types of notifications
enum class NotificationType {
    EMAIL, SMS, PUSH
}

// Define an interface for sending notifications
interface NotificationSender {
    suspend fun send(notification: Notification)
}

// Implement the interface for each notification provider
class EmailNotificationSender : NotificationSender {
    override suspend fun send(notification: Notification) {
        // Simulate the delay and logic of sending an email
        val coroutineName = coroutineContext[CoroutineName]?.name
        delay(1000)
        logger.info("Email sent to user ${notification.userId}: ${notification.message} by coroutine $coroutineName")
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class SMSNotificationSender : NotificationSender {
    override suspend fun send(notification: Notification) {
        // Simulate the delay and logic of sending an SMS
        val coroutineName = coroutineContext[CoroutineName]?.name
        delay(1000)
        logger.info("SMS sent to user ${notification.userId}: ${notification.message} by coroutine $coroutineName")
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class PushNotificationSender : NotificationSender {
    override suspend fun send(notification: Notification) {
        // Simulate the delay and logic of sending a push notification
        val coroutineName = coroutineContext[CoroutineName]?.name
        delay(1000)
        logger.info("Push notification sent to user ${notification.userId}: ${notification.message}  by coroutine $coroutineName")
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}


class NotificationService {
    private val pushNotificationSender = getNotificationSender(NotificationType.PUSH)
    private val emailNotificationSender = getNotificationSender(NotificationType.EMAIL)
    private val smsNotificationSender = getNotificationSender(NotificationType.SMS)

    suspend fun sendNotifications() = coroutineScope {
        launch(CoroutineName("PUSH coroutine")) {
            logger.info("Launch coroutine for PUSH")
            pushNotificationSender.send(Notification(1, "test 1", NotificationType.PUSH))
        }
        launch(CoroutineName("EMAIL coroutine")) {
            logger.info("Launch coroutine for EMAIL")
            emailNotificationSender.send(Notification(1, "test 2", NotificationType.EMAIL))
        }
        launch(CoroutineName("SMS coroutine")) {
            logger.info("Launch coroutine for SMS")
            smsNotificationSender.send(Notification(1, "test 2", NotificationType.SMS))
        }
    }


    // Function to choose the appropriate notification sender based on the type
    private fun getNotificationSender(type: NotificationType): NotificationSender {
        return when (type) {
            NotificationType.EMAIL -> EmailNotificationSender()
            NotificationType.SMS -> SMSNotificationSender()
            NotificationType.PUSH -> PushNotificationSender()
        }
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}