package com.nike.microservices.exercise.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeckNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeckNotFoundException(String deckName) {
        super("No such deck named: " + deckName);
    }
}
