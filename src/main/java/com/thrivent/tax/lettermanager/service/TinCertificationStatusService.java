package com.thrivent.tax.lettermanager.service;

import com.thrivent.tax.lettermanager.model.TinCertificationStatus;
import com.thrivent.tax.lettermanager.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class TinCertificationStatusService {

    private final TinCertificationStatusEventHandler tinCertificationStatusEventHandler;

    public void createTinCertificationStatus(TinCertificationStatus tinCertificationStatus) throws Exception {
        tinCertificationStatusEventHandler.send(ConverterUtil.toTinCertificationStatusAVRO(tinCertificationStatus));
    }
}
