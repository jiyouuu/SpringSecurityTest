package com.example.demo.ideas;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.bankers.Banker;

import groovy.transform.builder.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Idea {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	

	@Size(min = 10)
	private String description;
	
	private String proofImageName;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banker_id")
	private Banker banker;
	
	@Builder
	public Idea(String title, String description, String proofImageName, Banker banker) {
		this.title = title;
		this.description = description;
		this.proofImageName = proofImageName;
		this.banker = banker;
	}

	
	public void update(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
}
