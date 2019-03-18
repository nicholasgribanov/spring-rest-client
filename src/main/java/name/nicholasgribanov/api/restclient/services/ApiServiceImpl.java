package name.nicholasgribanov.api.restclient.services;

import name.nicholasgribanov.api.domain.User;
import name.nicholasgribanov.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    private RestTemplate restTemplate;
    private String apiUrl;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api_url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<User> getUsers(Integer limit) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("limit",limit);

        UserData data = restTemplate.getForObject(builder.toUriString(), UserData.class);
        return data.getData();
    }
}
