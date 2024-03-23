package controller;

import dtos.request.*;
import exceptions.*;
import model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.DiaryServicesImpl;
@RestController
public class DiaryController {
    private Entry entry = new Entry();
    @Autowired
    private  DiaryServicesImpl diaryServicesImplement;
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request){
        try{
            diaryServicesImplement.register(request);
            return "Registration was successful";
        }catch (UserNameExistException e){
            return"UserName exist";
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
    public String deleteDiary(DeleteUserRequest request){
        try{
            diaryServicesImplement.deleteDiary(request);
            return "Diary is removed";
        }catch (InvalidUserNameException | InvalidPasswordException e){
            return (e.getMessage());
        }
    }
    public String updateEntry(UpdateUserRequest request) {
        try {
            diaryServicesImplement.updateEntryWith(request);
            return "Entry Updated";
        }catch (EntryUpdateException | EntryNotFoundException e){
            return (e.getMessage());
        }
    }
    public String createEntry(CreateEntryRequest entryCreation){
        diaryServicesImplement.createEntryWith( entryCreation);
        return "Created successfully";
    }
}
