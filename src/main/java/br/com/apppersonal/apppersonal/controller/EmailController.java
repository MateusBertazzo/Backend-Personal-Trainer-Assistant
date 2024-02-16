package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.Dto.EmailRequestDto;
import br.com.apppersonal.apppersonal.model.Dto.ResetPasswordForgotDto;
import br.com.apppersonal.apppersonal.service.UserService;
import br.com.apppersonal.apppersonal.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final UserService userService;

    private final ApiResponse apiResponse;

    @Autowired
    public EmailController(UserService userService, ApiResponse apiResponse) {
        this.userService = userService;
        this.apiResponse = apiResponse;
    }
    /**
     * Método para enviar email de recuperação de senha
     *
     * @param   EmailRequestDto emailRequest
     * @return  ResponseEntity
     */
    @PostMapping("/send-email")
    public ResponseEntity<ApiResponse> resetPasswordForgotRequest(@RequestBody EmailRequestDto emailRequest) {
       return apiResponse.request(userService.sendEmailForgotPasswordRequest(emailRequest.getTo()));
    }

    /**
     * Método para resetar a senha do usuário
     *
     * @param   ResetPasswordForgotDto resetPasswordForgotDto
     * @return  ResponseEntity
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPasswordForgot(@RequestBody ResetPasswordForgotDto resetPasswordForgotDto) {
       return apiResponse.request(userService.resetPasswordForgot(resetPasswordForgotDto));
    }
}
