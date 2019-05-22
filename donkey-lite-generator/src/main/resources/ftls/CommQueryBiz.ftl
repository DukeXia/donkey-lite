package ${BasePackageName}.${CommBizPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName};
import ${BasePackageName}.${ServicePackageName}.${ClassName}Service;
import com.donkeycode.boot.interfaces.annotation.ResourceInfo;
import com.donkeycode.boot.interfaces.pubinterface.IDataQueryComponent;
import org.springframework.beans.factory.annotation.Autowired;
import ${BasePackageName}.constants.ResourceConstants;
import com.donkeycode.boot.interfaces.pubinterface.info.PageQueryResult;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author ${Author}
 * @since  ${Date}
 */
@ResourceInfo(type = ResourceConstants.RESOURCE_TYPE_${ClassNameUpperCase}, description = "${EntityDescription}查询接口")
public class ${ClassName}Query implements IDataQueryComponent{

    @Autowired
    ${ClassName}Service ${EntityName}Service;

    @Override
    public PageQueryResult pageList(String operateType, Map<String, String> param, int pageNum, int pageSize) {
        PageInfo<${ClassName}> pageInfo = ${EntityName}Service.getPageList(param, pageNum, pageSize);
        return new PageQueryResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
	public List<?> list(String operateType, Map<String, String> param) {
        return ${EntityName}Service.getList(param);
	}


    @Override
    public Object find(String operateType, String key, Map<String, String> params) {
        Assert.notNull(StringUtils.isNotEmpty(key), "AgentQuery find 参数不能为空");
        return ${EntityName}Service.selectByPrimaryKey(Long.parseLong(key));
    }

}
