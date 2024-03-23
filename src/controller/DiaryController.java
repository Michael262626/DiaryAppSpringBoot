package controller;

import dtos.request.*;
import exceptions.*;
import model.Entry;
import services.DiaryServicesImpl;

public class DiaryController {
    private Entry entry = new Entry();
    private  DiaryServicesImpl diaryServicesImplement;
    public String registerUser(RegisterRequest request){
        try{
            diaryServicesImplement.register(request);
            return "Registration was successful";
        }catch (UserNameExistException e){
            return"UserName exist";
        }
    }
    public String login(LoginRequest loginRequest){

        diaryServicesImplement.login(loginRequest);
        return "Logged in successfully";



    }
    public String logout(String name){
        try{
            diaryServicesImplement.logout(name);
            return "Logged out successfully";
        }catch (InvalidUserNameException | DiaryNotFoundException e){
            return (e.getMessage());
        }
    }
    public String findEntry(String id, String username){
        try{
            diaryServicesImplement.findEntry(id, username);
            return String.format(entry.toString());
        }catch (EntryNotFoundException e){
            return (e.getMessage());
        }
    }
    public String deleteEntry(String id, String username){
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
