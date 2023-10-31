package com.example.springbatchtest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MonitorDto {
    Date date;
    Integer time;

    public MonitorDto(Date date, int time){
        this.date = date;
        this.time = time;
    }

}
