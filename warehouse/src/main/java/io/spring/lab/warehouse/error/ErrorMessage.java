package io.spring.lab.warehouse.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Value;

@Value
public class ErrorMessage {

	private String message;

	public static ErrorMessage messageOf(String message) {
		return new ErrorMessage(message);
	}

	public static ErrorMessage messageOf(Exception e) {
		return messageOf(e.getMessage());
	}

	public static ResponseEntity<ErrorMessage> messageResponseOf(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(messageOf(message));
	}

	public static ResponseEntity<ErrorMessage> messageResponseOf(HttpStatus status, Exception e) {
		return messageResponseOf(status, e.getMessage());
	}
}
