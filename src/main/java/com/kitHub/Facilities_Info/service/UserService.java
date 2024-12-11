package com.kitHub.Facilities_info.service;

import com.kitHub.Facilities_info.domain.user.User;
import com.kitHub.Facilities_info.domain.facility.UserReview;
import com.kitHub.Facilities_info.dto.auth.AddLocalUserRequest;
import com.kitHub.Facilities_info.dto.auth.AddOauthUserRequest;
import com.kitHub.Facilities_info.repository.UserRepository;
import com.kitHub.Facilities_info.util.Authentication.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private AuthenticationProvider authenticationProvider;

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
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

    public User findBySnsId(String snsId) {
        return userRepository.findBySnsId(snsId)
                .orElseThrow(() -> new IllegalArgumentException("User with snsId " + snsId + " not found"));
    }

    public User updateNickname(Long userId, String nickname) {
        User user = findById(userId);
        user.updateNickname(nickname);
        return userRepository.save(user);
    }

    public User updateUserReview(User user, UserReview userReview) {
        user.updateUserReview(userReview);
        return userRepository.save(user);
    }

    public User getUserInfoIfMatchesAuthor(Long AuthorUserId) {
        User loginUser = authenticationProvider.getUserInfoFromSecurityContextHolder();
        return loginUser.getId().equals(AuthorUserId) ? loginUser : null;
    }

    public boolean checkEmailExists(String email) {
        boolean exists = userRepository.findByEmail(email).isPresent();
        System.out.println("Email check for '" + email + "': " + exists);
        return exists;
    }

    public boolean checkNicknameExists(String nickname) {
        boolean exists = userRepository.findByNickname(nickname).isPresent();
        System.out.println("Nickname check for '" + nickname + "': " + exists);
        return exists;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
