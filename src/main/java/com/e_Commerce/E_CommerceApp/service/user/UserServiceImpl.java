package com.e_Commerce.E_CommerceApp.service.user;

import com.e_Commerce.E_CommerceApp.dto.UserDto;
import com.e_Commerce.E_CommerceApp.errors.AlreadyExistRecord;
import com.e_Commerce.E_CommerceApp.errors.ResourceNotFound;
import com.e_Commerce.E_CommerceApp.model.User;
import com.e_Commerce.E_CommerceApp.reposiotory.UserRepository;
import com.e_Commerce.E_CommerceApp.request.CreateUserRequest;
import com.e_Commerce.E_CommerceApp.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;



    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("User Not found"));
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        User user= Optional.of(request)
                .filter(user1 -> !userRepository.existsByEmail(request.getEmail()))
                .map(userRequest ->{
                    User user1 = new User();
                    user1.setEmail(request.getEmail());
                    user1.setPassword(request.getPassword());
                    user1.setFirstName(request.getFirstName());
                    user1.setLastName(request.getLastName());
                    return userRepository.save(user1);

                }).orElseThrow(()-> new AlreadyExistRecord(request.getEmail() +"This User isAlready Exist"));

        return convertUserToDto(user);
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser-> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()->new ResourceNotFound("User Not Found"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository ::delete,
                () ->{
                    throw new ResourceNotFound("User Not Found");
                });

    }

    @Override
    public UserDto convertUserToDto(User user) {
        UserDto convertedUserDto = new UserDto();
        convertedUserDto.setId(user.getId());
        convertedUserDto.setEmail(user.getEmail());
        convertedUserDto.setFirstName(user.getFirstName());
        convertedUserDto.setLastName(user.getLastName());
        return convertedUserDto;
    }
}
