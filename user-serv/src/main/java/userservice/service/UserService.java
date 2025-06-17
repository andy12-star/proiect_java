package userservice.service;


import userservice.model.RegisterRequest;
import userservice.model.User;

public interface UserService {
    User registerUser(RegisterRequest request);
}

