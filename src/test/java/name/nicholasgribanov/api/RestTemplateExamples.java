package name.nicholasgribanov.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Retention;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {
    public static final String API_ROOT = "https://api.predic8.de:443/shop";

    @Test
    public void getCategories() {
        String apiUrl = API_ROOT + "/categories/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateCustomer() {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Roman");
        postMap.put("lastname", "Varlamov");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Roman 2");
        postMap.put("lastname", "Varlamov 2");

        restTemplate.put(apiUrl + id, postMap);

        JsonNode jsonNode1 = restTemplate.getForObject(apiUrl + id, JsonNode.class);

        System.out.println(jsonNode1.toString());

    }

    @Test
    public void updateCustomerUsingPatch() {
        String apiUrl = API_ROOT + "/customers/";

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Oleg");
        postMap.put("lastname", "Khovrin");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl,postMap,JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String id = jsonNode.get("customer_url").textValue().split("/")[3];
        System.out.println("Created customer id: " + id);

        postMap.put("firstname","Oleg 2");
        postMap.put("lastname", "Khovrin 2");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(postMap, httpHeaders);

        JsonNode jsonNode1 = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);

        System.out.println(jsonNode1.toString());
    }

    @Test
    public void deleteCustomer(){
        String apiUrl = API_ROOT +"/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Oleg");
        postMap.put("lastname", "Khovrin");


        JsonNode jsonNode = restTemplate.postForObject(apiUrl,postMap,JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String id = jsonNode.get("customer_url").textValue().split("/")[3];
        System.out.println("Created customer id: " + id);

        restTemplate.delete(apiUrl+id);
        System.out.println("Customer deleted");

        //should crashed with 404
        restTemplate.getForObject(apiUrl+id,JsonNode.class);
    }
}
