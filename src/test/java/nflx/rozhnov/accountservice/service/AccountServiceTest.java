package nflx.rozhnov.accountservice.service;

import nflx.rozhnov.accountservice.dto.request.AccountAddBalanceRq;
import nflx.rozhnov.accountservice.dto.response.AccountAddBalanceRs;
import nflx.rozhnov.accountservice.exception.KafkaSendingException;
import nflx.rozhnov.accountservice.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.exception.TransactionNotSavedException;
import nflx.rozhnov.accountservice.kafka.KafkaProducer;
import nflx.rozhnov.accountservice.model.Account;
import nflx.rozhnov.accountservice.model.Transaction;
import nflx.rozhnov.accountservice.repository.AccountRepository;
import nflx.rozhnov.accountservice.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    KafkaProducer kafkaProducer;

    @InjectMocks
    AccountService accountService;

    private final Long ACCOUNT_ID = 123456789L;
    private final BigDecimal ACCOUNT_BALANCE = new BigDecimal("123.456");
    private final Account ACCOUNT = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);

    @Test
    @DisplayName("getAccountBalance - correct")
    public void getAccountBalance_correct() {
        // Data
        AccountGetBalanceRs expected = new AccountGetBalanceRs(ACCOUNT_ID, ACCOUNT_BALANCE, new Date());

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.of(ACCOUNT));


        // Request
        AccountGetBalanceRs actual = accountService.getAccountBalance(ACCOUNT_ID);

        // Check
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("timestamp")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("getAccountBalance - NotFound")
    public void getAccountBalance_notFound() {
        //Data
        NotFoundAccountException exception = new NotFoundAccountException();

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenThrow(exception);


        // Request and Check
        Assertions.assertThatThrownBy(() -> accountService.getAccountBalance(ACCOUNT_ID))
                .isInstanceOf(NotFoundAccountException.class);
    }

    @Test
    @DisplayName("addBalanceToAccount - correct - with account")
    public void addBalanceToAccount_correct_withAccount() {
        // Data
        Account account = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);
        AccountAddBalanceRq rq = new AccountAddBalanceRq(ACCOUNT_BALANCE);
        Transaction expectedTransaction = new Transaction(
                null,
                null,
                -1L,
                ACCOUNT_ID,
                account.getBalance().add(rq.getAmount())
                );
        AccountAddBalanceRs expectedRs = new AccountAddBalanceRs(
                null, account.getBalance().add(rq.getAmount()), null);

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.of(account));
        when(accountRepository.save(any()))
                .thenReturn(account);
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(expectedTransaction);
        doNothing().when(kafkaProducer).sendMessage(any());

        // Request
        AccountAddBalanceRs actualRs = accountService.addBalanceToAccount(ACCOUNT_ID, rq);

        // Check
        Mockito.verify(accountRepository, times(1)).save(any());
        assertThat(actualRs).usingRecursiveComparison().isEqualTo(expectedRs);
    }

    @Test
    @DisplayName("addBalanceToAccount - correct - without account")
    public void addBalanceToAccount_correct_withoutAccount() {
        // Data
        Account account = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);
        AccountAddBalanceRq rq = new AccountAddBalanceRq(ACCOUNT_BALANCE);
        Transaction expectedTransaction = new Transaction(
                null,
                null,
                -1L,
                ACCOUNT_ID,
                rq.getAmount()
        );
        AccountAddBalanceRs expectedRs = new AccountAddBalanceRs(
                null, rq.getAmount(), null);

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any()))
                .thenReturn(account);
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(expectedTransaction);
        doNothing().when(kafkaProducer).sendMessage(any());

        // Request
        AccountAddBalanceRs actualRs = accountService.addBalanceToAccount(ACCOUNT_ID, rq);

        // Check
        Mockito.verify(accountRepository, times(1)).save(any());
        assertThat(actualRs).usingRecursiveComparison().isEqualTo(expectedRs);
    }

    @Test
    @DisplayName("addBalanceToAccount - Kafka exception")
    public void addBalanceToAccount_KafkaException() {
        // Data
        Account account = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);
        AccountAddBalanceRq rq = new AccountAddBalanceRq(ACCOUNT_BALANCE);
        Transaction expectedTransaction = new Transaction(
                null,
                null,
                -1L,
                ACCOUNT_ID,
                rq.getAmount()
        );
        AccountAddBalanceRs expectedRs = new AccountAddBalanceRs(
                null, rq.getAmount(), null);

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any()))
                .thenReturn(account);
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(expectedTransaction);
        doThrow(new KafkaSendingException()).when(kafkaProducer).sendMessage(any());

        // Request and Check
        Assertions.assertThatThrownBy(() -> accountService.addBalanceToAccount(ACCOUNT_ID, rq))
                .isInstanceOf(KafkaSendingException.class);
    }

    @Test
    @DisplayName("addBalanceToAccount - TransactionException - transaction")
    public void addBalanceToAccount_transactionException_transaction() {
        // Data
        Account account = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);
        AccountAddBalanceRq rq = new AccountAddBalanceRq(ACCOUNT_BALANCE);

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenThrow(new TransactionNotSavedException());

        // Request and Check
        Assertions.assertThatThrownBy(() -> accountService.addBalanceToAccount(ACCOUNT_ID, rq))
                .isInstanceOf(TransactionNotSavedException.class);
    }

    @Test
    @DisplayName("addBalanceToAccount - TransactionException - Account")
    public void addBalanceToAccount_transactionException_account() {
        // Data
        Account account = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);
        AccountAddBalanceRq rq = new AccountAddBalanceRq(ACCOUNT_BALANCE);
        Transaction expectedTransaction = new Transaction(
                null,
                null,
                -1L,
                ACCOUNT_ID,
                account.getBalance().add(rq.getAmount())
        );

        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenReturn(Optional.of(account));
        when(accountRepository.save(any()))
                .thenReturn(account);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(expectedTransaction);
        when(accountRepository.save(any(Account.class)))
                .thenThrow(new TransactionNotSavedException());

        // Request and Check
        Assertions.assertThatThrownBy(() -> accountService.addBalanceToAccount(ACCOUNT_ID, rq))
                .isInstanceOf(TransactionNotSavedException.class);
    }

}