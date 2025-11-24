package com.e_Commerce.E_CommerceApp.controller;

import com.e_Commerce.E_CommerceApp.dto.UserDto;
import com.e_Commerce.E_CommerceApp.model.User;
import com.e_Commerce.E_CommerceApp.request.CreateUserRequest;
import com.e_Commerce.E_CommerceApp.request.UserUpdateRequest;
import com.e_Commerce.E_CommerceApp.response.ApiResponse;
import com.e_Commerce.E_CommerceApp.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;



    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable long userId){
        User user = userService.getUserById(userId);
        UserDto userDto =userService.convertUserToDto(user);
        return ResponseEntity.ok(new ApiResponse(" User Fetched Successfully", userDto));

    }
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        UserDto userDto = userService.createUser(request);
        return ResponseEntity.ok(new ApiResponse("User Created Successfully", userDto));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserUpdateRequest request, @PathVariable long userId){
        User user = userService.updateUser(request, userId);
        UserDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.ok(new ApiResponse("User Updated Successfully", userDto));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser( @PathVariable long uerId){
         userService.deleteUser( uerId);
        return ResponseEntity.ok(new ApiResponse(" User Updated Successfully", null));

    }

}
