package it.progetto.catalogo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class ElementAlreadyPresentException extends Exception {

	public ElementAlreadyPresentException(String message) {
		super(message);
	}

	
}
