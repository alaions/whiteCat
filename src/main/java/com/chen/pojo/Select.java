package com.chen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "select")
public class Select {
    private String message;
    private String selectMessage;
    private String startTime;
    private String endTime;
    private Integer showCount;
}
