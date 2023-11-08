package service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import util.RandomDelayService.getRandomDelay
import java.math.BigDecimal

// Represents a user's bank account with a balance
data class BankAccount(val bankName: String, val balance: BigDecimal)

// Simulates a bank service that returns the balance of a user's account
class BankService {
    // Simulate API call to get account balance from a bank
    suspend fun getAccountBalance(bankName: String, userId: String): BankAccount {
        delay(getRandomDelay())
        logger.info("Get account balance for bank $bankName and user $userId")
        return BankAccount(bankName, BigDecimal((100..10000).random()))
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}

// Service that aggregates balances from multiple bank accounts
class AccountAggregatorService(
    private val bankServices: List<BankService>
) {
    suspend fun getTotalBalance(userId: String): BigDecimal = coroutineScope {
        bankServices.map { bankService ->
            async { bankService.getAccountBalance("Bank ${bankServices.indexOf(bankService) + 1}", userId) }
        }.awaitAll()
            .map { it.balance }
            .fold(BigDecimal.ZERO) { total, balance -> total + balance }
    }
    suspend fun getTotalBalanceWithSendAnalytics(userId: String): BigDecimal = coroutineScope {
        bankServices.map { bankService ->
            async { bankService.getAccountBalance("Bank ${bankServices.indexOf(bankService) + 1}", userId) }
        }.awaitAll()
            .map { it.balance }
            .fold(BigDecimal.ZERO) { total, balance -> total + balance }
    }
}