package rm.tempserver;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<JsonErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        return badRequest(e.getMessage());
    }

    private static ResponseEntity<JsonErrorMessage> badRequest(String message) {
        log.error("Bad request: {}", message);
        return createJsonErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    public static ResponseEntity<JsonErrorMessage> createJsonErrorResponse(
            HttpStatus status, String message) {
        return createJsonErrorResponse(status, new JsonErrorMessage(message));
    }

    private static <T> ResponseEntity<T> createJsonErrorResponse(HttpStatus status, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(body, headers, status);
    }
    @Data
    @JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class JsonErrorMessage {
        private final String message;
    }
}
