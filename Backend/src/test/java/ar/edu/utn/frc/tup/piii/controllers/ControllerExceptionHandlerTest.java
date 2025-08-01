package ar.edu.utn.frc.tup.piii.controllers;


import ar.edu.utn.frc.tup.piii.dtos.common.ErrorApi;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;


import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Mock
    private BindingResult bindingResult;

    @Test
    void testGenericException() {
        Exception ex = new Exception("Error genérico");

        ResponseEntity<ErrorApi> response = handler.handleError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error genérico", response.getBody().getMessage());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    void testEntityNotFoundException() {
        EntityNotFoundException ex = new EntityNotFoundException("Usuario no encontrado");

        ResponseEntity<ErrorApi> response = handler.handleError(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
    }

    @Test
    void testResponseStatusException() {
        ResponseStatusException ex = new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Datos inválidos"
        );

        ResponseEntity<ErrorApi> response = handler.handleError(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Datos inválidos", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
    }


    @Test
    void testEntityNotFoundExceptionDetailed() {
        // Test adicional más detallado para 404
        EntityNotFoundException ex = new EntityNotFoundException("El producto con ID 123 no fue encontrado");

        ResponseEntity<ErrorApi> response = handler.handleError(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El producto con ID 123 no fue encontrado", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHttpClientErrorExceptionConflict() {
        HttpClientErrorException.Conflict ex = (HttpClientErrorException.Conflict) HttpClientErrorException.Conflict.create(
                HttpStatus.CONFLICT,
                "Conflict",
                null,
                null,
                null
        );

        ResponseEntity<ErrorApi> response = handler.handleError(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(409, response.getBody().getStatus());
        assertEquals("Conflict", response.getBody().getError());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testMethodArgumentNotValidException() throws Exception {
        Object target = new Object();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "testObject");
        bindingResult.addError(new FieldError("testObject", "campo", "Error de validación"));

        Method method = this.getClass().getMethod("dummyMethod", String.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<ErrorApi> response = handler.handleError(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
    }

    // Método dummy para crear el MethodParameter
    public void dummyMethod(String param) {

    }
}