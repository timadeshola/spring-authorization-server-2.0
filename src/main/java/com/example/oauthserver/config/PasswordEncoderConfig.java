package com.example.oauthserver.config;

import com.example.oauthserver.model.config.HashConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/29/23
 * Time: 9:32 AM
 */
@Configuration
@RequiredArgsConstructor
public class PasswordEncoderConfig {

    private final HashConfig config;


    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        String encodingId = "argon2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, config.getStrength(), new SecureRandom()));
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder(config.getSecret(), config.getLength(), config.getIterations(), Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));
        encoders.put("scrypt", new SCryptPasswordEncoder(config.getCpuCost(), config.getMemoryCost(), config.getParallelization(), config.getHashLength(), config.getLength()));
        encoders.put("argon2", new Argon2PasswordEncoder(config.getLength(), config.getHashLength(), config.getParallelization(), config.getMemory(), config.getIterations()));

        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new Argon2PasswordEncoder(config.getLength(), config.getHashLength(), config.getParallelization(), config.getMemory(), config.getIterations()));
        return delegatingPasswordEncoder;
    }

//    @Bean
//    public PasswordEncoder bcryptPasswordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

   /* @Bean(name = "bcrypt")
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, config.getStrength(), new SecureRandom());
    }

    @Bean(name = "argon2")
    public PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder(config.getLength(), config.getHashLength(), config.getParallelization(), config.getMemory(), config.getIterations());
    }

    @Bean(name = "scrypt")
    public PasswordEncoder scryptPasswordEncoder() {
        return new SCryptPasswordEncoder(config.getCpuCost(), config.getMemoryCost(), config.getParallelization(), config.getHashLength(), config.getLength());
    }

    @Bean(name = "pbkdf2")
    public PasswordEncoder pbkdf2PasswordEncoder() {
        return new Pbkdf2PasswordEncoder(config.getSecret(), config.getLength(), config.getIterations(), Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }*/

}
