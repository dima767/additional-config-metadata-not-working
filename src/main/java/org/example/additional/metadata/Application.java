package org.example.additional.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;

@SpringBootApplication
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:META-INF/spring-configuration-metadata.json");
            ConfigurationMetadataRepositoryJsonBuilder builder = ConfigurationMetadataRepositoryJsonBuilder.create();
            for (Resource resource : resources) {
                try (InputStream in = resource.getInputStream()) {
                    builder.withJsonResource(in);
                }
            }
            ConfigurationMetadataRepository configMetadataRepo = builder.build();

            ConfigurationMetadataProperty prop = configMetadataRepo.getAllProperties().get("my.int-prop");
            String propName = prop.getName();

            LOGGER.info("{}/{}: {}", propName, "description", prop.getDescription());
            LOGGER.info("{}/{}: {}", propName, "type", prop.getType());
            if (prop.getDefaultValue() != null) {
                LOGGER.info("{}/{}: {}", propName, "default value", prop.getDefaultValue().toString());
            }
            prop.getHints().getValueHints().forEach(h -> LOGGER.info("HINT: {}", h));

        };
    }
}
