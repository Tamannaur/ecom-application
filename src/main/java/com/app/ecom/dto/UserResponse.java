package com.app.ecom.dto;

import com.app.ecom.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String fName, lName;
    private String email;
    private String phone;
    private UserRole userRole;
    private AddressDTO address;
}
