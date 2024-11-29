package com.kitHub.Facilities_Info.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddOauthUserRequest {
    private String provider;
    private String snsId;
    private String nickname;
}
