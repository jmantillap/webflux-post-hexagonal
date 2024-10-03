package work.javiermantilla.post.domain.ports.out;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.javiermantilla.post.domain.model.PostModel;

public interface PostRepositoryPortOut {
	
	Mono<PostModel> savePost(PostModel post);

    Flux<PostModel> findAllPosts();

    Mono<PostModel> updatePost(PostModel post, String id);

    Mono<Void> deletePost(String id);
	
	Mono<Boolean> postExistsWithTitle(String title);
	
	Mono<PostModel> getById(String id);
	
}
