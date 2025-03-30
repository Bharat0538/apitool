package com.apitool.apitool.repo;


import com.apitool.apitool.entity.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {
    Optional<RequestHistory> findByMethodAndUrlAndRequestBody(String method, String url, String requestBody);
}
