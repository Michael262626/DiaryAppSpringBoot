package michaelDiary.request;

import lombok.Data;

@Data
public class DeleteUserRequest {
    private String username;
    private String password;
}
