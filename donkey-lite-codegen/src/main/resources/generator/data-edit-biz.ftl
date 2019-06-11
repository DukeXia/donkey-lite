package ${package};

import ${tableClass.fullClassName};

import ${servicePackage}.${tableClass.shortClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.donkeycode.core.validation.ValidationUtils;
import com.donkeycode.boot.annotation.ResourceInfo;
import com.donkeycode.boot.pubinterface.DataEditService;
import com.donkeycode.boot.pubinterface.info.DataEditInfo;
import com.donkeycode.boot.pubinterface.info.LinkedInfo;
import com.donkeycode.boot.constants.ResourceConstants;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 通用修改数据接口
 *
 * @author ${author}
 * @since  ${date}
 */
@ResourceInfo(type = ResourceConstants.RESOURCE_TYPE_${tableClass.upperCase}, description = "${tableClass.description}修改接口")
public class ${tableClass.shortClassName}DataEdit implements DataEditService{

    @Autowired
    private ${tableClass.shortClassName}Service ${tableClass.lowerCaseName}Service;


    @Override
	public LinkedInfo insert(DataEditInfo dataEditInfo) {

        ${tableClass.shortClassName} ${tableClass.lowerCaseName} = JSON.parseObject(dataEditInfo.editInfo, ${tableClass.shortClassName}.class);
		ValidationUtils.validate(${tableClass.lowerCaseName});

        ${tableClass.lowerCaseName}Service.insertSelective(${tableClass.lowerCaseName});
		return new LinkedInfo(${tableClass.lowerCaseName}.get${tableClass.pkFields[0].fieldName?cap_first}() + "", dataEditInfo.editInfo);
	}

	@Override
	public LinkedInfo update(DataEditInfo dataEditInfo) {

        ${tableClass.shortClassName} ${tableClass.lowerCaseName} = JSON.parseObject(dataEditInfo.editInfo, ${tableClass.shortClassName}.class);

        ValidationUtils.validate(${tableClass.lowerCaseName});
        ${tableClass.lowerCaseName}Service.updateByPrimaryKey(${tableClass.lowerCaseName});
		return new LinkedInfo(${tableClass.lowerCaseName}.get${tableClass.pkFields[0].fieldName?cap_first }() + "", dataEditInfo.editInfo);
	}

	@Override
	public LinkedInfo delete(DataEditInfo dataEditInfo) {
		List<Long> ids = JSON.parseArray(dataEditInfo.editInfo, Long.class);
        ${tableClass.lowerCaseName}Service.deletes(ids);
        return new LinkedInfo(dataEditInfo.editInfo, dataEditInfo.editInfo);
    }
}
