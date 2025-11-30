package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private int id = 1;

    public List<User> fetchUsers(){
        return users;
    }

    public List<User> updateUser(int id, User user){
        return users.stream().filter(val->val.getId() == id)
                .map(val->{
                    val.setFName(user.getFName());
                    val.setLName(user.getLName());
                    return val;
                }).toList();
    }

    public void createUser(User user){
        user.setId(id++);
        users.add(user);
    }

    public User fetchUserById(int id){
        return users.stream().filter(val->val.getId() == id)
                .findFirst().orElse(null);
    }
}
