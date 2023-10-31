package com.example.springbatchtest.mapper;


import com.example.springbatchtest.dto.FindTwoNumsDto;
import com.example.springbatchtest.dto.LogDto;
import com.example.springbatchtest.service.FindTwoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
public class LogMapper implements FieldSetMapper<LogDto>{

    private final FindTwoService findTwoService;
    @Override
    public LogDto mapFieldSet(FieldSet fieldSet) throws BindException {
        String date = fieldSet.readString(3).replace("[","") +" "+fieldSet.readString(4).replace("]","");
        LogDto logDto = new LogDto();

        logDto.setIp(fieldSet.readString(0));
        logDto.setDate(date);
        String[] str = fieldSet.readString(5).split("/");
        logDto.setMethod(str[0].trim());
        logDto.setProtocol(str[1]);
        logDto.setStatus(fieldSet.readString(6));
        logDto.setUrl(fieldSet.readString(8));
        log.info("logDto : "+logDto.toString());
        return logDto;
    }
}
