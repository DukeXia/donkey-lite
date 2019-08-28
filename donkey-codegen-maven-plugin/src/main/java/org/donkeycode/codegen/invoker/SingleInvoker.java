package org.donkeycode.codegen.invoker;

import java.sql.SQLException;

import org.donkeycode.codegen.entity.TableClass;
import org.donkeycode.codegen.invoker.base.AbstractBuilder;
import org.donkeycode.codegen.invoker.base.AbstractInvoker;
import org.donkeycode.codegen.invoker.base.Invoker;
import org.donkeycode.codegen.utils.StringUtil;

/**
 * 
 * @author nanfeng
 *
 */
public class SingleInvoker extends AbstractInvoker {

	@Override
	protected void getTableInfos() throws SQLException {
		columnFields = jDBCConnectionFactory.getMetaData(tableClass.getTableName());
	}

	@Override
	protected void initTasks() {
		taskQueue.initTasks(tableClass, columnFields);
	}

	public static class Builder extends AbstractBuilder {

		private SingleInvoker invoker = new SingleInvoker();

		public Builder setTableClass(TableClass tableClass) {
			invoker.setTableClass(tableClass);
			return this;
		}
		
		@Override
		public Invoker build() {
			if (!isParamtersValid()) {
				return null;
			}
			return invoker;
		}

		@Override
		public void checkBeforeBuild() throws Exception {
			if (StringUtil.isBlank(invoker.getTableClass().getTableName())) {
				throw new Exception("Expect table's name, but get a blank String.");
			}
			if (StringUtil.isBlank(invoker.getTableClass().getTableName())) {
				//invoker.set(GeneratorUtil.generateClassName(invoker.getTableClass().getTableName()));
			}
		}
	}

}
