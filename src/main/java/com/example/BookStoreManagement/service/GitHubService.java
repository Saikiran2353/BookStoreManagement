package com.example.BookStoreManagement.service;

import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GitHubService {

	private final String url = "https://api.github.com/users/";
	

	public int getFollowersCount(String userName) {
        String apiUrl = url + userName;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        
        HttpEntity<String> entity = new HttpEntity<>(headers);  
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);

        
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("followers")) {
                return (int) responseBody.get("followers");
            }
           
        }
            return -1;
        

	}
}