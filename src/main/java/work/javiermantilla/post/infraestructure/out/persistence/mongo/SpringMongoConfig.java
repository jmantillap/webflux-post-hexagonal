package work.javiermantilla.post.infraestructure.out.persistence.mongo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;


import com.mongodb.reactivestreams.client.MongoClient;

@Configuration
public class SpringMongoConfig {

	 	 
	//remove '_class’ field
	@Bean
	ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
		ReactiveMongoTemplate template = new ReactiveMongoTemplate(mongoClient, "ems");
		MappingMongoConverter converter = (MappingMongoConverter) template.getConverter();
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		converter.afterPropertiesSet();
		return template;
	}
}