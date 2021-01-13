package com.shanthan.ibo.service;

import com.shanthan.ibo.domain.Address;
import com.shanthan.ibo.domain.State;
import com.shanthan.ibo.exception.IboException;
import com.shanthan.ibo.repository.Ibo;
import com.shanthan.ibo.repository.IboRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class IboServiceTest {

    @InjectMocks
    private IboService subject;

    @Mock
    private IboRepository iboRepository;

    private Ibo ibo;

    @BeforeEach
    void setUp() {
        openMocks(this);
        Address address = Address.builder()
                .addressLine1("someAddressLine1")
                .addressLine2("someAddressLine2")
                .city("someCity")
                .state(State.ILLINOIS)
                .zipCode("60608")
                .build();
        ibo = new Ibo();
        ibo.setAddress(address);
        ibo.setIboNumber(1);
        ibo.setDateOfBirth(LocalDate.of(1995,1,1));
        ibo.setFirstName("someFirstName");
        ibo.setLastName("someLastName");

        when(iboRepository.findById(1L)).thenReturn(Optional.of(ibo));

    }

    @Test
    void givenIboDetails_WhenDetailsAreValidAndIboDoesNotExist_thenIboIsSavedInDatabase() throws IboException {
        subject.addNewIbo(ibo);
        verify(iboRepository).save(ibo);
    }

    @Test
    void givenIboNumber_whenIboNumberValid_thenReturnIbo() throws IboException {
        Ibo result = subject.getIbo(1L);
        assertNotNull(result);
        assertEquals("someFirstName", result.getFirstName());
    }

    @Test
    void givenIboNumberAndDetails_whenIboExists_thenReturnUpdatedIbo() {
        ibo.setFirstName("updatedFirstName");
        when(iboRepository.save(any(Ibo.class))).thenReturn(ibo);
        Ibo result = subject.updateIbo(ibo, 1L);
        assertNotNull(result);
        assertEquals("updatedFirstName", result.getFirstName());
    }

    @Test
    void givenIboNumberAndDetails_whenIboDoesNotExist_thenReturnNewSavedIbo() {
        when(iboRepository.findById(1L)).thenReturn(Optional.empty());
        ibo.setFirstName("newFirstName");
        ibo.setLastName("newLastName");
        when(iboRepository.save(any(Ibo.class))).thenReturn(ibo);
        Ibo result = subject.updateIbo(ibo, 1L);
        assertNotNull(result);
        assertEquals("newFirstName", result.getFirstName());
    }
}