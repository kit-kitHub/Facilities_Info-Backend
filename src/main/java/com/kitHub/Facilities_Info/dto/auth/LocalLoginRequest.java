package com.kitHub.Facilities_Info.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocalLoginRequest {
    private String Email;
    private String password;
}
