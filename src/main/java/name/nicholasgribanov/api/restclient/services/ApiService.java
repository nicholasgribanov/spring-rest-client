package name.nicholasgribanov.api.restclient.services;

import name.nicholasgribanov.api.domain.User;

import java.util.List;

public interface ApiService {
    List<User> getUsers(Integer limit);
}
