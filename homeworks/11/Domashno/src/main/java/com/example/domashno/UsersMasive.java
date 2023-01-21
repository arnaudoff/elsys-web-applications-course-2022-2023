package com.example.domashno;

import java.util.HashMap;

public class UsersMasive {
    private int users=0;
    private static HashMap<Integer,Users> UsersMap = new HashMap<>();
    boolean Add(Users users){
        UsersMap.put(this.users,users);
        this.users++;
        return true;
    }

    public HashMap<Integer, Users> getUsersMap() {
        return UsersMap;
    }
    public boolean isLogedin(String Name,String Password){
        for (int i = 0; i < UsersMap.size(); i++) {
            if ((UsersMap.get(i).getName().equals(Name))&&(UsersMap.get(i).getPassword().equals(Password))) {
                return true;
            }
        }
        return false;
    }
}
