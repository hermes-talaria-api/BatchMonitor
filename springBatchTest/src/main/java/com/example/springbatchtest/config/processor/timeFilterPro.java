package com.example.springbatchtest.config.processor;

import com.example.springbatchtest.dto.LogDto;
import lombok.extern.slf4j.Slf4j;

import javax.batch.api.chunk.ItemProcessor;
import java.util.Map;


@Slf4j
public class timeFilterPro implements ItemProcessor{
    @Override
    public Object processItem(Object item) throws Exception {
        LogDto logItem = (LogDto)item;
        log.info("logItem : "+logItem);
        
        return null;
    }
}
