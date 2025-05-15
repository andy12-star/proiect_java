package com.andy.proiect_facultate.service.api;

import com.andy.proiect_facultate.model.dto.request.RegisterRequest;
import com.andy.proiect_facultate.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);

    Page<User> getUsersPage(Pageable pageable);
}
