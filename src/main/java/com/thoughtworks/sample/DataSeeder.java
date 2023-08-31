package com.thoughtworks.sample;

import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(VersionRepository versionRepository){
        return args -> {
            if(versionRepository.currentVersion().isEmpty()){
                versionRepository.save(new Version(1,"v2"));
            }
        };
    }
}
