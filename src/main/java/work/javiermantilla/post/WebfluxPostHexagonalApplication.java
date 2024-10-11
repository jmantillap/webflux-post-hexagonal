package work.javiermantilla.post;

import java.time.LocalDateTime;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import work.javiermantilla.post.aplication.dto.PostDto;
import work.javiermantilla.post.aplication.mapper.PostDtoMapper;
import work.javiermantilla.post.domain.ports.in.PostUseCasePortIn;

@SpringBootApplication
@RequiredArgsConstructor // para inicializar valores demo en la base de datos de mongo
@Log4j2 
public class WebfluxPostHexagonalApplication implements CommandLineRunner { 

	
	//atributos temporales de prueba para alimentar la base de datos mongo-db
	private final ReactiveMongoTemplate mongoTemplate;
	private final PostUseCasePortIn productUseCase;
	private final PostDtoMapper mapper; 
		
	public static void main(String[] args)  {
		SpringApplication.run(WebfluxPostHexagonalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
// 		mongoTemplate.dropCollection("categorias").subscribe();
//		Categoria electronico = new Categoria("Electrónico");
//		Categoria deporte = new Categoria("Deporte");
//		Categoria computacion = new Categoria("Computación");
//		Categoria muebles = new Categoria("Muebles");

		Flux.just(new PostDto("titulo1","descripcion1","cuerpo1"),
				new PostDto("titulo2","descripcion2","cuerpo2"),
				new PostDto("titulo3","descripcion3","cuerpo3"), 
				new PostDto("titulo4","descripcion4","cuerpo4"),
				new PostDto("titulo5","descripcion5","cuerpo5"),
				new PostDto("titulo6","descripcion6","cuerpo6"),
				new PostDto("titulo7","descripcion7","cuerpo7"),
				new PostDto("titulo8","descripcion8","cuerpo8"),
				new PostDto("titulo9","descripcion9","cuerpo9"))
			.flatMap(post -> {
					post.setCreatedOn(LocalDateTime.now());
					post.setUpdatedOn(LocalDateTime.now());
					return productUseCase.savePost(mapper.toPostModel(post));
			}).subscribe(post -> log.info("Insert: " + post.getId() + " " + post.getTitle()));

	}

		
}


