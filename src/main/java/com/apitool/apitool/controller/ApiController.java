package com.apitool.apitool.controller;

import com.apitool.apitool.dto.ApiRequest;
import com.apitool.apitool.dto.ApiResponse;
import com.apitool.apitool.repo.RequestHistoryRepository;
import com.apitool.apitool.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;
    private final RequestHistoryRepository historyRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("apiRequest", new ApiRequest());
        return "index";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("historyList", historyRepository.findAll());
        return "history";
    }


    @PostMapping("/send")
    public String sendRequest(
            @ModelAttribute ApiRequest apiRequest,
            @RequestParam(required = false) List<String> headers,
            @RequestParam(required = false) List<String> params,
            Model model) {

        Map<String, String> headerMap = new LinkedHashMap<>();
        if (headers != null) {
            for (int i = 0; i < headers.size() - 1; i += 2) {
                String key = headers.get(i);
                String value = headers.get(i + 1);
                if (key != null && !key.isBlank()) {
                    headerMap.put(key.trim(), value != null ? value.trim() : "");
                }
            }
        }

        Map<String, String> paramMap = new LinkedHashMap<>();
        if (params != null) {
            for (int i = 0; i < params.size() - 1; i += 2) {
                String key = params.get(i);
                String value = params.get(i + 1);
                if (key != null && !key.isBlank()) {
                    paramMap.put(key.trim(), value != null ? value.trim() : "");
                }
            }
        }

        // Send both DTO and maps to the service
        ApiResponse response = apiService.sendRequest(apiRequest, headerMap, paramMap);

        model.addAttribute("apiRequest", apiRequest);
        model.addAttribute("apiResponse", response);
        model.addAttribute("historyList", historyRepository.findAll());
        return "index";
    }


}
