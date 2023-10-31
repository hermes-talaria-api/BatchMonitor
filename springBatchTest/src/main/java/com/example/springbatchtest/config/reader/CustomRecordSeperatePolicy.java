package com.example.springbatchtest.config.reader;

import com.example.springbatchtest.service.FindTwoService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

import java.text.ParseException;


@AllArgsConstructor
@Slf4j
public class CustomRecordSeperatePolicy implements RecordSeparatorPolicy {

    FindTwoService findTwoService;
    @Override
    public boolean isEndOfRecord(String record) {
        try {

            log.info("record : "+record);
            String[] arr = record.split(" ");
            String date = (arr[3]+" "+arr[4]).replace("[","").replace("]","");
            log.info("isEndOfRecord : "+ date);
            Long mill = findTwoService.getTime(date);
            if(mill + 500000 > System.currentTimeMillis()){
                log.info("false");
                return true;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        log.info("true");
        return false;
    }

    @Override
    public String postProcess(String record) {
        return null;
    }

    @Override
    public String preProcess(String record) {
        return null;
    }


}
