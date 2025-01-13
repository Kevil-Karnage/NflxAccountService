package nflx.rozhnov.accountservice.service;

import nflx.rozhnov.accountservice.dto.exception.NotFoundAccountException;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.model.Account;
import nflx.rozhnov.accountservice.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    AccountService accountService = new AccountService();

    @Mock
    AccountRepository accountRepository;

    private final Long ACCOUNT_ID = 123456789L;
    private final Double ACCOUNT_BALANCE = 123.456;
    private final Account ACCOUNT = new Account(ACCOUNT_ID, ACCOUNT_BALANCE);
    private static final String TEST_EXCEPTION_MESSAGE = "TEST EXCEPTION MESSAGE";

    @Test
    @DisplayName("getAcocuntBalance - correct")
    public void getAccountBalance_correct() {
        // Data
        AccountGetBalanceRs expected = new AccountGetBalanceRs(ACCOUNT_ID, ACCOUNT_BALANCE, ZonedDateTime.now());

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
        // Mockito
        when(accountRepository.findById(ACCOUNT_ID))
                .thenThrow(new RuntimeException(TEST_EXCEPTION_MESSAGE));

        // Request and Check
        Assertions.assertThatThrownBy(() -> accountService.getAccountBalance(ACCOUNT_ID))
                .isInstanceOf(NotFoundAccountException.class)
                .hasMessageContaining(TEST_EXCEPTION_MESSAGE);
    }
}