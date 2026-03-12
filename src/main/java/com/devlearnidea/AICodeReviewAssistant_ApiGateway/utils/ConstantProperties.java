package com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "constant")
@Data
public class ConstantProperties
{
    private String defaultTenant;
    private String coreEngineServiceUrl;
}
