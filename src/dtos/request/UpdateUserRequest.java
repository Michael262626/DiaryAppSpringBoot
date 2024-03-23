package dtos.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class UpdateUserRequest {
    private String author;
    private String title;
    private String body;
    private String id;
}
