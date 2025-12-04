package com.app.ecom.service;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.model.UserRole;
import com.app.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private int id = 1;

    public List<UserResponse> fetchUsers(){
        return userRepository.findAll()
                .stream().map(UserService::mapUserResponse)
                .toList();
    }

    public UserResponse updateUser(int id, UserRequest userRequest){
        return mapUserResponse(userRepository.findById(id)
                .map(val->{
                    updateUserFromReq(val,userRequest);
                    return val;
                }).orElse(null));
    }

    public void createUser(UserRequest req){
        //user.setId(id++);
        User user = new User();
        updateUserFromReq(user,req);
        userRepository.save(user);
    }

    public String deleteUser(int id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }

    public UserResponse fetchUserById(int id){
        return mapUserResponse(userRepository.findById(id).orElse(null));
    }

    public static void updateUserFromReq(User user, UserRequest req){
        user.setFName(req.getFName());
        user.setLName(req.getLName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUserRole(UserRole.CUSTOMER);
        if(req.getAddress() != null){
            Address address = Address.builder()
                    .city(req.getAddress().getCity())
                    .country(req.getAddress().getCountry())
                    .zipCode(req.getAddress().getZipCode())
                    .street(req.getAddress().getStreet())
                    .state(req.getAddress().getState())
                    .build();
            user.setAddress(address);
        }
    }

    public static UserResponse mapUserResponse(User user){
        return Optional.ofNullable(user).map(user1->UserResponse.builder().id(Optional.ofNullable(String.valueOf(user.getId())).orElse(null))
                .fName(user.getFName())
                .lName(user.getLName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .userRole(user.getUserRole())
                .address(Optional.ofNullable(user.getAddress())
                        .map(val->AddressDTO.builder()
                                .city(val.getCity())
                                .state(val.getState())
                                .country(val.getCountry())
                                .street(val.getStreet())
                                .zipCode(val.getZipCode())
                                .build())
                        .orElse(null))
                .build()).orElse(null);
    }
}
