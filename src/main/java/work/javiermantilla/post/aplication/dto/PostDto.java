package work.javiermantilla.post.aplication.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private String id;
	private String title;
	private String description;
	private String body;	
	private LocalDateTime createdOn;	
	private LocalDateTime updatedOn;
	public PostDto(String title, String description, String body) {
		super();
		this.title = title;
		this.description = description;
		this.body = body;
		
	}
	
}
