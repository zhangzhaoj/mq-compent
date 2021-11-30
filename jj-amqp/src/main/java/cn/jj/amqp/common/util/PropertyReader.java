package cn.jj.amqp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author by wanghui03
 * @Classname PropertyReader
 * @Description TODO
 * @Date 2021/11/30 11:37
 */

@Slf4j
public class PropertyReader {

    private static PropertyReader ourInstance = new PropertyReader();

    public static PropertyReader getInstance() {
        return ourInstance;
    }

    private PropertyConfigurer configurer;

    /**
     *获取当前jar包所在系统中的目录
     */
    private String findDefaultHomeDir() {
        String userDir = System.getProperty("user.dir");
        File userFile = new File(StringUtils.hasLength(userDir) ? userDir : ".");
        if (userFile.isFile()) {
            userFile = userFile.getParentFile();
        }

        userFile = userFile.exists() ? userFile : new File(".");
        return userFile.getAbsoluteFile().getAbsolutePath();
    }

    private PropertyReader() {
        configurer = new PropertyConfigurer();
        PathMatchingResourcePatternResolver resolover = new PathMatchingResourcePatternResolver();
        try {
            String propertiesPattern = "/*.properties";
            File file = new File(findDefaultHomeDir() + "/config/application.properties");
            String propertiesFilePath = "classpath*:/config" + propertiesPattern;
            if(null != file && file.exists()){
                if(file.isFile()){
                    file = file.getParentFile();
                }
                propertiesFilePath = "file:" + file.getAbsolutePath() + propertiesPattern;
            }
            log.info("propertyReader.propertyFilePath:"+propertiesFilePath);
            Resource[] resources = resolover.getResources(propertiesFilePath);
            configurer.setLocations(resources);
            configurer.mergeProperties();
        }catch (IOException e){
            log.error("PropertyReader.propertyConfigurer",e);
        }
    }

    /**
     * 获取属性
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return configurer.getProperties().getProperty(key);
    }

    /**
     * 获取带默认值的属性
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(String key, String defaultValue) {
        return configurer.getProperties().getProperty(key, defaultValue);
    }

    /**
     * 是否包含该属性
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return configurer.getProperties().containsKey(key);
    }

}
