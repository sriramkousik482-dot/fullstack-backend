package com.fwms.service;

import com.fwms.dto.UserDto;
import com.fwms.model.User;
import com.fwms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto.Response> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }

    public List<UserDto.Response> searchUsers(String search) {
        if (search == null || search.trim().isEmpty()) {
            return getAllUsers();
        }
        
        String searchTerm = search.toLowerCase();
        return userRepository.findAll().stream()
                .filter(user -> 
                    user.getName().toLowerCase().contains(searchTerm) ||
                    user.getEmail().toLowerCase().contains(searchTerm)
                )
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Transactional
    public UserDto.Response updateUser(Long id, UserDto.UpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhoneNumber(request.getPhone());
        }
        if (request.getOrganizationName() != null) {
            user.setOrganizationName(request.getOrganizationName());
        }
        
        user = userRepository.save(user);
        return mapToResponse(user);
    }

    private UserDto.Response mapToResponse(User user) {
        return new UserDto.Response(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getPhoneNumber(),
                user.getOrganizationName(),
                user.getCreatedAt()
        );
    }
}
