package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.EmailRequestDto;
import br.com.apppersonal.apppersonal.service.EmailService;
import br.com.apppersonal.apppersonal.service.UserService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final UserService userService;

    private final ApiResponse apiResponse;

    public EmailController(UserService userService, ApiResponse apiResponse) {
        this.userService = userService;
        this.apiResponse = apiResponse;
    }

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailRequestDto emailRequest) {
        apiResponse.request(userService.resetPasswordForgotRequest(emailRequest.getTo()));
    }
}
