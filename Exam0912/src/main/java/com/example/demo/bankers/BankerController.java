package com.example.demo.bankers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class BankerController {

	private final BankerService bankerService;
	
	@GetMapping("/users/login")
	public String login() {
		return "/users/login-form";
	}
	
	@PostMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
	
	@GetMapping("/users/signup")
	public String signUp(BankerDto bankerDto) {
		return "/users/signup-form";
	}
	
	
	@PostMapping("/users/signup")
	public String signUp(@Valid BankerDto bankerDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/users/signup-form";
		}
		this.bankerService.createBanker(bankerDto);
	
		return "redirect:/users/login";
	}
	
}






