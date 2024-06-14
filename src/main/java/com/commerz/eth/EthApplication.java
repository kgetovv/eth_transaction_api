package com.commerz.eth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class EthApplication {

    private static final Logger log = LoggerFactory.getLogger(EthApplication.class);

    public static void main(String[] args) {
    Environment env = SpringApplication.run(EthApplication.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
