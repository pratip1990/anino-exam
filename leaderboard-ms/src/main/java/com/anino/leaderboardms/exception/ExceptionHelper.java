package com.anino.leaderboardms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHelper.class);

	@ExceptionHandler(value = { InvalidParameterException.class })
	public ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex) {
		LOGGER.error("Invalid Parameter Exception: ", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(value = { NoDataFoundException.class })
	public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException ex) {
		LOGGER.error("No Data Found Exception Exception: ", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<Object> handleOtherException(RuntimeException ex) {
		LOGGER.error("Something went wrong", ex.getMessage());
		return new ResponseEntity<Object>("Something went wrong", HttpStatus.BAD_REQUEST);

	}
}
