package nflx.rozhnov.accountservice.service;

import nflx.rozhnov.accountservice.dto.enums.TransactionStatus;
import nflx.rozhnov.accountservice.dto.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.request.AccountPutBalanceRq;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.dto.response.AccountPutBalanceRs;
import nflx.rozhnov.accountservice.model.Account;
import nflx.rozhnov.accountservice.model.Transaction;
import nflx.rozhnov.accountservice.repository.AccountRepository;
import nflx.rozhnov.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public AccountGetBalanceRs getAccountBalance(Long id) {
        Account fromDb = getAccountBalanceFromRepository(id);

        return new AccountGetBalanceRs(id, fromDb.getAmount(), ZonedDateTime.now());
    }

    public AccountPutBalanceRs putBalanceToAccount(Long id, AccountPutBalanceRq rq) {
        // создаём транзакцию
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                TransactionStatus.PENDING.name(),
                TransactionStatus.PENDING.getMessage(),
                ZonedDateTime.now(),
                null,
                id,
                rq.getAmount()
        );

        // обновляем баланс аккаунта
        Account fromDb = getAccountBalanceFromRepository(id);
        fromDb.setAmount(fromDb.getAmount() + rq.getAmount());

        // сохраняем
        transactionRepository.save(transaction);
        accountRepository.save(fromDb);

        // возвращаем транзакцию
        return new AccountPutBalanceRs(
                transaction.getId(),
                transaction.getStatus(),
                transaction.getMessage(),
                fromDb.getAmount(),
                transaction.getTimestamp()
        );
    }

    private Account getAccountBalanceFromRepository(Long id) {
        try {
            return accountRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundAccountException(e.getMessage());
        }
    }
}
