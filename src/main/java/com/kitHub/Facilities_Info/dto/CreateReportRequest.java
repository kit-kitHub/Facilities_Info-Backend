package com.kitHub.Facilities_info.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReportRequest {
    private String reason;
    private String type;
    private Long reportedContentId;  // 추가
    private String reportedContentType;  // 추가
}
