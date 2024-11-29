package com.kitHub.Facilities_Info.repository;

import com.kitHub.Facilities_Info.domain.User;
import com.kitHub.Facilities_Info.domain.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(String userId);

    Optional<User> findBySnsId(String snsId);

    Optional<User> findByNickname(String nickname);

}
