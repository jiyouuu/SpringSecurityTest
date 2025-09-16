package com.example.demo.ideas;


import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IdeaController {

	private final IdeaService ideaService;

	
	@GetMapping("/")
	public String home() {
		return "/home";
	}
	
	
	// page : 현재 페이지 번호
	// size : 한 페이지에 보여줄 데이터 개수 
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/ideas")
	public String showIdeas(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "6") int size,
			@RequestParam(value = "keyword", required = false) String keyword,
			Principal principal, Model model) {
		
		// Pageable : page + size + 정렬 정보(Sort) 묶어서 전달
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Idea> ideas;
		
		
		List<Idea> my_Idea = this.ideaService.findMyIdea(principal);
		
		if(keyword == null || keyword.trim().isEmpty()) {
			// 페이징 처리를 위해 sublist + pageImpl
			int start = Math.min(page*size, my_Idea.size());
			int end = Math.min(start +size, my_Idea.size());
			ideas = new PageImpl<>(my_Idea.subList(start, end),pageable, my_Idea.size());
		} else {
			List<Idea> result_Idea = this.ideaService.findKeywordIdea(keyword,my_Idea);
			int start = Math.min(page*size, result_Idea.size());
			int end = Math.min(start +size, result_Idea.size());
			ideas = new PageImpl<>(result_Idea.subList(start, end),pageable, result_Idea.size());
		}
		
		
		model.addAttribute("ideas", ideas);
		model.addAttribute("keyword", keyword);
		
		return "/ideas/list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// /ideas/new 새로 만들기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/ideas/new")
	public String newIdea(IdeaDto ideaDto) {
		return "/ideas/new-form";
	}
	
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/ideas/new")
	public String newIdea(@Valid IdeaDto ideaDto, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "/ideas/new-form";
		}
		this.ideaService.newIdea(ideaDto,principal);
		return "redirect:/ideas";
	}
	
	
	// /ideas/{id}/edit(id=${idea.id})  편집 하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/ideas/{id}/edit")
	public String editIdea(@PathVariable(value = "id") Long id, Model model, Principal principal) {
		Idea idea = this.ideaService.findEditIdea(id);
		if(!principal.getName().equals(idea.getBanker().getUsername())) {
			return "redirect:/ideas";
		}
		
		model.addAttribute("ideaDto2", idea);
		return "/ideas/edit-form";
	}
	
	
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/ideas/{id}/edit")
	public String editIdea(@PathVariable(value = "id") Long id, Model model, @Valid IdeaDto2 ideaDto2, Principal principal, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return  "/ideas/edit-form";
		}
		
		Idea idea = this.ideaService.findEditIdea(id);
		if(!principal.getName().equals(idea.getBanker().getUsername())) {
			return "redirect:/ideas";
		}
		
		this.ideaService.editIdea(id, ideaDto2);
		
		return "redirect:/ideas";
	}
	

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/ideas/{id}/delete")
	public String deleteIdea(@PathVariable(value = "id") Long id, Principal principal) {
		Idea idea = this.ideaService.findEditIdea(id);
		if(!principal.getName().equals(idea.getBanker().getUsername())) {
			return "redirect:/ideas";
		}
		
		this.ideaService.deleteIdea(id);
		return "redirect:/ideas";
	}
	
}








