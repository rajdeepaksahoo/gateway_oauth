package com.lcwd.gateway.oktaconfig;

import com.lcwd.gateway.models.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Data
public class OktaService {


    private String oktaApiToken;


    private String oktaApiDomain="https://dev-77584981.okta.com/api/v1/users";

    private final RestTemplate restTemplate;

    @Autowired
    public OktaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createUser(User user) {
        String url = "https://" + oktaApiDomain + "/api/v1/users?activate=true";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        oktaApiToken ="eyJraWQiOiJSNXEyRzFvamp6cDI3WkxhUUR1R2ZrakdkN1ZiakdCQXJkUXhpTkNnSmhJIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkVBMnpmN3VKQm9MbXdsWGlLYWFaSEF2REtDQ1FqWDJ4dWhERHdsOXVwTlEub2FyMTZ0Zmhsb3VyUk9UelI1ZDciLCJpc3MiOiJodHRwczovL2Rldi04Mjc1NDY5MS5va3RhLmNvbS9vYXV0aDIvZGVmYXVsdCIsImF1ZCI6ImFwaTovL2RlZmF1bHQiLCJpYXQiOjE2ODk5NDUzNTAsImV4cCI6MTY4OTk0ODk1MCwiY2lkIjoiMG9hYWd5cWo3NHA1NVdLM241ZDciLCJ1aWQiOiIwMHVhZ3ZvYzEwTHJzd0VWajVkNyIsInNjcCI6WyJvZmZsaW5lX2FjY2VzcyIsImVtYWlsIiwib3BlbmlkIiwicHJvZmlsZSJdLCJhdXRoX3RpbWUiOjE2ODk5NDI2NTMsInN1YiI6InJhamRlZXBha3NhaG9vQGdpdGh1Yi5va3RhaWRwIiwibXljbGFpbSI6WyJFdmVyeW9uZSJdfQ.CQ8MiX7RcmrF6AQFtUshuMJRQivozS8WTGG1bWvjl6yjbgBOeufOlPjLXZCiPvcv_exHZUmFg68m74aUCymPdgtMNsHlXtMgeKJlJTCAv0AokntJnJMBdzWnk8dlDuh3LB0oqcozXxt-6TsWiwdEoZ5R6PZ7i2ds3fVRPwBkuNczrC-M6Wp7dfd_wwe2ifcynO5L0WzbXP2KmPm6CfTsCF78nJ7d6riN3ks9TIjSoIt45cf40lbp1svcWtIQdkcOtGIHOue2IoFvheLK8S1Dp4dQj_vf4rcv-UakpXUgUMBN0M6H8pCvq1sjmDS7-HGxRAHfsOxm2V7HT1HutepxNw";
        headers.set("Authorization", "Bearer " + oktaApiToken);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("User created successfully!");
        } else {
            System.out.println("Error creating user. Status code: " + responseEntity.getStatusCodeValue());
        }
    }
}
