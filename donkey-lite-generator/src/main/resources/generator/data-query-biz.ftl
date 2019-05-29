package ${package};

import ${tableClass.fullClassName};

import ${servicePackage}.${tableClass.shortClassName}Service;
import com.donkeycode.boot.annotation.ResourceInfo;
import com.donkeycode.boot.pubinterface.DataQueryService;
import com.donkeycode.consts.Constants;
import com.donkeycode.consts.ResourceConstants;
import org.springframework.beans.factory.annotation.Autowired;

import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.PageResult;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author ${author}
 * @since  ${date}
 */
@ResourceInfo(type = ResourceConstants.RESOURCE_TYPE_${tableClass.upperCase}, description = "${tableClass.description}查询接口")
public class ${tableClass.shortClassName}Query implements DataQueryService{

    @Autowired
    private ${tableClass.shortClassName}Service ${tableClass.lowerCaseName}Service;

    @Override
    public PageResult<?> pageList(String operateType, PageFilter pageFilter) {
        PageInfo<${tableClass.shortClassName}> pageInfo = ${tableClass.lowerCaseName}Service.getPageList(pageFilter);
        return new PageResult<${tableClass.shortClassName}>(pageInfo.getTotal(), pageInfo.getList());
    }


    @Override
	public List<?> list(String operateType, Map<String, String> param) {
        return ${tableClass.lowerCaseName}Service.getList(param);
	}


    @Override
    public Object find(String operateType, String key, Map<String, String> params) {
        Assert.notNull(StringUtils.isNotEmpty(key), "AgentQuery find 参数不能为空");
        return ${tableClass.lowerCaseName}Service.selectByPrimaryKey(Long.parseLong(key));
    }

}
