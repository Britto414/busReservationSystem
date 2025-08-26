package com.example.busreservation.controller;

import com.example.busreservation.models.AuthResponseModel;
import com.example.busreservation.models.ResponseModel;
import com.example.busreservation.repos.AppUserRepository;
import com.example.busreservation.entities.AppUser;
// import com.example.busreservation.models.AuthResponseModel;
import com.example.busreservation.security.JwtTokenProvider;
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
@RequestMapping("/api/auth")
public class AuthController {
    @Value("${app.jwtExpirationInMs}")
    private Long expiration;

   @Autowired
    AuthenticationManager authenticationManager;

   @Autowired
   private JwtTokenProvider jwtTokenProvider;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private AppUserRepository appUserRepository;

   @PostMapping("/login")
   public ResponseEntity<AuthResponseModel> login(@RequestBody AppUser user){
       final AuthResponseModel authResponseModel;
       final Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               user.getUserName(),user.getPassword()
       ));
       SecurityContextHolder.getContext().setAuthentication(authentication);
       String token =jwtTokenProvider.generateToken(authentication);
       authResponseModel =new AuthResponseModel(
               HttpStatus.OK.value(),
               "Succesfully logged in",
               token,
               System.currentTimeMillis(),
               expiration
       );
       return ResponseEntity.ok(authResponseModel);

   }

   @PostMapping("/register")
public ResponseEntity<ResponseModel<AppUser>> register(@RequestBody AppUser request) {

    if (appUserRepository.findByUserName(request.getUserName()).isPresent()) {
        ResponseModel<AppUser> existResponse = new ResponseModel<>(
                HttpStatus.BAD_REQUEST.value(),
                "Username already exists!",
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existResponse);
    }

    // Create and save new user
    AppUser user = AppUser.builder()
            .userName(request.getUserName())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

    appUserRepository.save(user);

    ResponseModel<AppUser> successResponse = new ResponseModel<>(
            HttpStatus.CREATED.value(),
            "User registered successfully!",
            user
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
   

}
