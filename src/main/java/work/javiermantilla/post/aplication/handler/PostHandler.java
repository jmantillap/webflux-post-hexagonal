package work.javiermantilla.post.aplication.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.javiermantilla.post.aplication.dto.PostDto;
import work.javiermantilla.post.aplication.mapper.PostDtoMapper;
import work.javiermantilla.post.aplication.validation.ObjectValidator;
import work.javiermantilla.post.domain.model.PostModel;
import work.javiermantilla.post.domain.ports.in.PostUseCasePortIn;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostHandler {

	private final PostUseCasePortIn postUseCasePortIn;
	private final PostDtoMapper postDtoMapper;
	private final ObjectValidator objectValidator;

	public Mono<ServerResponse> listPosts(ServerRequest serverRequest) {
		log.info(serverRequest.headers().toString());
		Flux<PostDto> allPosts = postUseCasePortIn.listPosts()
				.map(postDtoMapper::toPostDto)
				.switchIfEmpty(Flux.empty());

		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(allPosts, PostDto.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> savePost(ServerRequest serverRequest) {
		Mono<PostDto> postDtoMono = serverRequest.bodyToMono(PostDto.class)
				.doOnNext(objectValidator::validate);
		return postDtoMono
				.flatMap(postDto -> 
						ServerResponse.status(HttpStatus.CREATED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(
							postUseCasePortIn.savePost(postDtoMapper.toPostModel(postDto)), 
							PostDto.class
							)
				.switchIfEmpty(ServerResponse.notFound().build()));
	}

	public Mono<ServerResponse> getOne(ServerRequest request) {		
//		Mono<PostDto> postDto1 = postUseCasePortIn.getByIdPost(request.pathVariable("id"))
//				.map(postDtoMapper::toPostDto);		
//		return ServerResponse.ok()
//				.contentType(MediaType.APPLICATION_JSON)
//				.body(postDto, PostDto.class)
//				.switchIfEmpty(ServerResponse.notFound().build());
		return postUseCasePortIn.getByIdPost(request.pathVariable("id"))
				.flatMap(p->{
						Mono<PostDto> postDto= Mono.just(postDtoMapper.toPostDto(p));
						return ServerResponse.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(postDto,PostDto.class) 
								.switchIfEmpty(ServerResponse.notFound().build());
				});
	}
	
	public Mono<ServerResponse> updatePost(ServerRequest request) {
		String id= request.pathVariable("id");
		Mono<PostDto> postDtoMono = request.bodyToMono(PostDto.class)
									.doOnNext(objectValidator::validate);
				
		return postDtoMono.flatMap(p->{
					Mono<PostModel> postUpdate= postUseCasePortIn
									.updatePost(postDtoMapper.toPostModel(p),id);
					return ServerResponse.status(HttpStatus.OK)
							.contentType(MediaType.APPLICATION_JSON)
						.body(postUpdate,PostDto.class)
						.switchIfEmpty(ServerResponse.notFound().build());
		});
	}
	
	 public Mono<ServerResponse> deletePost(ServerRequest serverRequest) {
	        String id = serverRequest.pathVariable("id");
	        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	        return ServerResponse
	                .status(HttpStatus.NO_CONTENT)
	                .build(postUseCasePortIn.deletePost(id))
	                .switchIfEmpty(notFound);
	    }
	

}
