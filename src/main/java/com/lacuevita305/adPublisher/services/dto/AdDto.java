package com.lacuevita305.adPublisher.services.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdDto {

    @CsvBindByPosition(position = 0)
    private Double price;

    @CsvBindByPosition(position = 1)
    private String currency;

    @CsvBindByPosition(position = 2)
    private String title;

    @CsvBindByPosition(position = 3)
    private String body;

}