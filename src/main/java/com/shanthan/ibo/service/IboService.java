package com.shanthan.ibo.service;

import com.shanthan.ibo.exception.IboException;
import com.shanthan.ibo.repository.Ibo;
import com.shanthan.ibo.repository.IboRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class IboService {

    private final IboRepository iboRepository;

    public IboService(IboRepository iboRepository) {
        this.iboRepository = iboRepository;
    }

    public void addNewIbo(Ibo ibo) throws IboException {
        log.info("Saving Ibo with iboNumber -> {}", ibo.getIboNumber());
        try {
            iboRepository.save(ibo);
        } catch (Exception ex) {
            log.error("Unable to save new Ibo. Reason -> {} ", ex.getMessage(), ex);
            throw new IboException("Unable to save new Ibo ", INTERNAL_SERVER_ERROR);
        }

    }

    public Ibo getIbo(Long iboNumber) throws IboException {
        log.info("Retrieving Ibo with Ibo number -> {} from database ", iboNumber);
        return iboRepository.findById(iboNumber)
                .orElseThrow(() -> new IboException("Cannot find ibo with Ibo number ", NOT_FOUND));
    }

    public Ibo updateIbo(Ibo ibo, Long iboNumber) {
        log.info("Updating Ibo with Ibo number -> {} ", iboNumber);
        return iboRepository.findById(iboNumber)
                .map(updateIbo -> {
                    updateIbo.setFirstName(ibo.getFirstName());
                    updateIbo.setLastName(ibo.getLastName());
                    updateIbo.setDateOfBirth(ibo.getDateOfBirth());
                    updateIbo.setAddress(ibo.getAddress());
                    return iboRepository.save(updateIbo);
                }).orElseGet(() -> {
                    ibo.setIboNumber(iboNumber);
                    return iboRepository.save(ibo);
                });
    }
}
