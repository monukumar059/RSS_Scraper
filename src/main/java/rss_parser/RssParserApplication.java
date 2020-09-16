package rss_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RssParserApplication {
    public static void main(String[] args) {
        SpringApplication.run(RssParserApplication.class, args);
    }
}
