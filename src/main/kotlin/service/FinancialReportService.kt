package service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import util.RandomDelayService.getRandomDelay
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random

// Mock Services with hypothetical implementation details

class AccountService {
    suspend fun getAccountBalances(userId: String, millis: Long): List<AccountBalance> {
        delay(millis)
        logger.info("Get account balance for user $userId with delay$millis")
        return listOf(
            AccountBalance("Checking", BigDecimal("1500.00")),
            AccountBalance("Savings", BigDecimal("5600.00"))
        )
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class TransactionService {
    suspend fun getRecentTransactions(userId: String, millis: Long): List<Transaction> {
        delay(millis)
        logger.info("Get recent transaction for user $userId with delay$millis")
        return listOf(
            Transaction(LocalDate.now().minusDays(1), "Grocery Store", BigDecimal("-80.50")),
            Transaction(LocalDate.now().minusDays(2), "Online Retailer", BigDecimal("-120.15"))
        )
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class InvestmentService {
    suspend fun getInvestmentSummary(userId: String, millis: Long): InvestmentSummary {
        delay(millis)
        logger.info("Tech stocks for user $userId with delay$millis")
        return InvestmentSummary(listOf(Investment("Tech Stocks", BigDecimal("2400.00"))))
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

class AnalyticsService {
    suspend fun getPredictiveAnalytics(userId: String, millis: Long): PredictiveAnalytics {
        delay(millis)
        logger.info("Get predictive analytics for user $userId with delay$millis")
        return PredictiveAnalytics("Based on your spending habits, you could save an additional $300 next month.")
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

// Data classes

data class FinancialReport(
    val accountBalances: List<AccountBalance>,
    val recentTransactions: List<Transaction>,
    val investmentSummary: InvestmentSummary,
    val predictiveAnalytics: PredictiveAnalytics
)

data class AccountBalance(val accountType: String, val balance: BigDecimal)
data class Transaction(val date: LocalDate, val description: String, val amount: BigDecimal)
data class InvestmentSummary(val investments: List<Investment>)
data class Investment(val name: String, val value: BigDecimal)
data class PredictiveAnalytics(val advice: String)

// The main service that composes the FinancialReport

class FinancialReportingService {
    private val accountService = AccountService()
    private val transactionService = TransactionService()
    private val investmentService = InvestmentService()
    private val analyticsService = AnalyticsService()

    suspend fun generateFinancialReport(userId: String): FinancialReport = coroutineScope {
        val balanceDeferred = async { accountService.getAccountBalances(userId, getRandomDelay()) }
        val transactionsDeferred = async { transactionService.getRecentTransactions(userId, getRandomDelay()) }
        val investmentSummaryDeferred = async { investmentService.getInvestmentSummary(userId, getRandomDelay()) }
        val analyticsDeferred = async { analyticsService.getPredictiveAnalytics(userId, getRandomDelay()) }

        FinancialReport(
            accountBalances = balanceDeferred.await(),
            recentTransactions = transactionsDeferred.await(),
            investmentSummary = investmentSummaryDeferred.await(),
            predictiveAnalytics = analyticsDeferred.await()
        ).also {
            logger.info("Financial report $it")
        }
    }



    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}