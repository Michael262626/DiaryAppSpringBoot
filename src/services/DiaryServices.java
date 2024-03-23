package services;

import dtos.request.*;
import model.Diary;
import model.Entry;
import org.springframework.stereotype.Service;

import java.util.List;
public interface DiaryServices {
    void register(RegisterRequest registerRequest);
    Diary findDiaryBy(String username);
    void login(LoginRequest loginRequest);
    void logout(String username);
    void createEntryWith(CreateEntryRequest createEntryRequest);
    void updateEntryWith(UpdateUserRequest updateEntryRequest);
    void deleteEntry(String id, String username);
    Entry getEntry(String id, String username);
    List<Entry> getEntriesFor(String username);
    long count();
    long numberOfEntries();
    void deleteDiary(DeleteUserRequest request);
}
