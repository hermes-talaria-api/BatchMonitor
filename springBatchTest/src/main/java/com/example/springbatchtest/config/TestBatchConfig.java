package com.example.springbatchtest.config;

import com.example.springbatchtest.config.reader.CustomRecordSeperatePolicy;
import com.example.springbatchtest.dto.FindTwoNumsDto;
import com.example.springbatchtest.dto.LogDto;
import com.example.springbatchtest.mapper.LogMapper;
import com.example.springbatchtest.service.FindTwoService;
import com.example.springbatchtest.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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

import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Configuration
//@EnableBatchProcessing
@RequiredArgsConstructor
public class TestBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final LogService logService;
    private final FindTwoService findTwoService;

//    @Bean
//    public Job ExampleJob(){
//
//        Job exampleJob = jobBuilderFactory.get("exampleJob1")
//                .incrementer(new RunIdIncrementer())
//                .start(Step())
//                .build();
//
//        return exampleJob;
//    }

    @Bean
    public Job fileJob(){

        Job fileJob = jobBuilderFactory.get("fileJob")
                .incrementer(new RunIdIncrementer())
                .start(fileStep())
                .build();

        return fileJob;
    }

//    @Bean
//    public Step Step() {
//        return stepBuilderFactory.get("step")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("Step!");
//                    try (FileInputStream fis = new FileInputStream("C:/Users/SSAFY/Desktop/git/springBatchTest/log.txt");
//                         BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            // 파일에서 한 줄씩 읽음
//                            System.out.println(line);
////                            logService.sendMessageToClient("/sub/log",line);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }

    @Bean
    public Step fileStep() {

        Step step =  stepBuilderFactory.get("flatFilesStep1")
                .<LogDto, LogDto>chunk(5)
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
        lineMapper.setFieldSetMapper(new LogMapper(findTwoService)); /* filedSetMapper */
        itemReader.setLineMapper(lineMapper);
//        itemReader.setRecordSeparatorPolicy(new CustomRecordSeperatePolicy(findTwoService));
//        itemReader.setLinesToSkip(1); // 첫번째 row 건너뜀
        return itemReader;
    }

    @Bean
    public ItemWriter<LogDto> itemWriter(){
        return new ItemWriter<LogDto>() {
            @Override
            public void write(List<? extends LogDto> items) throws Exception {
                Map<Long, Integer> dateCountMap = new HashMap<>();
                List<FindTwoNumsDto> list = new ArrayList<>();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH.mm.ss Z", Locale.US);
                for(LogDto item : items){
                    log.info("getDate : "+item.getDate());
                    Date date = dateFormat.parse(item.getDate());
                    long milliseconds = date.getTime();

                    if (dateCountMap.containsKey(milliseconds)) {
                        dateCountMap.put(milliseconds, dateCountMap.get(milliseconds) + 1);
                    } else {
                        dateCountMap.put(milliseconds, 1);
                    }
                }

                dateCountMap.forEach((key,value) ->{
//                    list.add(new FindTwoNumsDto(key,value));'
                    log.info("key {} : value {}",key,value);
                    FindTwoNumsDto result = new FindTwoNumsDto(key,value);
                    logService.sendMessageToClient("/sub/log",result);
                });

//                System.out.println("Processed LogDto: " + list);

            }
        };
    }



    }