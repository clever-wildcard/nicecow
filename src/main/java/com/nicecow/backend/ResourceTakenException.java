package com.nicecow.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceTakenException extends RuntimeException {
    public ResourceTakenException(String message) {super(message);}
}
