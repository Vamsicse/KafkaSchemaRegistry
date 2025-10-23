package com.thrivent.tax.lettermanager.controller;

import com.thrivent.tax.lettermanager.model.TinCertificationStatus;
import com.thrivent.tax.lettermanager.service.TinCertificationStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/event")
@RequiredArgsConstructor
@RestController
@Slf4j
public class TinCertificationStatusController {

    private final TinCertificationStatusService tinCertificationStatusService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createTinCertificationStatus(@RequestBody TinCertificationStatus tinCertificationStatus) throws Exception {
        log.info("Received request to create TinCertificationStatus {}", tinCertificationStatus);
        tinCertificationStatusService.createTinCertificationStatus(tinCertificationStatus);
        return "Message posted to Kafka";
    }

}
