package com.example.busreservation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.busreservation.entities.User;
import com.example.busreservation.models.CustomUserDetails;
import com.example.busreservation.repos.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // For secure passwords

    // Admin credentials from application.properties
    @Value("${app.admin.username}")
    private String ADMIN_USERNAME;
    @Value("${app.admin.password}")
    private String ADMIN_PASSWORD; 
    @Value("${app.admin.mail}")
    private String Admin_Mail;      
    @Value("${app.admin.phone}")
    private String Admin_Phone; 

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userEntity = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role;

        // ✅ If admin credentials, give ROLE_ADMIN
        if (username.equals(ADMIN_USERNAME)) {
            role = "ADMIN";
        } else {
            // ✅ Everyone else gets ROLE_USER
            role = "USER";
        }

        return new CustomUserDetails(userEntity);
    }
    
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @PostConstruct
    public void createAdminIfNotExists() {
        Optional<User> existingAdmin = userRepository.findByName(ADMIN_USERNAME);

        if (existingAdmin.isEmpty()) {
            User admin = new User();
            admin.setName(ADMIN_USERNAME);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setEmail(Admin_Mail);
            admin.setPhone(Admin_Phone);
            userRepository.save(admin);
        
        }
    }
}
