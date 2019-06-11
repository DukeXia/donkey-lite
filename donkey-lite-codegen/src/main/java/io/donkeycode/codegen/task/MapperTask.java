package io.donkeycode.codegen.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.TemplateException;
import io.donkeycode.codegen.task.base.AbstractTask;
import io.donkeycode.codegen.utils.ConfigUtil;
import io.donkeycode.codegen.utils.FileUtil;
import io.donkeycode.codegen.utils.FreemarketConfigUtils;
import io.donkeycode.codegen.utils.StringUtil;

/**
 * Author GreedyStar
 * Date   2018/4/20
 */
public class MapperTask extends AbstractTask {

    public MapperTask(String className) {
        super(className);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Dao填充数据
        System.out.println("Generating " + className + "Mapper.java");
        Map<String, String> daoData = new HashMap<>();
        daoData.put("BasePackageName", ConfigUtil.getConfiguration().getPackageName());
        daoData.put("DaoPackageName", ConfigUtil.getConfiguration().getPath().getMapper());
        daoData.put("EntityPackageName", ConfigUtil.getConfiguration().getPath().getEntity());
        daoData.put("Author", ConfigUtil.getConfiguration().getAuthor());
        daoData.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        daoData.put("ClassName", className);
        daoData.put("EntityName", StringUtil.firstToLowerCase(className));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName()) + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getMapper());
        String fileName = className + "Mapper.java";
        // 生成dao文件
        FileUtil.generateToJava(FreemarketConfigUtils.TYPE_MAPPER, daoData, filePath + fileName);
    }
}
