package com.tin.userservice.service.impl;

import com.tin.userservice.dto.CreateUserRequestDTO;
import com.tin.userservice.dto.UserResponseDTO;
import com.tin.userservice.entity.User;
import com.tin.userservice.repository.UserRepository;

import com.tin.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setName(dto.getName());

        user = userRepository.save(user);
        return toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, CreateUserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setName(dto.getName());

        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob())
                .name(user.getName())
                .id(user.getId())
                .build();
    }
}
