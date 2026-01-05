package com.back.global.initData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.back.domain.post.post.document.Post;
import com.back.domain.post.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BaseInitData {

	private final PostService postService;

	@Bean
	public ApplicationRunner baseInitDataRunner() {
		return args -> {
			work1();

			log.debug("기존 Post 전체조회");
			work2();

			log.debug("post 단건 조회");
			work3();
		};
	}

	private void work1() {
		log.debug("Post entity 개수: {}", postService.count());
		if (postService.count() == 0) {
			log.debug("샘플 post 데이터 생성");
			for (int i = 1; i <= 10; i++) {
				String title = "Sample Post Title " + i;
				String content = "This is the content of sample post number " + i + ".";
				String author = "Author" + i;
				Post post = postService.create(title, content, author);
				log.debug("Created Post: {}", post);
			}
		}
	}

	private List<String> work2() {
		List<String> postIds = new ArrayList<>();
		for (Post post : postService.findAll()) {
			log.debug("Existing Post: {}", post);
		}
		return postIds;
	}

	private void work3() {

		for (String ids : work2()) {
			Optional<Post> post = postService.findById(ids);
			log.debug("단건 조회된 Post: {}", post);
		}
	}
}
