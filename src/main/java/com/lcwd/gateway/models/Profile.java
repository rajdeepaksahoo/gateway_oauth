package com.lcwd.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String mobilePhone;
}
