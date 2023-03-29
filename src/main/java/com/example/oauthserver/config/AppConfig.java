package com.example.oauthserver.config;

import com.example.oauthserver.model.config.HashConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/29/23
 * Time: 10:38 PM
 */
@Configuration
@EnableConfigurationProperties(HashConfig.class)
public class AppConfig {
}
