package com.example.demo.bankers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BankerSecurityService implements UserDetailsService{

	private final BankerRepository bankerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Banker> b = this.bankerRepository.findByUsername(username);
		if(b.isEmpty()) {
			throw new UsernameNotFoundException("사용자 찾을 수 없음!!");	
		}
		
		Banker banker = b.get();
		
		List<GrantedAuthority> auth = new ArrayList<>();
		
		auth.add(new SimpleGrantedAuthority("USER"));
		return new User(banker.getUsername(), banker.getPassword(), auth) ;
	}

	
}
