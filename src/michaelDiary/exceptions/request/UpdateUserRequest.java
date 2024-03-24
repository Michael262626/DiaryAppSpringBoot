package michaelDiary.exceptions.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String author;
    private String title;
    private String body;
    private String id;
}
