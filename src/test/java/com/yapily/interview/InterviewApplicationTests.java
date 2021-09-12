package com.yapily.interview;

import com.yapily.interview.controller.CharacterRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InterviewApplicationTests {

	@Autowired
	CharacterRestController characterRestController;

	@Test
	void contextLoads() {
		assertThat(characterRestController).isNotNull();
	}

}
