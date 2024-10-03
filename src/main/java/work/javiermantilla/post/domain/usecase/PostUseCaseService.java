package work.javiermantilla.post.domain.usecase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.javiermantilla.post.domain.model.PostModel;
import work.javiermantilla.post.domain.ports.in.PostUseCasePortIn;
import work.javiermantilla.post.domain.ports.out.PostRepositoryPortOut;

public class PostUseCaseService implements PostUseCasePortIn {

	private final PostRepositoryPortOut postRepositoryPortOut;
			
	public PostUseCaseService(PostRepositoryPortOut postRepositoryPortOut) {	
		this.postRepositoryPortOut = postRepositoryPortOut;
	}

	@Override
	public Mono<PostModel> savePost(PostModel post) {		
		return postRepositoryPortOut.savePost(post);
	}

	@Override
	public Flux<PostModel> listPosts() {
		return postRepositoryPortOut.findAllPosts();
	}

	@Override
	public Mono<PostModel> updatePost(PostModel post, String id) {		
		return postRepositoryPortOut.updatePost(post, id);
	}

	@Override
	public Mono<Void> deletePost(String id) {
		return postRepositoryPortOut.deletePost(id);
	}

	@Override
	public Mono<PostModel> getByIdPost(String id) {		
		return postRepositoryPortOut.getById(id)
			.switchIfEmpty(Mono.error(new RuntimeException("Post Not Exist")))	;
	}

}
