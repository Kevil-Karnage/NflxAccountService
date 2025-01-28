package nflx.rozhnov.accountservice.service;

import nflx.rozhnov.accountservice.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.request.AccountAddBalanceRq;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.dto.response.AccountAddBalanceRs;
import nflx.rozhnov.accountservice.model.Account;
import nflx.rozhnov.accountservice.model.Transaction;
import nflx.rozhnov.accountservice.repository.AccountRepository;
import nflx.rozhnov.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public AccountGetBalanceRs getAccountBalance(Long id) {
        Account fromDb = getAccountBalanceFromRepository(id);

        return new AccountGetBalanceRs(id, fromDb.getBalance(), ZonedDateTime.now());
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
        Transaction transaction = new Transaction(
                null,
                ZonedDateTime.now(),
                -1L,
                id,
                rq.getAmount()
        );


        // 3) сохраняем обновлённые данные и возвращаем пользователю
        account = accountRepository.save(account);
        transaction = transactionRepository.save(transaction);

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
