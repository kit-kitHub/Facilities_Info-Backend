package com.kitHub.Facilities_Info.service;

import com.kitHub.Facilities_Info.domain.User;

import com.kitHub.Facilities_Info.domain.UserReview;
import com.kitHub.Facilities_Info.dto.auth.AddLocalUserRequest;
import com.kitHub.Facilities_Info.dto.auth.AddOauthUserRequest;
import com.kitHub.Facilities_Info.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long saveLocal(AddLocalUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        final String provider = "local";

        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return null;
        }
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .provider(provider)
                .build()).getId();
    }

    public Long saveOAuth(AddOauthUserRequest dto) {
        if(userRepository.findByEmail(dto.getSnsId()).isPresent()) {
            return null;
        }
        String provider = "local";
        return userRepository.save(User.builder()
                .snsId(dto.getSnsId())
                .provider(dto.getProvider())
                .build()).getId();
    }


    public User findById(Long userId) {
        return userRepository.findById(userId)
               .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
               .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findBySnsId(String snsId) {
        return userRepository.findBySnsId(snsId)
               .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User updateNickname(Long userId, String nickname) {
        User user = findById(userId);
        user.updateNickname(nickname);
        return userRepository.save(user);
    }

    public User updateUserReview (User user, UserReview userReview) {
        user.updateUserReview(userReview);
        return userRepository.save(user);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkNicknameExists(String nickname)  {
        return userRepository.findByNickname(nickname).isPresent();
    }

}