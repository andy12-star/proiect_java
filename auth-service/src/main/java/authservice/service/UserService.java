package authservice.service;

import authservice.dto.request.RegisterRequest;
import authservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User registerUser(RegisterRequest request);

    Page<User> getUsersPage(Pageable pageable);
}