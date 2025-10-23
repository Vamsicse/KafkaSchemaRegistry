package com.thrivent.tax.lettermanager.service;

import com.thrivent.tax.lettermanager.model.TinCertificationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class LetterManagerService {

    public void sendUpdateToCorrespondenceAPI(TinCertificationStatus tinCertificationStatus) {
        log.info("Sending update to Correspondence-API for taxClientId: {}", tinCertificationStatus.getTaxClientId());
        // TODO
        log.info("Sent update to Correspondence API. Response {}", tinCertificationStatus);
    }

}
