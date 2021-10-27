package com.sparta.StarProject.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StarGazingDto {
    private String name;
    private Long id;
    private Boolean ascending;
    private String localDateTime;   //"2021-10-25T07:00:00-03:00",
    private Long epochDateTime;
    private BigDecimal value;
    private String category;
    private Long categoryValue;
    private String mobileLink;
    private String link;

    public StarGazingDto(Map<String, Object> map) {
        this.name = (String) map.get("Name");
        this.id = Long.valueOf((int)map.get("ID"));
        this.ascending = (Boolean) map.get("Ascending");
        this.localDateTime = (String)map.get("LocalDateTime");
        this.epochDateTime = Long.valueOf((int)map.get("EpochDateTime"));
        this.value = (BigDecimal) map.get("Value");
        this.category = (String)map.get("Category");
        this.categoryValue = Long.valueOf((int)map.get("CategoryValue"));
        this.mobileLink = (String)map.get("MobileLink");
        this.link = (String)map.get("Link");
    }

}
