package com.kitHub.Facilities_Info.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateReviewRequest {
    private String reviewComment;
    private Integer rating;
}
