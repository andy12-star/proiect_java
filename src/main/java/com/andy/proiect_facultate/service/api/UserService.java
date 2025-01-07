package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.model.dto.request.RegisterRequest;
import com.andy.proiect_facultate.model.entity.User;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
}
