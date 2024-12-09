package com.kitHub.Facilities_info.service.facility;

import com.kitHub.Facilities_info.domain.facility.UserReview;
import com.kitHub.Facilities_info.repository.UserReviewRepository;
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
