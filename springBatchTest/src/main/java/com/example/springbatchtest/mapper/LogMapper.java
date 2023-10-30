package com.example.springbatchtest.mapper;


import com.example.springbatchtest.dto.LogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.Arrays;

@Slf4j
public class LogMapper implements FieldSetMapper<LogDto>{

    @Override
    public LogDto mapFieldSet(FieldSet fieldSet) throws BindException {
        if(fieldSet == null){
            return null;
        }
//        log.info("names : "+ Arrays.toString(fieldSet.getNames()));
//        log.info("values: " + Arrays.toString(fieldSet.getValues()));


        LogDto logDto = new LogDto();

        logDto.setIp(fieldSet.readString(0));
        logDto.setDate(fieldSet.readString(3).replace("[",""));
        String[] str = fieldSet.readString(5).split("/");
        logDto.setMethod(str[0].trim());
        logDto.setProtocol(str[1]);
        logDto.setStatus(fieldSet.readString(6));
        logDto.setUrl(fieldSet.readString(8));
//        log.info("logDto : "+logDto.toString());
        return logDto;
    }
}
