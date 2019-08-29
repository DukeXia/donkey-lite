package org.donkeycode.codegen;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.text.WordUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.donkeycode.codegen.ContextConfiguration.Table;
import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.invoker.SingleInvoker;
import org.donkeycode.codegen.invoker.base.Invoker;
import org.donkeycode.codegen.utils.ConfigurationParser;

import com.donkeycode.core.utils.CollectionUtils;

/**
 * 
 * @author nanfeng
 * @data 2019年8月30日
 */
@Mojo(name = "code", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class CodegenMojo extends AbstractMojo {

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "gencode.generator.outputDirectory", defaultValue = "${project.build.directory}/generated-sources/mybatis-generator", required = true)
    private File outputDirectory;

    @Parameter(property = "gencode.generator.configurationFile", defaultValue = "${project.basedir}/src/main/resources/generator.yaml", required = true)
    private File configurationFile;

    /**
     * 插件执行入口
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        ConfigurationParser cp = new ConfigurationParser();
        try {
            ContextConfiguration configuration = cp.parseConfiguration(configurationFile);
            configuration.setBasicProjectPath(getBasicProjectPath(project.getBuild().getOutputDirectory()));
            Map<String, Table> tables = configuration.getTables();
            log.debug("Read table info");

            ContextConfiguration.DataGenerator dataGenerator = configuration.getDataGenerator();
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

                    Invoker invoker = new SingleInvoker.Builder().setConfiguration(configuration).setTableClass(tableClass).build();
                    invoker.execute();
                });
            }
        } catch (IOException e) {
            log.info("error. message:" + e.getMessage());
        }

    }

    private static String getBasicProjectPath(String classesPath) {
        String path = new File(classesPath).getPath() + File.separator;
        StringBuilder sb = new StringBuilder();
        sb.append(path.substring(0, path.indexOf("target"))).append("src").append(File.separator).append("main").append(File.separator);
        return sb.toString();
    }
}