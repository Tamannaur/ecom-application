package com.app.ecom.dto;

import com.app.ecom.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String fName, lName;
    private String email;
    private String phone;
    private AddressDTO address;
}
