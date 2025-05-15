package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ServiceLoader;


public class ServiceLoaderHelper{

    public static Iterable<IGamePluginService> loadGamePluginServices() {
        return ServiceLoader.load(IGamePluginService.class);
    }
    public static Iterable<IEntityProcessingService> loadEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class);
    }
    public static Iterable<IPostEntityProcessingService> loadPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class);
    }
}
