package com.app.ecom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private int id = 1;

    public List<User> fetchUsers(){
        return userRepository.findAll();
    }

    public User updateUser(int id, User user){
        return userRepository.findById(id)
                .map(val->{
                    val.setFName(user.getFName());
                    val.setLName(user.getLName());
                    return val;
                }).orElse(null);
    }

    public void createUser(User user){
        //user.setId(id++);
        userRepository.save(user);
    }

    public User fetchUserById(int id){
        return userRepository.findById(id).orElse(null);
    }
}
