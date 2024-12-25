package nflx.rozhnov.accountservice.controller;

import nflx.rozhnov.accountservice.dto.request.AccountPutBalanceRq;
import nflx.rozhnov.accountservice.dto.response.AccountGetBalanceRs;
import nflx.rozhnov.accountservice.dto.response.AccountPutBalanceRs;
import nflx.rozhnov.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/{id}/balance")
    public AccountGetBalanceRs getAccountBalance(@PathVariable Long id) {
        return service.getAccountBalance(id);
    }

    @PostMapping("/{id}/balance")
    public AccountPutBalanceRs putBalanceToAccount(@RequestBody AccountPutBalanceRq rq,
                                                   @PathVariable("id") Long id) {
        return service.putBalanceToAccount(id, rq);
    }
}
