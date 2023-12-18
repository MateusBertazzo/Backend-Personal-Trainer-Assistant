package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.UserDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController  {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserEntity userEntity) {
         userService.createUser(userEntity);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }
}
