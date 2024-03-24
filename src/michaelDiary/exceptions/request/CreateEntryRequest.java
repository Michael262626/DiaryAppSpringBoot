package michaelDiary.exceptions.request;

import lombok.Data;

@Data
public class CreateEntryRequest {
    private String author;
    private String title;
    private String body;
}
