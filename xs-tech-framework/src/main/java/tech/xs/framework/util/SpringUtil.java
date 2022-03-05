package tech.xs.framework.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.stream.StreamUtil;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import tech.xs.framework.constant.ConfigConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringBoot 工具
 *
 * @author 沈家文
 * @date 2021-14-24 11:14
 */
public class SpringUtil {
    /**
     * 获取含有指定注解的所有类
     *
     * @param annotationClass 注解class
     * @return 返回查询到是结果集
     * @throws IOException            异常信息
     * @throws ClassNotFoundException 异常信息
     */
    public static List<Class<?>> getAnnotationClassList(Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(ConfigConstant.ROOT_PACKAGE) + "/**/*.class";
        org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(pattern);
        MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        List<Class<?>> classList = new ArrayList<>();
        for (org.springframework.core.io.Resource resource : resources) {
            MetadataReader reader = readerfactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(className);
            Annotation annotation = clazz.getAnnotation(annotationClass);
            if (annotation != null) {
                classList.add(clazz);
            }
        }
        return classList;
    }

    /**
     * 读取资源文件
     *
     * @param path
     * @return
     */
    public static String getResourceStringContent(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream stream = classPathResource.getStream();
            String content = IoUtil.readUtf8(stream);
            stream.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取资源文件
     *
     * @param path
     * @return
     */
    public static Reader getResourceFileReader(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            return classPathResource.getReader(Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 深度遍历文件夹,并返回其URL
     *
     * @param path
     * @return
     */
    public static List<URL> getDepthFileResource(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        try {
            List<URL> res = new ArrayList<>();
            List<URL> urlList = ResourceUtil.getResources(path);
            for (int i = 0; i < urlList.size(); i++) {
                URL url = urlList.get(i);
                File file = new UrlResource(urlList.get(i)).getFile();
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File item : files) {
                            urlList.add(item.toURI().toURL());
                        }
                    }
                } else {
                    res.add(file.toURI().toURL());
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResourceStringContent(URL url) {
        UrlResource urlResource = new UrlResource(url);
        return urlResource.readUtf8Str();
    }

}
