package com.kitHub.Facilities_info.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCommentRequest {
    private String content;
    private Long postId;
}
