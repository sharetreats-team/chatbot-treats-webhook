package com.sharetreats.chatbot.module.repository;

import com.sharetreats.chatbot.module.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
