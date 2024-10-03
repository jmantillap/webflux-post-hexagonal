package work.javiermantilla.post.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostModel implements Serializable {
	
	private static final long serialVersionUID = 4921099655409293622L;
	private String id;
	private String title;
	private String description;
	private String body;	
	private LocalDateTime createdOn;	
	private LocalDateTime updatedOn;
	
	public PostModel() {	}
	
	public PostModel(String id, String title, String description, String body, LocalDateTime createdOn,
			LocalDateTime updatedOn) {	
		this.id = id;
		this.title = title;
		this.description = description;
		this.body = body;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
