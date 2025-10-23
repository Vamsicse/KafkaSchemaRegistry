package com.thrivent.tax.lettermanager.util;

import com.thrivent.tax.lettermanager.model.TinCertificationStatus;
import com.thrivent.tax.lettermanager.model.TinCertificationStatusAVRO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConverterUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    static {
        sdf.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
    }

    public static TinCertificationStatusAVRO toTinCertificationStatusAVRO(TinCertificationStatus source) {
        if (source == null) {
            return null;
        }
        TinCertificationStatusAVRO avro = new TinCertificationStatusAVRO();
        avro.setTaxClientId(source.getTaxClientId());
        avro.setDocTypeCode(source.getDocTypeCode());
        avro.setStatusCode(source.getStatusCode());
        if (source.getCreatedDate() != null) {
            avro.setCreatedDate(sdf.format(source.getCreatedDate()));
        } else {
            avro.setCreatedDate(null);
        }
        avro.setOperatorIdCode(source.getOperatorIdCode());
        return avro;
    }

    public static TinCertificationStatus toTinCertificationStatus(TinCertificationStatusAVRO avro) {
        if (avro == null) {
            return null;
        }
        TinCertificationStatus pojo = new TinCertificationStatus();
        pojo.setTaxClientId(avro.getTaxClientId());
        pojo.setDocTypeCode(charSequenceToString(avro.getDocTypeCode()));
        pojo.setStatusCode(charSequenceToString(avro.getStatusCode()));
        if (avro.getCreatedDate() != null) {
            try {
                Date date = sdf.parse(avro.getCreatedDate().toString());
                pojo.setCreatedDate(date);
            } catch (ParseException e) {
                throw new RuntimeException("Failed to parse createdDate: " + avro.getCreatedDate(), e);
            }
        } else {
            pojo.setCreatedDate(null);
        }
        pojo.setOperatorIdCode(charSequenceToString(avro.getOperatorIdCode()));
        return pojo;
    }

    private static String charSequenceToString(CharSequence cs) {
        return cs == null ? null : cs.toString();
    }
}
