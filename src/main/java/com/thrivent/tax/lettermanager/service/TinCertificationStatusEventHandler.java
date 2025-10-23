package com.thrivent.tax.lettermanager.service;

import com.thrivent.tax.lettermanager.model.TinCertificationStatus;
import com.thrivent.tax.lettermanager.model.TinCertificationStatusAVRO;
import com.thrivent.tax.lettermanager.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class TinCertificationStatusEventHandler {

    private final KafkaTemplate<String, TinCertificationStatusAVRO> kafkaTemplate;
    private final LetterManagerService letterManagerService;

    @Value("${kafka.topic}")
    private String topic;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(TinCertificationStatusAVRO message) {
        log.info("Received TinCertificationStatusAVRO message: {}", message);
        TinCertificationStatus tinCertificationStatus = ConverterUtil.toTinCertificationStatus(message);
        letterManagerService.sendUpdateToCorrespondenceAPI(tinCertificationStatus);
    }

    public SendResult<String, TinCertificationStatusAVRO> send(TinCertificationStatusAVRO message) {
        log.info("Sending TinCertificationStatusAVRO message: {}", message);
        SendResult<String, TinCertificationStatusAVRO> result = kafkaTemplate.send(topic, message).join();
        log.info("Message sent successfully to topic-partition {}-{} with offset {}",
                result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
        return result;
    }

}
