package work.javiermantilla.post.infraestructure.out.persistence.mongo.adapter;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import work.javiermantilla.post.domain.model.PostModel;
import work.javiermantilla.post.domain.ports.out.PostRepositoryPortOut;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.document.PostDocument;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.mapper.PostDocumentMapper;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.repository.PostMongoReactiveRepository;

@RequiredArgsConstructor
public class MongoAdapterPersistenceAdapter implements PostRepositoryPortOut {

	private final PostMongoReactiveRepository postMongoReactiveRepository;
	private final PostDocumentMapper postDocumentMapper;
	
	@Override
	public Mono<Boolean> postExistsWithTitle(String title) {		
		return postMongoReactiveRepository.existsByTitle(title);
	}

	@Override
	public Mono<PostModel> savePost(PostModel post) {
		PostDocument postDocument= postDocumentMapper.toPostDocument(post);
		postDocument.setCreatedOn(LocalDateTime.now());
		postDocument.setUpdatedOn(LocalDateTime.now());
		
		return postMongoReactiveRepository.save(postDocument)
				.map(postSave->{
					post.setId(postSave.getId());
					post.setCreatedOn(postSave.getCreatedOn());
					post.setUpdatedOn(postSave.getUpdatedOn());					
					return post;
				});
	}

	@Override
	public Flux<PostModel> findAllPosts() {		
		 return postMongoReactiveRepository.findAll()
	                .map(postDocumentMapper::toPostModel)
	                .switchIfEmpty(Flux.empty());
	}

	@Override
	public Mono<PostModel> updatePost(PostModel post, String id) {
		return postMongoReactiveRepository.findById(id)
                .flatMap(savedPost -> {
                	PostDocument postUpdate = postDocumentMapper.toPostDocument(post);
                	postUpdate.setId(savedPost.getId());
                	return postMongoReactiveRepository.save(postUpdate);
                })
                .map(postDocumentMapper::toPostModel);
	}

	@Override
	public Mono<Void> deletePost(String id) {
		 return postMongoReactiveRepository.deleteById(id);
	}

	@Override
	public Mono<PostModel> getById(String id) {
		return postMongoReactiveRepository.findById(id)
				.map(postDocumentMapper::toPostModel)				
				.switchIfEmpty(Mono.empty());
	}

}
