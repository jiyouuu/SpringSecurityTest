package com.example.demo.bankers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BankerService {

	private final BankerRepository bankerRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	public void createBanker(BankerDto bankerDto) {
		Banker b = new Banker(bankerDto.getUsername(), 
				passwordEncoder.encode(bankerDto.getPassword()));
		this.bankerRepository.save(b);
		
	}
}
