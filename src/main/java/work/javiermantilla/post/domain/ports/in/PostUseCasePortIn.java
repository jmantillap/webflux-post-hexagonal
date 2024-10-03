package work.javiermantilla.post.domain.ports.in;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.javiermantilla.post.domain.model.PostModel;

public interface PostUseCasePortIn {
	Mono<PostModel> savePost(PostModel postModel);
    Flux<PostModel> listPosts();
    Mono<PostModel> updatePost(PostModel postModel, String id);
    Mono<Void> deletePost(String id);
    Mono<PostModel> getByIdPost(String id);
}
