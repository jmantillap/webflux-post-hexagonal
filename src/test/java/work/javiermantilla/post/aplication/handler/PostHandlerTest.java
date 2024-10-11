package work.javiermantilla.post.aplication.handler;



import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import work.javiermantilla.post.aplication.dto.PostDto;
//import work.javiermantilla.post.aplication.mapper.PostDtoMapper;
//import work.javiermantilla.post.aplication.validation.ObjectValidator;
import work.javiermantilla.post.domain.model.PostModel;
import work.javiermantilla.post.domain.ports.in.PostUseCasePortIn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostHandlerTest {
	
	@Autowired
    private WebTestClient webTestClient;
	//@MockBean
	@Autowired
	private PostUseCasePortIn postUseCasePortIn;
	//@Autowired
	//private PostDtoMapper postDtoMapper;
//	@Autowired
//	private ObjectValidator objectValidator;
	
	@Test
	void testListPosts() {
		 webTestClient.get().uri("api/post")
         .accept(MediaType.APPLICATION_JSON)
         .exchange()
         .expectStatus().isOk()
         .expectHeader().contentType(MediaType.APPLICATION_JSON)
         .expectBodyList(PostDto.class)
         .consumeWith(System.out::println);
	}
	
	@Test
	void testGetOne() {
		PostModel post = new PostModel();
        post.setTitle("Blog Post 2");
        post.setDescription("Blog Post 2 Description");
        post.setBody("Blog Post 2 Body");
        
		PostModel savedPost = postUseCasePortIn.savePost(post).block();
		 
		 webTestClient.get()
         .uri("api/post/{id}", Collections.singletonMap("id",  savedPost.getId()))
         .exchange()
         .expectStatus().isOk()
         .expectBody()
         .consumeWith(System.out::println)
         .jsonPath("$.id").isEqualTo(savedPost.getId());
	}

	@Test
	void testSavePost() {
		
		 PostDto post = new PostDto();
	        post.setTitle("Blog Post 1");
	        post.setDescription("Blog Post 1 Description");
	        post.setBody("Blog Post 1 Body");	        
	        webTestClient.post().uri("api/post")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .body(Mono.just(post), PostDto.class)
	                .exchange()
	                .expectStatus().isCreated()
	                .expectBody()
	                .consumeWith(System.out::println)
	                .jsonPath("$.title").isEqualTo(post.getTitle())
	                .jsonPath("$.description").isEqualTo(post.getDescription())
	                .jsonPath("$.body").isEqualTo(post.getBody());
		
	}

	

	@Test
	void testUpdatePost() {
		PostModel post = new PostModel();
        post.setTitle("Blog Post 1");
        post.setDescription("Blog Post 1 Description");
        post.setBody("Blog Post 1 Body");

        PostDto updatedPost = new PostDto();
        updatedPost.setTitle("Blog Post 1 updated");
        updatedPost.setDescription("Blog Post 1 Description updated");
        updatedPost.setBody("Blog Post 1 Body updated");

        PostModel savedPost = postUseCasePortIn.savePost(post).block();

        
        webTestClient.put()
                .uri("api/post/{id}", Collections.singletonMap("id", savedPost.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedPost), PostDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.title").isEqualTo(updatedPost.getTitle())
                .jsonPath("$.description").isEqualTo(updatedPost.getDescription())
                .jsonPath("$.body").isEqualTo(updatedPost.getBody());
	}

	@Test
	void testDeletePost() {
		PostModel post = new PostModel();
        post.setTitle("Blog Post 2");
        post.setDescription("Blog Post 2 Description");
        post.setBody("Blog Post 2 Body");

        PostModel savedPost = postUseCasePortIn.savePost(post).block();

        webTestClient.delete()
                .uri("api/post/{id}", Collections.singletonMap("id",  savedPost.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
	}

}
