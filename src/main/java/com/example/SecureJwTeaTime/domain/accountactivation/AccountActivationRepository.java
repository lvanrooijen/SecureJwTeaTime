package com.example.SecureJwTeaTime.domain.accountactivation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountActivationRepository extends JpaRepository<AccountActivation, Long> {}
