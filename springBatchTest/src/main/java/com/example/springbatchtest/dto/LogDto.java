package com.example.springbatchtest.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogDto {
    String ip;
    String date;
    String method;
    String Status;
    String protocol;
    String url;

    @Override
    public String toString() {
        return "LogDto{" +
                "ip='" + ip + '\'' +
                ", date='" + date + '\'' +
                ", method='" + method + '\'' +
                ", Status='" + Status + '\'' +
                ", protocol='" + protocol + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
