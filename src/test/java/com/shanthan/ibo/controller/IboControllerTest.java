package com.shanthan.ibo.controller;

import com.shanthan.ibo.domain.Address;
import com.shanthan.ibo.domain.State;
import com.shanthan.ibo.domain.StatusResponse;
import com.shanthan.ibo.exception.IboException;
import com.shanthan.ibo.repository.Ibo;
import com.shanthan.ibo.service.IboService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.*;

class IboControllerTest {

    @InjectMocks
    private IboController subject;

    @Mock
    private IboService iboService;

    private Ibo ibo;

    @BeforeEach
    void setUp() {
        openMocks(this);
        Address address = Address.builder()
                .addressLine1("someAddressLine1")
                .addressLine2("someAddressLine2")
                .city("someCity")
                .state(State.ILLINOIS.getDescription())
                .zipCode("60608")
                .build();
        ibo = new Ibo();
        ibo.setAddress(address);
        ibo.setIboNumber(1);
        ibo.setDateOfBirth(LocalDate.of(1995, 1, 1));
        ibo.setFirstName("someFirstName");
        ibo.setLastName("someLastName");
    }

    @Test
    void givenIboRequest_whenRequestValid_returnsSuccessResponse() throws IboException {
        doNothing().when(iboService).addNewIbo(ibo);
        ResponseEntity<StatusResponse> resultResponse = subject.createNewIbo(ibo);
        StatusResponse resultBody = resultResponse.getBody();
        assertNotNull(resultBody);
        assertEquals(CREATED, resultResponse.getStatusCode());
        assertEquals(ibo.getIboNumber(),resultBody.getIboNumber());
        assertEquals("Created", resultBody.getStatus());
    }

    @Test
    void givenIboNumber_whenIboPresent_returnsIboDetails() throws IboException {
        when(iboService.getIbo(1L)).thenReturn(ibo);
        ResponseEntity<Ibo> resultResponse = subject.getIboDetails(1L);
        assertNotNull(resultResponse);
        Ibo resultBody = resultResponse.getBody();
        assertNotNull(resultBody);
        assertEquals(OK, resultResponse.getStatusCode());
        assertEquals(ibo.getFirstName(), resultBody.getFirstName());
    }

    @Test
    void updateIbo() {
        Address address = Address.builder()
                .addressLine1("someAddressLine1")
                .addressLine2("someAddressLine2")
                .city("someCity")
                .state(State.ILLINOIS.getDescription())
                .zipCode("60608")
                .build();
        Ibo updatedIbo = new Ibo();
        updatedIbo.setAddress(address);
        updatedIbo.setIboNumber(1);
        updatedIbo.setDateOfBirth(LocalDate.of(1995, 1, 1));
        updatedIbo.setFirstName("someFirstName");
        updatedIbo.setLastName("updatedLastName");

        when(iboService.updateIbo(updatedIbo, 1L)).thenReturn(updatedIbo);

        ResponseEntity<Ibo> resultResponse = subject.updateIbo(updatedIbo, 1L);
        assertNotNull(resultResponse);
        Ibo resultBody = resultResponse.getBody();
        assertNotNull(resultBody);
        assertEquals(ACCEPTED, resultResponse.getStatusCode());
        assertEquals(updatedIbo.getLastName(), resultBody.getLastName());

    }
}