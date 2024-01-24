package br.com.apppersonal.apppersonal.utils;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Data
@Service
public class ApiResponse {
    private boolean success;
    private String message;
    private Object response;

    @JsonIgnore
    private final ModelMapper modelMapper = new ModelMapper();

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

    @Transactional
    public ResponseEntity<ApiResponse> request(ResponseEntity<?> responseEntity) {
        var body = responseEntity.getBody();
        ApiResponse domain = modelMapper.map(body, ApiResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    domain.getMessage(),
                                    domain.getResponse()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    domain.getMessage(),
                                    domain.getResponse()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    domain.getMessage()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    "Erro na requisição: " + domain.getMessage()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    "Não autorizado: " + domain.getMessage()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.FORBIDDEN) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    "Acesso proibido: " + domain.getMessage()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    "Recurso não encontrado: " + domain.getMessage()
                            )
                    );
        }

        if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    domain.isSuccess(),
                                    "Erro interno do servidor: " + domain.getMessage()
                            )
                    );
        }

        return ResponseEntity.status(responseEntity.getStatusCode()).body(
                new ApiResponse(
                        false,
                        "Erro ao realizar requisição (default)"
                )
        );
    }

}
