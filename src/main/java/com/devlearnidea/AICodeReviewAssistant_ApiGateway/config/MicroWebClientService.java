package com.devlearnidea.AICodeReviewAssistant_ApiGateway.config;

import com.devlearnidea.AICodeReviewAssistant_ApiGateway.dto.TenantContextDTO;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.exception.CustomException;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.response.ApiResponse;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils.ConstantProperties;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils.HttpUtilDTO;
import com.devlearnidea.AICodeReviewAssistant_ApiGateway.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.core.type.TypeReference;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MicroWebClientService
{

    private final WebClient webClient;
    private final ConstantProperties constantProperties;

    private final ObjectMapper objectMapper = HttpUtils.mapper();

    // --- GET ALL STS ---
    public List<String> getAllSts()
            throws CustomException
    {
        TenantContextDTO tenant = new TenantContextDTO(constantProperties.getDefaultTenant(), "System");
        HttpHeaders headers = HttpUtils.createHeaders(tenant).getHeaders();

        String url = constantProperties.getCoreEngineServiceUrl() + "tenant/getall/sts";
        log.info("Fetching all STS from URL: {}", url);

        Mono<ApiResponse<?>> responseMono = webClient.get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});

        ApiResponse<?> response = responseMono.block();
        return handleResponse(response, new TypeReference<List<String>>() {});
    }

    // --- RESPONSE HANDLER ---
    public <T> T handleResponse(ApiResponse<?> response, TypeReference<T> typeReference) throws CustomException
    {
        if (response != null && response.getStatus() == ApiResponse.Status.SUCCESS) {
            return objectMapper.convertValue(response.getResponse(), typeReference);
        }
        else if (response != null && response.getMessage() != null) {
            throw new CustomException(response.getMessage());
        }
        else {
            throw new CustomException("Something went wrong while calling the service");
        }
    }


    public static <T, R extends Collection<T>> R commonApiReturnResponse(
            TypeReference<?> requestType,
            ApiResponse<?> apiResponse)
            throws CustomException
    {
        return apiReturnResponse(requestType, apiResponse);
    }

    @SuppressWarnings("unchecked")
    public static <T> T apiReturnResponse(TypeReference<?> requestType,
            ApiResponse<?> apiResponse)
            throws CustomException
    {

        if (apiResponse != null
                && apiResponse.getStatus().equals(ApiResponse.Status.SUCCESS)) {

            ObjectMapper mapper = HttpUtils.mapper();

            return (T) mapper.convertValue(apiResponse.getResponse(),
                    requestType);
        }
        else if (apiResponse != null && apiResponse.getMessage() != null) {
            throw new CustomException(apiResponse.getMessage());
        }
        else {
            throw new CustomException();
        }
    }

    /**
     * Common API Call (WebClient)
     */
    public final <T, P, R> R apiCallSingleObjectWithSingleReturn(
            TypeReference<?> requestType,
            T input,
            HttpMethod method,
            R emptyOutput,
            TenantContextDTO tenantContextDTO,
            String apiRoute,
            Map<String, P> requestParams)
    {

        try {

            String url = getApiUrl(apiRoute, requestParams);

            ApiResponse<?> apiResponse =
                    requestEntityCreator(input, tenantContextDTO, method, url);

            return apiReturnResponse(requestType, apiResponse);

        }
        catch (CustomException e) {
            return emptyOutput;
        }
    }

    /**
     * WebClient request execution
     */
    private <T> ApiResponse<?> requestEntityCreator(
            T input,
            TenantContextDTO tenantContextDTO,
            HttpMethod method,
            String url)
    {

        HttpUtilDTO httpUtilDTO = HttpUtils.createHeaders(tenantContextDTO);

        Mono<ApiResponse<?>> responseMono;

        if (method != HttpMethod.GET) {

            log.info("Calling API : {}", url);

            responseMono = webClient.method(method)
                    .uri(url)
                    .headers(h -> h.addAll(httpUtilDTO.getHeaders()))
                    .bodyValue(input)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});
        }
        else {

            responseMono = webClient.method(method)
                    .uri(url)
                    .headers(h -> h.addAll(httpUtilDTO.getHeaders()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<?>>() {});
        }

        return responseMono.block();
    }

    /**
     * URL with formatted params
     */
    @SafeVarargs
    private <P> String getApiUrl(String apiRoute, P... requestParams)
    {

        String url = apiRoute;

        return url.formatted((Object[]) requestParams);
    }

    /**
     * URL with query params
     */
    private <P> String getApiUrl(String apiRoute, Map<String, P> requestParams)
    {

        StringBuilder urlBuilder = new StringBuilder(apiRoute);

        if (requestParams != null && !requestParams.isEmpty()) {

            urlBuilder.append("?");

            for (Map.Entry<String, P> entry : requestParams.entrySet()) {

                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }

            urlBuilder.setLength(urlBuilder.length() - 1);
        }

        return urlBuilder.toString();
    }
}
