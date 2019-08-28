package org.donkeycode.codegen;

import java.util.Map;

import org.apache.commons.text.WordUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.donkeycode.codegen.ContextConfiguration.Table;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.invoker.SingleInvoker;
import org.donkeycode.codegen.invoker.base.Invoker;
import org.donkeycode.codegen.utils.ConfigUtil;

import com.donkeycode.core.utils.CollectionUtils;

/**
 *
 */
@Mojo(name = "codegen")
public class CodegenMojo extends AbstractMojo {

    /**
     * 插件执行入口
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

    	Map<String, Table> tables = ConfigUtil.getConfiguration().getTables();
        ContextConfiguration.DataGenerator dataGenerator = ConfigUtil.getConfiguration().getDataGenerator();

        if (CollectionUtils.isNotEmpty(tables)) {
            tables.forEach((key, table) -> {
                TableClass tableClass = new TableClass();
                tableClass.setDescription(table.getDescription());
                tableClass.setTableName(table.getTableName());
                tableClass.setShortClassName(table.getDomainName());
                tableClass.setAlias(table.getAlias());
                tableClass.setFullClassName(dataGenerator.getModelPackage().concat("." + table.getDomainName()));
                tableClass.setPackageName(dataGenerator.getModelPackage());
                tableClass.setLowerCaseName(WordUtils.uncapitalize(table.getDomainName()));

                Invoker invoker = new SingleInvoker.Builder().setTableClass(tableClass).build();
                invoker.execute();
            });
        }
    }

    public static void main(String[] args) {
        Map<String, Table> tables = ConfigUtil.getConfiguration().getTables();
        ContextConfiguration.DataGenerator dataGenerator = ConfigUtil.getConfiguration().getDataGenerator();

        if (CollectionUtils.isNotEmpty(tables)) {
            tables.forEach((key, table) -> {
                TableClass tableClass = new TableClass();
                tableClass.setDescription(table.getDescription());
                tableClass.setTableName(table.getTableName());
                tableClass.setShortClassName(table.getDomainName());
                tableClass.setAlias(table.getAlias());
                tableClass.setFullClassName(dataGenerator.getModelPackage().concat("." + table.getDomainName()));
                tableClass.setPackageName(dataGenerator.getModelPackage());
                tableClass.setLowerCaseName(WordUtils.uncapitalize(table.getDomainName()));

                Invoker invoker = new SingleInvoker.Builder().setTableClass(tableClass).build();
                invoker.execute();
            });
        }
    }
}