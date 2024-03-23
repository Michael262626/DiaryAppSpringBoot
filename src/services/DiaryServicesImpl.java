package services;

import dtos.request.*;
import exceptions.*;
import model.Diary;
import model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DiaryRepositories;
import repository.EntryRepositories;

import java.util.Optional;

@Service
public class DiaryServicesImpl implements DiaryServices{
    @Autowired
    private DiaryRepositories diaryRepositories;
    @Autowired
    private EntryRepositories entryRepositories;
    @Autowired
    private EntryServicesImpl entryServicesImplement;

    @Override
    public void register(RegisterRequest request) {
        Diary newDiary = new Diary();
        if(isDiaryExisting(request.getUsername()))throw new UserNameExistException("User Name Existed Already");
        newDiary.setUsername(request.getUsername().toLowerCase());
        newDiary.setPassword(request.getPassword());
        diaryRepositories.save(newDiary);
    }

    @Override
    public Diary findDiaryBy(String username) {
        Optional<Diary> foundDiary = diaryRepositories.findById(username.toLowerCase());
        if (foundDiary.isEmpty()) throw new UserNotFoundException("User not found.");

        return foundDiary.get();
    }

    @Override
    public void login(LoginRequest request) {
        Diary foundDiary = findDiaryBy(request.getUsername().toLowerCase());
        if (isPasswordIncorrect(foundDiary, request.getPassword())) throw new IncorrectPasswordException("Password is incorrect.");
        foundDiary.setLocked(false);

        diaryRepositories.save(foundDiary);
    }
    private boolean isPasswordIncorrect(Diary foundDiary, String password) {
        if (foundDiary == null) {
            throw new NullPointerException("Diary object is null");
        }

        if (!foundDiary.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid Password");
        }
        return false;
    }

    private  void validateUsername(Diary diary) {
        if(diary ==null)throw new InvalidUserNameException("InValid UserName Provide A Valid Username");
    }

    private boolean isDiaryExisting(String username){
        for(Diary diary: diaryRepositories.findAll()){
            if(diary.getUsername().equals(username))return true;
        }
        return false;
    }
    @Override
    public long count() {
        return diaryRepositories.count();
    }
    @Override
    public long numberOfEntries() {
        return entryRepositories.findAll().size();
    }

    @Override
    public void logout(String username) {
        Diary diary = findDiaryBy(username);
        if (diary == null) {
            throw new DiaryNotFoundException("Diary not found for username: " + username);
        }
        validateUsername(diary);
        diary.setLocked(true);
    }

    @Override
    public void updateEntryWith(UpdateUserRequest updateEntryRequest) {
        Diary foundDiary = findDiaryBy(updateEntryRequest.getAuthor().toLowerCase());
        checkLockStatusOf(foundDiary);
        String title = updateEntryRequest.getTitle();
        String body = updateEntryRequest.getBody();
        Entry entry = new Entry();
        if (entry.getBody().equals(body)) {
            throw new EntryUpdateException("Body was not changed '" + title + "'");
        }
        entry.setId(updateEntryRequest.getId());
        entry.setTitle(updateEntryRequest.getTitle());
        entry.setBody(updateEntryRequest.getBody());
        entryServicesImplement.save(entry);
    }


    @Override
    public void deleteEntry(String id, String username) {
            Entry entry = findEntry(id, username);
            entryRepositories.delete(entry);
    }

    @Override
    public Entry getEntry(String id, String username) {
        Diary foundDiary = findDiaryBy(username.toLowerCase());
        checkLockStatusOf(foundDiary);

        return entryServicesImplement.getEntry(id);
    }
    private void checkLockStatusOf(Diary diary) {
        if (diary.isLocked()) throw new IllegalDiaryStateException("You need to login to use this service.");
    }

    @Override
    public Optional<Entry> getEntriesFor(String username) {
        Diary foundDiary = findDiaryBy(username.toLowerCase());
        checkLockStatusOf(foundDiary);

        return entryServicesImplement.getEntriesFor(username);
    }
    @Override
    public void createEntryWith(CreateEntryRequest entryCreation) {
        Entry entry = new Entry();
        entry.setTitle(entryCreation.getTitle());
        entry.setBody(entryCreation.getBody());
        entryRepositories.save(entry);
    }

    @Override
    public void deleteDiary(DeleteUserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Diary diary = diaryRepositories.findByUsername(username);
        if (diary == null) {
            throw new DiaryNotFoundException("Diary not found for username: " + username);
        }
        if (!diary.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid password for username: " + username);
        }
         diaryRepositories.delete(diary);
    }

    public Entry findEntry(String id, String username) {
        for (Entry entry : entryRepositories.findAll() ){
            if (entry.getId().equals(id)||entry.getAuthor().equals(username)) {
                return entry;
            }
        }
        throw new EntryNotFoundException("Entry with title '" +id +"and"+ username + "' was not found");
    }
}

