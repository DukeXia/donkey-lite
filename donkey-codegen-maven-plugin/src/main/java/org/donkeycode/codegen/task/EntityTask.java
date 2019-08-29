package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.entity.ColumnField;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.GeneratorUtil;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nanfeng
 * @data 2019年8月29日
 */
@Slf4j
public class EntityTask extends AbstractTask {

    public EntityTask(ContextConfiguration configuration, TableClass tableClass, List<ColumnField> columnFields) {
        super(configuration, tableClass, columnFields);
    }

    public EntityTask(ContextConfiguration configuration, TableClass tableClass) {
        super(configuration, tableClass);
    }

    @Override
    public void run() throws IOException, TemplateException {
        ContextConfiguration.DataGenerator data = configuration.getDataGenerator();
        log.info("Generating " + tableClass.getShortClassName() + ".java");
        Map<String, Object> entityData = new HashMap<>();
        entityData.put("tableClass", tableClass);
        entityData.put("targetPackage", data.getModelPackage());
        entityData.put("Properties", GeneratorUtil.generateEntityProperties(columnFields));
        entityData.put("author", configuration.getAuthor());
        entityData.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String filePath = configuration.getSourcePath() + StringUtil.package2Path(data.getModelPackage());
        String fileName = tableClass.getShortClassName() + ".java";
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_ENTITY, entityData, filePath + fileName);
    }
}
