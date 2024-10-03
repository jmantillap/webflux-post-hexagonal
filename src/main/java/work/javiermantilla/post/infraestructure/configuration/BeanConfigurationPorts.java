package work.javiermantilla.post.infraestructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import work.javiermantilla.post.domain.ports.in.PostUseCasePortIn;
import work.javiermantilla.post.domain.ports.out.PostRepositoryPortOut;
import work.javiermantilla.post.domain.usecase.PostUseCaseService;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.adapter.MongoAdapterPersistenceAdapter;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.mapper.PostDocumentMapper;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.repository.PostMongoReactiveRepository;

@Configuration
@RequiredArgsConstructor
public class BeanConfigurationPorts {

	private final PostMongoReactiveRepository postMongoReactiveRepository;
	private final PostDocumentMapper postDocumentMapper;
	
	@Bean
	PostRepositoryPortOut postRepositoryPortOut() {
		return new MongoAdapterPersistenceAdapter(postMongoReactiveRepository,postDocumentMapper);
	}   
	
	@Bean
	PostUseCasePortIn postUseCasePortIn() {
		return new PostUseCaseService(this.postRepositoryPortOut());
	}
	
}
