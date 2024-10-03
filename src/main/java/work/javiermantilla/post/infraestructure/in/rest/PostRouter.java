package work.javiermantilla.post.infraestructure.in.rest;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import work.javiermantilla.post.aplication.handler.PostHandler;



@Configuration
@Slf4j
@RequiredArgsConstructor
public class PostRouter {
	
	private static final String PATH = "api/post";	
	private static final String PATH_VARIABLE = PATH + "/{id}";
	
	@Bean
    WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
	
	 @Bean
	 RouterFunction<ServerResponse> router(PostHandler handler) {
		 	log.info("Se cargo el router para el manejaror de post");
	        return RouterFunctions.route()
	                .GET(PATH, handler::listPosts)
	                .POST(PATH, handler::savePost)
	                .GET(PATH_VARIABLE, handler::getOne)	                
//	                .PUT(PATH_VARIABLE, handler::update)
//	                .DELETE(PATH_VARIABLE, handler::delete)
	                .build();
	 }
}
