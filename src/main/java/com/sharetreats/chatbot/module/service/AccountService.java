package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.controller.dto.brandDtos.AccountRequest;
import com.sharetreats.chatbot.module.entity.Account;
import com.sharetreats.chatbot.module.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(AccountRequest request) {

        Account newAccount = Account.of(request.getId(), request.getName(), request.getAvatar(), 100);
        accountRepository.save(newAccount);

        return newAccount;
    }

    public void validateAccount(AccountRequest request) {
        accountRepository.findById(request.getId())
                .map(account -> {
                    account.changeReSubscribed(account.getSubscription());
                    return accountRepository.save(account);
                })
                .orElseGet(() -> createAccount(request));
    }

    public void unsubscribe(String id) {
        accountRepository.findById(id)
                .map(account -> {
                    account.changeUnsubscribed();
                    return accountRepository.save(account);
                })
                .orElseThrow(IllegalArgumentException::new);
    }
}
