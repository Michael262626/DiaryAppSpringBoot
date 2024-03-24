package michaelDiary.controller;

import michaelDiary.request.*;
import michaelDiary.exceptions.*;
import michaelDiary.model.Entry;
import michaelDiary.services.DiaryServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class DiaryController {
    @Autowired
    private DiaryServicesImpl diaryServicesImplement;
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request){
        try{
            diaryServicesImplement.register(request);
            return "Registration was successful";
        }catch (UserNameExistException e){
            return"UserName exist";
        }catch (UserNotFoundException e){
            return e.getMessage();
        }
    }
    @PatchMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            diaryServicesImplement.login(loginRequest);
            return "Logged in successfully";
        }catch (IncorrectPasswordException e){
            return e.getMessage();
        }
    }
    @PatchMapping("/logout/{name}")
    public String logout(@RequestBody String name){
        try{
            diaryServicesImplement.logout(name);
            return "Logged out successfully";
        }catch (InvalidUserNameException | DiaryNotFoundException e){
            return (e.getMessage());
        }
    }
    @GetMapping("getEntry/{entryId}")
    public String findEntry(@PathVariable("entryId") String id,  @RequestParam(name = "username", defaultValue = "") String username){
        try{
            Entry entry = new Entry();
            diaryServicesImplement.findEntry(id, username);
            return String.format(entry.toString());
        }catch (EntryNotFoundException e){
            return (e.getMessage());
        }
    }
    @DeleteMapping("deleteEntry/{entryId}")
    public String deleteEntry(@PathVariable("entryId") String id, @RequestParam(name = "username", defaultValue = "") String username){
        try{
            diaryServicesImplement.deleteEntry(id,username);
            return "Entry deleted successfully";
        }catch (EntryNotFoundException e){
            return (e.getMessage());
        }
    }
    @DeleteMapping("deleteDiary/{entryId}")
    public String deleteDiary(DeleteUserRequest request){
        try{
            diaryServicesImplement.deleteDiary(request);
            return "Diary is removed";
        }catch (InvalidUserNameException | InvalidPasswordException e){
            return (e.getMessage());
        }
    }
    @PatchMapping("updateEntry")
    public String updateEntry(@RequestBody UpdateUserRequest request) {
        try {
            diaryServicesImplement.updateEntryWith(request);
            return "Entry Updated";
        }catch (EntryUpdateException | EntryNotFoundException e){
            return (e.getMessage());
        }
    }
    @GetMapping("/getEntriesFor/{author}")
    public List<?> getEntriesFor(@PathVariable("author") String username) {
        try {
            return Collections.singletonList(diaryServicesImplement.getEntriesFor(username));
        } catch (DiaryAppException e) {
            return List.of(e.getMessage());
        }
    }
    @PostMapping("createEntry")
    public List<String> createEntry(CreateEntryRequest entryCreation){
        diaryServicesImplement.createEntryWith( entryCreation);
        return Collections.singletonList("Created successfully");
    }
}
