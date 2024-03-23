package model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Entry {
        private String id;
        private String title;
        private String body;
        private String author;
        private final LocalDateTime localDateTime = LocalDateTime.now();
        @Override
        public String toString() {
            return "Entry{" +
                    "id =" + getId() +
                    ", title ='" + getTitle() + '\'' +
                    ", body ='" + getBody() + '\'' +
                    ", localDateTime =" + getLocalDateTime() +
                    '}';
        }
}
