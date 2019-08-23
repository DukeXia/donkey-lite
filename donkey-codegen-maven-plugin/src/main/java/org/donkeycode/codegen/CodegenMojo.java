package org.donkeycode.codegen;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.donkeycode.codegen.invoker.SingleInvoker;
import org.donkeycode.codegen.invoker.base.Invoker;

/**
 *
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
    	Invoker invoker = new SingleInvoker.Builder().setTableName("user").setClassName("User").build();
		invoker.execute();

    }
}