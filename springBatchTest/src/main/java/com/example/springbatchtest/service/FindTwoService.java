package com.example.springbatchtest.service;


import com.example.springbatchtest.dto.FindTwoNumsDto;
import com.example.springbatchtest.dto.LogDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Component
public class FindTwoService {

   public Long getTime(String time) throws ParseException {

       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH.mm.ss Z", Locale.US);
           Date date = dateFormat.parse(time);
         return date.getTime();
   }
}
