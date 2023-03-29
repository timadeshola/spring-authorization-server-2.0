package com.example.oauthserver.model.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/29/23
 * Time: 10:19 AM
 */
@ConfigurationProperties(prefix = "app.salt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HashConfig implements Serializable {

    private Integer strength;
    private String secret;
    private Integer length;
    private Integer iterations;
    private Integer cpuCost;
    private Integer memoryCost;
    private Integer parallelization;
    private Integer hashLength;
    private Integer memory;
}
