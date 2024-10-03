package work.javiermantilla.post.infraestructure.out.persistence.mongo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import work.javiermantilla.post.domain.model.PostModel;
import work.javiermantilla.post.infraestructure.out.persistence.mongo.document.PostDocument;

@Mapper(componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE,
		unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PostDocumentMapper {
	
	PostDocument toPostDocument(PostModel postModel);
	//@Mapping(source = "description", target = "description")
	PostModel toPostModel(PostDocument postdocument);
	
}
