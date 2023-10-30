package com.example.springbatchtest.parser;

import com.example.springbatchtest.dto.LogDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    public List<LogDto> parseLog(String filePath) throws IOException {
        List<LogDto> logList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogDto logDTO = parseLogEntry(line);
                if (logDTO != null) {
                    logList.add(logDTO);
                }
            }
        }
        return logList;
    }

    private LogDto parseLogEntry(String logEntry) {
        // 이 메서드에서 로그 항목을 파싱하여 LogDTO 객체로 변환합니다.
        String regex = "^(\\S+) - - \\[([^\\]]+)\\] \"(\\S+) (\\S+)\\s+HTTP/\\d\\.\\d\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\"$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(logEntry);
        String ip = "초기 ip";
        String date = "초기 날짜";
        String method = "초기 메소드";
        String url = "초기 url";
        String status = "초기 상태";
        if (matcher.find()) {
            ip = matcher.group(1);
            date = matcher.group(2);
            method = matcher.group(3);
            url = matcher.group(4);
            status = matcher.group(5);
        }
        // 필드 추출 및 설정
        return new LogDto(ip, date, method, url, status);
    }
}
