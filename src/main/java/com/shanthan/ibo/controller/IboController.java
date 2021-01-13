package com.shanthan.ibo.controller;

import com.shanthan.ibo.domain.StatusResponse;
import com.shanthan.ibo.exception.IboException;
import com.shanthan.ibo.repository.Ibo;
import com.shanthan.ibo.service.IboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
public class IboController {

    private final IboService iboService;

    public IboController(IboService iboService) {
        this.iboService = iboService;
    }

    @PostMapping("/ibo")
    public ResponseEntity<StatusResponse> createNewIbo(@RequestBody Ibo ibo) throws IboException {
        iboService.addNewIbo(ibo);
        return ResponseEntity.status(CREATED)
                .contentType(APPLICATION_JSON)
                .body(StatusResponse.builder()
                              .iboNumber(ibo.getIboNumber())
                              .status(CREATED.getReasonPhrase())
                              .build());
    }

    @GetMapping("/ibo/{iboNumber}")
    public ResponseEntity<Ibo> getIboDetails(@PathVariable Long iboNumber) throws IboException {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(iboService.getIbo(iboNumber));
    }

    @PutMapping("/ibo/{iboNumber}")
    public ResponseEntity<Ibo> updateIbo(@RequestBody Ibo ibo, @PathVariable Long iboNumber) {
        return ResponseEntity.status(ACCEPTED)
                .contentType(APPLICATION_JSON)
                .body(iboService.updateIbo(ibo, iboNumber));
    }

}
