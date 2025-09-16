package com.example.demo.ideas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IdeaRepository extends JpaRepository<Idea, Long>{

	@Query(value = "SELECT * FROM IDEA WHERE TITLE LIKE %:keyword%", nativeQuery = true)
	List<Idea> findIdea(@Param("keyword") String keyword);
}
