package com.e_Commerce.E_CommerceApp.service.user;

import com.e_Commerce.E_CommerceApp.dto.UserDto;
import com.e_Commerce.E_CommerceApp.model.User;
import com.e_Commerce.E_CommerceApp.request.CreateUserRequest;
import com.e_Commerce.E_CommerceApp.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long id);
    UserDto createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);


}
