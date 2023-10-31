package com.example.springbatchtest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FindTwoNumsDto {
    Long x; // ms 시간
    Integer y; //  해당 요청 갯수
}
