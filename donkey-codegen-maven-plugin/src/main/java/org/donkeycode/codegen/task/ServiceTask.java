package org.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.donkeycode.codegen.ContextConfiguration;
import org.donkeycode.codegen.ContextConfiguration.Path;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.task.base.AbstractTask;
import org.donkeycode.codegen.utils.ConfigUtil;
import org.donkeycode.codegen.utils.FileUtil;
import org.donkeycode.codegen.utils.FreemarketConfigUtils;
import org.donkeycode.codegen.utils.StringUtil;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * Author GreedyStar
 * Date   2018/4/20
 */
@Slf4j
public class ServiceTask extends AbstractTask {

    public ServiceTask(TableClass tableClass) {
        super(tableClass);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Service填充数据
        ContextConfiguration.ServiceGenerator serviceGenerator = ConfigUtil.getConfiguration().getServiceGenerator();
        ContextConfiguration.DataGenerator dataGenerator = ConfigUtil.getConfiguration().getDataGenerator();

        Map<String, Object> serviceData = new HashMap<>();
        serviceData.put("tableClass", tableClass);
        serviceData.put("targetPackage", serviceGenerator.getTargetPackage());
        serviceData.put("mapperPackageName", dataGenerator.getMapperPackage());
        serviceData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        serviceData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(serviceGenerator.getTargetProject()) + StringUtil.package2Path(serviceGenerator.getTargetPackage());

        serviceData.put("Impl", "Impl implements " + tableClass.getShortClassName() + "Service");
        serviceData.put("Override", "\n    @Override");
        serviceData.put("InterfaceImport", "import " + ConfigUtil.getConfiguration().getPackageName() + path.getInterf() + "." + className + "Service;");
        String fileName = tableClass.getShortClassName() + "ServiceImpl.java";
        log.info("Generating " + tableClass.getShortClassName() + "ServiceImpl.java");
        // 生成Service文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_SERVICE, serviceData, filePath + fileName);
    }
}
