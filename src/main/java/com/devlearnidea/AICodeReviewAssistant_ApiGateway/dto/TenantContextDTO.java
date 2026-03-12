package com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TenantContextDTO
{
    private String tenantId;
    private String userName;
}
