package com.example.demo.bankers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankerRepository extends JpaRepository<Banker, Long>{

	Optional<Banker> findByUsername(String username);
}
