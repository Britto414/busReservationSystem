package com.example.busreservation.models;

import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserPrincipal {
    private final UserDetails userDetails;
    private final Long customerId;

    public CustomUserPrincipal(UserDetails userDetails, Long customerId) {
        this.userDetails = userDetails;
        this.customerId = customerId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
