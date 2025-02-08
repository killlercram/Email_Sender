package com.emailsender.app.helper;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomeResponse {

    private String message;
    private HttpStatus httpStatus;
    private boolean success=false;

}
