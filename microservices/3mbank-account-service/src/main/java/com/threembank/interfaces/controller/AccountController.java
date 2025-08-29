package com.threembank.interfaces.controller;

import com.threembank.application.usecase.AccountUseCase;
import com.threembank.domain.valueobject.AccountStatus;
import com.threembank.infrastructure.security.User;
import com.threembank.interfaces.dto.AccountResponse;
import com.threembank.interfaces.dto.CreateRequest;
import com.threembank.interfaces.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountUseCase useCase;
    private final AccountMapper accountMapper;

    @PostMapping
    public void create(@RequestBody CreateRequest request, @AuthenticationPrincipal User user) {
        useCase.create(request, user);
    }

    @GetMapping
    public ResponseEntity<Collection<AccountResponse>> getAccounts(@AuthenticationPrincipal User user) {
        var accounts = useCase.getAll(user).stream()
                .map(accountMapper::toResponse).toList();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getUseCase(@PathVariable long id) {
        var account = accountMapper.toResponse(useCase.get(id));
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable long id, @RequestBody AccountStatus status) {
        useCase.updateStatus(id,status);
        return  ResponseEntity.noContent().build();
    }
}
