package nflx.rozhnov.accountservice.controller;

import nflx.rozhnov.accountservice.dto.request.AccountAddBalanceRq;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.dto.response.AccountAddBalanceRs;
import nflx.rozhnov.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/account", produces = APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/{id}/balance")
    public AccountGetBalanceRs getAccountBalance(@PathVariable Long id) {
        return service.getAccountBalance(id);
    }

    @PostMapping("/{id}/balance")
    public AccountAddBalanceRs addBalanceToAccount(@RequestBody AccountAddBalanceRq rq,
                                                   @PathVariable("id") Long id) {
        return service.addBalanceToAccount(id, rq);
    }
}
