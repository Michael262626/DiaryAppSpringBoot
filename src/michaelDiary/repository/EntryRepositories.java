package michaelDiary.repository;

import michaelDiary.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepositories extends MongoRepository<Entry, String> {
}
