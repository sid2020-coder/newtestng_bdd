package config;

import org.aeonbits.owner.ConfigCache;

public class propertyUtil {

    public static PropertyConfig getConfig(){
        return ConfigCache.getOrCreate(PropertyConfig.class);
    }


}
