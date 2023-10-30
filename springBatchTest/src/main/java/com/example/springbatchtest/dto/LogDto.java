package com.example.springbatchtest.dto;

import lombok.Data;

@Data
public class LogDto {
    String ip;
    String date;
    String method;
    String status;
    String protocol;
    String url;

    public LogDto(String ip, String date, String method, String url, String status) {
        this.ip = ip;
        this.date = date;
        this.method = method;
        this.url = url;
        this.status = status;
    }
}
