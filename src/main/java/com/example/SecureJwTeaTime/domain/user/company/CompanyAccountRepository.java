package com.example.SecureJwTeaTime.domain.user.company;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, UUID> {
  Optional<CompanyAccount> findByCompanyName(String companyName);
}
