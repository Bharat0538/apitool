package com.apitool.apitool.service;

import com.apitool.apitool.dto.ApiRequest;
import com.apitool.apitool.dto.ApiResponse;
import com.apitool.apitool.entity.RequestHistory;
import com.apitool.apitool.repo.RequestHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final RequestHistoryRepository historyRepository;

    public ApiResponse sendRequest(ApiRequest request, Map<String, String> headers, Map<String, String> queryParams) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            String url = request.getUrl();
            if (queryParams != null && !queryParams.isEmpty()) {
                String paramString = queryParams.entrySet().stream()
                        .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                        .collect(Collectors.joining("&"));
                url += (url.contains("?") ? "&" : "?") + paramString;
            }

            HttpUriRequestBase httpRequest;

            switch (request.getMethod().toUpperCase()) {
                case "GET" -> httpRequest = new HttpGet(url);
                case "POST" -> {
                    httpRequest = new HttpPost(url);
                    ((HttpPost) httpRequest).setEntity(new StringEntity(request.getBody(), StandardCharsets.UTF_8));
                }
                case "PUT" -> {
                    httpRequest = new HttpPut(url);
                    ((HttpPut) httpRequest).setEntity(new StringEntity(request.getBody(), StandardCharsets.UTF_8));
                }
                case "DELETE" -> httpRequest = new HttpDelete(url);
                case "PATCH" -> {
                    httpRequest = new HttpPatch(url);
                    ((HttpPatch) httpRequest).setEntity(new StringEntity(request.getBody(), StandardCharsets.UTF_8));
                }
                default -> {
                    return new ApiResponse(400, "Unsupported HTTP method: " + request.getMethod());
                }
            }

            // Set headers
            if (headers != null) {
                headers.forEach(httpRequest::addHeader);
            }

            var response = client.execute(httpRequest);
            String body = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                    .lines().collect(Collectors.joining("\n"));

            Optional<RequestHistory> existing = historyRepository.findByMethodAndUrlAndRequestBody(
                    request.getMethod(), url, request.getBody()
            );

            RequestHistory history = existing.orElse(new RequestHistory());
            history.setMethod(request.getMethod());
            history.setUrl(url);
            history.setRequestBody(request.getBody());
            history.setResponseBody(body);
            history.setStatusCode(response.getCode());
            history.setTimestamp(LocalDateTime.now());

            historyRepository.save(history);


            return new ApiResponse(response.getCode(), body);

        } catch (Exception e) {
            return new ApiResponse(500, "Error: " + e.getMessage());
        }
    }


}
