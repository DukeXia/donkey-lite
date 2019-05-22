package ${BasePackageName}.${CommBizPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName};
import ${BasePackageName}.${ServicePackageName}.${ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.donkeycode.core.validation.ValidationUtils;
import com.donkeycode.boot.interfaces.pubinterface.IDataEditComponent;
import com.donkeycode.boot.interfaces.annotation.ResourceInfo;
import com.donkeycode.boot.interfaces.pubinterface.info.DataEditInfo;
import com.donkeycode.boot.interfaces.pubinterface.info.LinkedInfo;
import com.donkeycode.boot.constants.ResourceConstants;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author ${Author}
 * @since  ${Date}
 */
@ResourceInfo(type = ResourceConstants.RESOURCE_TYPE_${ClassNameUpperCase}, description = "${EntityDescription}修改接口")
public class ${ClassName}DataEdit implements IDataEditComponent{

    @Autowired
    ${ClassName}Service ${EntityName}Service;


    @Override
	public LinkedInfo insert(DataEditInfo dataEditInfo) {

        ${ClassName} ${EntityName} = JSON.parseObject(dataEditInfo.editInfo, ${ClassName}.class);
		ValidationUtils.validate(${EntityName});

        ${EntityName}Service.insertSelective(${EntityName});
		return new LinkedInfo(${EntityName}.getFunctionId() + "", dataEditInfo.editInfo);
	}

	@Override
	public LinkedInfo update(DataEditInfo dataEditInfo) {

        ${ClassName} ${EntityName} = JSON.parseObject(dataEditInfo.editInfo, ${ClassName}.class);

        ValidationUtils.validate(${EntityName});
        ${EntityName}Service.updateByPrimaryKey(${EntityName});
		return new LinkedInfo(${EntityName}.getFunctionId() + "", dataEditInfo.editInfo);
	}

	@Override
	public LinkedInfo delete(DataEditInfo dataEditInfo) {

		List<Long> ids = JSON.parseArray(dataEditInfo.editInfo, Long.class);
        ${EntityName}Service.deletes(ids);
        return new LinkedInfo(dataEditInfo.editInfo, dataEditInfo.editInfo);
    }
}
