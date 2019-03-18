package name.nicholasgribanov.api.restclient.controllers;

import lombok.extern.slf4j.Slf4j;
import name.nicholasgribanov.api.restclient.services.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class UserController {

    private ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {

        Mono<MultiValueMap<String, String>> map = serverWebExchange.getFormData();

        Mono<Integer> limit1 = map.map(m -> Integer.valueOf(m.get("limit").get(0)));
        log.info("@@@@" + map.hasElement());
        final Integer[] limit = new Integer[1];
        limit1.subscribe(
                value -> limit[0] = value
        );

        log.debug("Received Limit value: " + limit[0]);
        //default if null or zero
        if (limit[0] == null || limit[0] == 0) {
            log.debug("Setting limit to default of 10");
            limit[0] = 10;
        }

        model.addAttribute("users", apiService.getUsers(limit[0]));

        return "userlist";
    }
}
