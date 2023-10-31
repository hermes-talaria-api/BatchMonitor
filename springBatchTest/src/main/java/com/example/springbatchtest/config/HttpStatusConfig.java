package com.example.springbatchtest.config;

import com.example.springbatchtest.dto.LogDto;
import com.example.springbatchtest.dto.MonitorDto;
import com.example.springbatchtest.parser.LogParser;
import com.example.springbatchtest.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpStatusConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private LogParser logParser = new LogParser();
    private final LogService logService;
    private List<LogDto> logDataList;
    private MonitorDto monitorDto;


    @Bean
    public Job HttpStatusJob(){
        System.out.println("시작");
        return jobBuilderFactory.get("httpStatus")
                .incrementer(new RunIdIncrementer())
                .start(flatFileItemReaderStep())// log 파일 불러오기 List<LogDto> 생성
                .next(checkConnectionCountStep())
                .next(sendLogFileToFrontStep()) // 프론트 단에 보내주기
                .build();
    }

    @Bean
    public Step checkConnectionCountStep() {
        return stepBuilderFactory.get("checkConnection")
                .tasklet((contribution, chunkContext) -> {
                    monitorDto = new MonitorDto(new Date(), logDataList.size());
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step sendLogFileToFrontStep() {
        return stepBuilderFactory.get("sendLogFileFront")
                .tasklet((contribution, chunkContext) -> {
                    logService.sendMonitorDtoToClient("/sub/log",monitorDto);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    // log 파일을 불러오는 Step
    @Bean
    public Step flatFileItemReaderStep() {
        return stepBuilderFactory.get("flatFileItemReader")
                .tasklet((contribution, chunkContext) ->{
                    logDataList = logParser.parseLog("C:/Users/SSAFY/Desktop/Project/BatchMonitor/springBatchTest/log.txt");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
