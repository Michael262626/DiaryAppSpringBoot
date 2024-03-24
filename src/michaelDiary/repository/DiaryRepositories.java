package michaelDiary.repository;

import michaelDiary.model.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepositories extends MongoRepository<Diary, String> {
    Diary findByUsername(String username);
}
