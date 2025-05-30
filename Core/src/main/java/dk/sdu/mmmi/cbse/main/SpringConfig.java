package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ServiceLoader;

@Configuration
@ComponentScan(basePackages = "dk.sdu.mmmi.cbse")

public class SpringConfig {

    @Bean
    public Iterable<IGamePluginService> gamePluginServices() {
        return ServiceLoader.load(IGamePluginService.class);
    }

    @Bean
    public Iterable<IEntityProcessingService> entityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class);
    }
    @Bean
    public Iterable<dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class);
    }
}
