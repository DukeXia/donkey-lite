package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
@Slf4j
public class ServiceTask extends AbstractTask {

    public ServiceTask(ContextConfiguration configuration, TableClass tableClass) {
        super(configuration, tableClass);
    }

    @Override
    public void run() throws IOException, TemplateException {
        ContextConfiguration.ServiceGenerator serviceGenerator = configuration.getServiceGenerator();
        ContextConfiguration.DataGenerator dataGenerator = configuration.getDataGenerator();
        Map<String, Object> serviceData = new HashMap<>();
        serviceData.put("tableClass", tableClass);
        serviceData.put("targetPackage", serviceGenerator.getTargetPackage());
        serviceData.put("mapperPackage", dataGenerator.getMapperPackage());
        serviceData.put("author", configuration.getAuthor());
        serviceData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String filePath = configuration.getSourcePath() + StringUtil.package2Path(serviceGenerator.getTargetPackage());
        String fileName = tableClass.getShortClassName() + "ServiceImpl.java";
        log.info("Generating " + tableClass.getShortClassName() + "ServiceImpl.java");
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_SERVICE, serviceData, filePath + fileName);
    }
}
