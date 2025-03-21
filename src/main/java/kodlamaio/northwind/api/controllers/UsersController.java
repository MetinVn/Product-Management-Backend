package kodlamaio.northwind.api.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.northwind.api.dto.UserLoginRequest;
import kodlamaio.northwind.business.abstracts.UserService;
import kodlamaio.northwind.core.entities.User;
import kodlamaio.northwind.core.utilities.results.ErrorDataResult;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
	super();
	this.userService = userService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@Valid @RequestBody User user) {
	return ResponseEntity.ok(this.userService.add(user));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
	User loggedInUser = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

	if (loggedInUser != null) {
	    return ResponseEntity.ok(loggedInUser);
	} else {
	    Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("error", "Invalid email or password");
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
	Map<String, String> validationErrors = new HashMap<>();
	for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
	    validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
	}
	ErrorDataResult<Object> errors = new ErrorDataResult<>(validationErrors, "Dogrulama hatalari");
	return errors;
    }
}
