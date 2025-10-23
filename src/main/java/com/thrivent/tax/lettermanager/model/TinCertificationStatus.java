package com.thrivent.tax.lettermanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(    ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TinCertificationStatus {

    private Long taxClientId;

    private String docTypeCode;

    private String statusCode;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Chicago")
    private Date createdDate;

    private String operatorIdCode;
}