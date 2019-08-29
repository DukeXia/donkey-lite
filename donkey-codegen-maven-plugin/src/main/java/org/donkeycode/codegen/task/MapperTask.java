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
public class MapperTask extends AbstractTask {

    public MapperTask(ContextConfiguration configuration, TableClass tableClass) {
        super(configuration, tableClass);
    }

    @Override
    public void run() throws IOException, TemplateException {
        ContextConfiguration.DataGenerator data = configuration.getDataGenerator();
        log.info("Generating " + tableClass.getShortClassName() + "Mapper.java");
        Map<String, Object> mapperData = new HashMap<>();
        mapperData.put("tableClass", tableClass);
        mapperData.put("targetPackage", data.getMapperPackage());
        mapperData.put("baseMapper", "com.donkeycode.boot.utils.BaseMapper");
        mapperData.put("author", configuration.getAuthor());
        mapperData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String filePath = configuration.getSourcePath() + StringUtil.package2Path(data.getMapperPackage());
        String fileName = tableClass.getShortClassName() + "Mapper.java";
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_DAO, mapperData, filePath + fileName);
    }
}
