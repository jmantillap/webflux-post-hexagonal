package work.javiermantilla.post.infraestructure.out.persistence.mongo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.document.PostDocument;

@Repository
public interface PostMongoReactiveRepository extends ReactiveCrudRepository<PostDocument, String> {
	Mono<Boolean> existsByTitle(String title);
}
