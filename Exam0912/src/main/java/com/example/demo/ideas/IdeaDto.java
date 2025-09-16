package com.example.demo.ideas;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdeaDto {

	@NotEmpty(message = "제목은 필수입니다.")
	private String title;
	
	@NotEmpty(message = "내용은 필수입니다.")
	private String description;
	
	
	@NotNull(message = "사진은 필수입니다.")
	private MultipartFile proofImage;
}
