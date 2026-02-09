package nh.demo.plantify.shared.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ClockProvider {

    private static final Logger log = LoggerFactory.getLogger( ClockProvider.class );

    @Bean
    Clock clock() {
        log.info("🕰️ Registering Clock...");
        return Clock.systemDefaultZone();
    }

}
