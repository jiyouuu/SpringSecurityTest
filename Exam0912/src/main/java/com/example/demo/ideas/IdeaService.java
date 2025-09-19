package com.example.demo.ideas;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bankers.Banker;
import com.example.demo.bankers.BankerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IdeaService {

	private final IdeaRepository ideaRepository;
	private final BankerRepository bankerRepository;
	
	
	@Value("${file.upload_dir}")
	private String uploadDir;
	
	
	public List<Idea> findMyIdea(Principal principal) {
		List<Idea> ideas = this.ideaRepository.findAll();
		
		List<Idea> my_idea = new ArrayList<>();
		for(Idea idea : ideas) {
			if(idea.getBanker().getUsername().equals(principal.getName())) {
				my_idea.add(idea);
			}
		}
		
		return my_idea;
	}

	
	
	
	
	
	
	public void newIdea(IdeaDto ideaDto, Principal principal) {
		MultipartFile file = ideaDto.getProofImage();
		String filePath = null;
		
		if(file != null && !file.isEmpty()) {
			filePath = UUID.randomUUID()+"_"+ file.getOriginalFilename();
			
			// 서버와 로컬 경로 연결시키기
			File f = new File(uploadDir, filePath);
			
			try {
				file.transferTo(f);
			} catch (IOException e) {
				throw new RuntimeException("파일 저장 오류", e);
			}
		
		}
		
		Banker banker = this.bankerRepository.findByUsername(principal.getName()).get();
		Idea i = new Idea(ideaDto.getTitle(), ideaDto.getDescription(),filePath, banker);
		this.ideaRepository.save(i);
	}

	public Idea findEditIdea(Long id) {
		return this.ideaRepository.findById(id).get();
	}

	
	public void deleteIdea(Long id) {
		this.ideaRepository.deleteById(id);
	}
	
	
	

	public void editIdea(Long id, IdeaDto2 ideaDto2) {
		Idea idea = this.ideaRepository.findById(id).get();
	
		idea.update(ideaDto2.getTitle(), ideaDto2.getDescription());
		
		// update하고 이거 무조건 해줘야 해ㅠㅠㅠ
		this.ideaRepository.save(idea);
	}

	
	
	public List<Idea> findKeywordIdea(String keyword, List<Idea> my_Idea) {
		// keyword == null : 아무것도 안 쳤을때
		// keyword.isEmpty() : 공백 치고 엔터 쳤을 경우 빈 문자열 ""로 넘어오기 때문에 
		if(keyword == null || keyword.isEmpty()) {
			return my_Idea;
		} 
		
		String lower_Keyword = keyword.trim().toLowerCase(); // 공백 제거 및 소문자로 !! 
		return my_Idea.stream()
				.filter(idea ->idea.getTitle().toLowerCase()
						.contains(lower_Keyword))
				.collect(Collectors.toList());
	
	}
	
	
}


