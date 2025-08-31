package com.example.busreservation.controller;

import com.example.busreservation.models.AuthResponseModel;
import com.example.busreservation.models.ResponseModel;
import com.example.busreservation.repos.UserRepository;
import com.example.busreservation.entities.User;
// import com.example.busreservation.models.AuthResponseModel;
// import com.example.busreservation.security.JwtTokenProvider;
import com.example.busreservation.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Value("${app.jwtExpirationInMs}")
    private Long expiration;

//    @Autowired
//     AuthenticationManager authenticationManager;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;

   @Autowired
   private UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponseModel> login(@RequestBody User user){
//        final AuthResponseModel authResponseModel;
//        final Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                user.getName(),user.getPassword()
//        ));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token =jwtTokenProvider.generateToken(authentication);
//        authResponseModel =new AuthResponseModel(
//                HttpStatus.OK.value(),
//                "Succesfully logged in",
//                token,
//                System.currentTimeMillis(),
//                expiration
//        );
//        return ResponseEntity.ok(authResponseModel);

//    }

   @PostMapping("/register")
    public ResponseEntity<ResponseModel<User>> register(@RequestBody User request) {

        User user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel<>(HttpStatus.CREATED.value(), "User registered successfully", user)
        );
    }
   

}
