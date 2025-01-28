package nflx.rozhnov.accountservice.service;

import nflx.rozhnov.accountservice.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.request.AccountAddBalanceRq;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.dto.response.AccountAddBalanceRs;
import nflx.rozhnov.accountservice.exception.TransactionNotSavedException;
import nflx.rozhnov.accountservice.model.Account;
import nflx.rozhnov.accountservice.model.Transaction;
import nflx.rozhnov.accountservice.repository.AccountRepository;
import nflx.rozhnov.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public AccountGetBalanceRs getAccountBalance(Long id) {
        Account fromDb = getAccountBalanceFromRepository(id);

        return new AccountGetBalanceRs(id, fromDb.getBalance(), new Date());
    }

    public AccountAddBalanceRs addBalanceToAccount(Long id, AccountAddBalanceRq rq) {
        // 1) получаем аккаунт

        Account account;
        try {
            // 1.1) пытаемся получить из бд
            account = getAccountBalanceFromRepository(id);
            account.setBalance(account.getBalance() + rq.getAmount());
        } catch (Exception ex) {
            // 1.2) если аккаунта с таким id нет, то создаем его:
            account = new Account(id, rq.getAmount());
        }

        // 2) создаём транзакцию пополнения
        Date date = new Date();

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                date,
                -1L,
                id,
                rq.getAmount()
        );

        // 3) сохраняем обновлённые данные и возвращаем пользователю
        try {
            transaction = transactionRepository.save(transaction);
            account = accountRepository.save(account);
        } catch (Exception ex) {
            throw new TransactionNotSavedException(ex.getMessage());
        }

        return new AccountAddBalanceRs(
                transaction.getId(),
                account.getBalance(),
                transaction.getTimestamp()
        );
    }

    private Account getAccountBalanceFromRepository(Long id) throws NotFoundAccountException {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new NotFoundAccountException();
        } else {
            return accountOptional.get();
        }
    }
}
