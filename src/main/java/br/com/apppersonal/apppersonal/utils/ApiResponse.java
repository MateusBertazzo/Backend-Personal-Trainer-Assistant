package br.com.apppersonal.apppersonal.utils;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Data
@Service
public class ApiResponse {
    private boolean success;
    private String message;
    private Object response;

    public ApiResponse() {
    }
    public ApiResponse(boolean success, String message, Object response) {
        this.success = success;
        this.message = message;
        this.response = response;
    }
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseEntity<ApiResponse> request(ResponseEntity<?> responseEntity) {
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Requisição realizada com sucesso",
                                    responseEntity.getBody()
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Requisição realizada com sucesso",
                                    responseEntity.getBody()
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(
                            new ApiResponse(
                                    true,
                                    "Requisição realizada com sucesso"
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ApiResponse(
                                    false,
                                    "Erro na requisição: "
                                            + responseEntity.getBody()
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ApiResponse(
                                    false,
                                    "Não autorizado: "
                                            + responseEntity.getBody()
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.FORBIDDEN) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(
                            new ApiResponse(
                                    false,
                                    "Acesso proibido: "
                                            + responseEntity.getBody()
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    false,
                                    "Recurso não encontrado: "
                                            + responseEntity.getBody()
                            )
                    );
        }
        if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                            false,
                            "Erro interno do servidor: "
                                    + responseEntity.getBody()
                            )
                    );
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(
                new ApiResponse(
                        false,
                        "Erro ao realizar requisição"
                )
        );
    }

}
