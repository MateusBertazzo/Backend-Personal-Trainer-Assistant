package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.ResetPasswordDto;
import br.com.apppersonal.apppersonal.model.Dto.UserCreateDto;
import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.service.TokenService;
import br.com.apppersonal.apppersonal.service.UserService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final ApiResponse apiResponse;

    @Autowired
    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          TokenService tokenService,
                          ApiResponse apiResponse) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserCreateDto userCreateDto) {
        return apiResponse.request(userService.createUser(userCreateDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(),
                            userDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            var principal =  (UserEntity) authentication.getPrincipal();

            if (principal.getDeleted()) throw new Exception("Usuário Deletado");

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, Object> additionalData = new HashMap<>();
            additionalData.put("username", userDetails.getUsername());
            additionalData.put("email", principal.getEmail());
            additionalData.put("role", principal.getRole().ordinal());
            additionalData.put("userId", principal.getId());

            String token = tokenService.generateToken(userDetails, additionalData);

            return apiResponse.request(
                    ResponseEntity
                            .status(HttpStatus.OK)
                            .body(
                                    new ApiResponse(
                                            true,
                                            "Usuário logado com sucesso!",
                                            token
                                    )
                            )
            );

        } catch (Exception e) {
            return apiResponse.request(
                    ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(
                                    new ApiResponse(
                                            false,
                                            e.getMessage()
                                    )
                            )
            );
        }
    }

    @DeleteMapping("/delete-user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        return apiResponse.request(userService.deleteUser(id));
    }

    @PostMapping("/forgot-password/{id}")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto,
            @PathVariable Long id
    )
    {
        return apiResponse.request(userService.resetPassword(resetPasswordDto, id));
    }
}
