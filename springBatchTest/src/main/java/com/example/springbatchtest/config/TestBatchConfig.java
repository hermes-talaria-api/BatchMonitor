package com.example.springbatchtest.config;

import com.example.springbatchtest.dto.LogDto;
import com.example.springbatchtest.mapper.LogMapper;
import com.example.springbatchtest.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Configuration
//@EnableBatchProcessing
@RequiredArgsConstructor
public class TestBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final LogService logService;

    @Bean
    public Job ExampleJob(){

        Job exampleJob = jobBuilderFactory.get("exampleJob1")
                .incrementer(new RunIdIncrementer())
                .start(Step())
                .build();

        return exampleJob;
    }

    @Bean
    public Job fileJob(){

        Job fileJob = jobBuilderFactory.get("fileJob")
                .incrementer(new RunIdIncrementer())
                .start(fileStep())
                .build();

        return fileJob;
    }

    @Bean
    public Step Step() {
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step!");
                    try (FileInputStream fis = new FileInputStream("C:/Users/SSAFY/Desktop/git/springBatchTest/log.txt");
                         BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            // 파일에서 한 줄씩 읽음
                            System.out.println(line);
//                            logService.sendMessageToClient("/sub/log",line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step fileStep() {

        Step step =  stepBuilderFactory.get("flatFilesStep1")
                .<LogDto, LogDto>chunk(200)
                .reader(itemReader())
                .writer(itemWriter())
                .build();

        return step;
    }

    @Bean
    public ItemReader<LogDto> itemReader() {
        FlatFileItemReader<LogDto> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("C:/Users/SSAFY/Desktop/git/BatchMonitor/springBatchTest/log.txt"));

        DefaultLineMapper<LogDto> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(" ");
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new LogMapper()); /* filedSetMapper */
        itemReader.setLineMapper(lineMapper);
//        itemReader.setLinesToSkip(1); // 첫번째 row 건너뜀

        return itemReader;
    }

    @Bean
    public ItemWriter<LogDto> itemWriter(){
        return items -> {
//            for (LogDto item : items) {
                // 처리된 LogDto 객체를 저장하거나 출력
//                System.out.println("Processed LogDto: " + item);
//                logService.sendMessageToClient("/sub/log",item);
//            }
        };
    }



    }