package com.example.springbatchtest.config.reader;

import com.example.springbatchtest.dto.LogDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;

public class CustomRecordReader<T> implements ItemReader<T>{

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
}
