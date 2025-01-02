package com.demo.cryptographyChallenge.repositories;

import com.demo.cryptographyChallenge.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
