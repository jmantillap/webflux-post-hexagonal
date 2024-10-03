package work.javiermantilla.post.aplication.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import work.javiermantilla.post.aplication.dto.PostDto;
import work.javiermantilla.post.domain.model.PostModel;



@Mapper(componentModel = "spring", 
	unmappedTargetPolicy = ReportingPolicy.IGNORE, 
	unmappedSourcePolicy = ReportingPolicy.IGNORE
	)
public interface PostDtoMapper {
	
	PostModel toPostModel(PostDto postDto);

	PostDto toPostDto(PostModel postModel);
	
	default List<PostDto> toResponseList(List<PostModel> postList) {
        return postList.stream()
                .map(this::toPostDto)
                .toList();
    }
}
