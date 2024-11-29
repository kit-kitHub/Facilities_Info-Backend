package com.kitHub.Facilities_Info.service;

import com.kitHub.Facilities_Info.domain.UserReview;
import com.kitHub.Facilities_Info.repository.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;

    public List<UserReview> getReviewsByFacilityId(Long facilityId) {
        return userReviewRepository.findByFacilityId(facilityId);
    }
}
