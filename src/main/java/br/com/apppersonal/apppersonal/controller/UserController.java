package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.service.TokenService;
import br.com.apppersonal.apppersonal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController  {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          TokenService tokenService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserEntity userEntity) {
         userService.createUser(userEntity);
    }

//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public UserEntity loginUser(@RequestBody UserDto userDto) {
//        return userService.loginUser(userDto);
//    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                            userDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = tokenService.generateToken(userDetails);

            return ResponseEntity.status(HttpStatus.OK).body(token);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
