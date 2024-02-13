package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.EmailRequestDto;
import br.com.apppersonal.apppersonal.model.Dto.ResetPasswordForgotDto;
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

    @PostMapping("/send-email")
    public void resetPasswordForgotRequest(@RequestBody EmailRequestDto emailRequest) {
        apiResponse.request(userService.sendEmailForgotPasswordRequest(emailRequest.getTo()));
    }

    @PostMapping("/reset-password")
    public void resetPasswordForgot(@RequestBody ResetPasswordForgotDto resetPasswordForgotDto) {
        apiResponse.request(userService.resetPasswordForgot(resetPasswordForgotDto));
    }
}
