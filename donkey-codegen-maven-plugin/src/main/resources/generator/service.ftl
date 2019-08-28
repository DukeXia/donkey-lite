package ${targetPackage};

import ${mapperPackage}.${tableClass.shortClassName}Mapper;
import ${targetPackage}.${tableClass.shortClassName}Service;
import ${tableClass.fullClassName};

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.utils.CollectionUtils;
import com.donkeycode.boot.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import com.donkeycode.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.donkeycode.core.validation.ValidateUtils;

import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;
import java.util.Map;

/**
 * 资源服务接口
 *
 * @author ${author}
 * @since  ${date}
 */
@Service
public class ${tableClass.shortClassName}ServiceImpl extends BaseService<${tableClass.shortClassName}> implements ${tableClass.shortClassName}Service{


    @Autowired
    ${tableClass.shortClassName}Mapper ${tableClass.lowerCaseName}Mapper;

    @Override
    public boolean save(${tableClass.shortClassName} entity) {
        return mapper.insertSelective(entity)>0 ? true: false;
    }
    
    public List<${tableClass.shortClassName}> selectList(String userId, Map<String, String> param) {
        Weekend<${tableClass.shortClassName}> weekend = new Weekend<>(${tableClass.shortClassName}.class);
        WeekendCriteria<${tableClass.shortClassName}, Object> criteria = weekend.weekendCriteria();
        if (CollectionUtils.isNotEmpty(param)) {

        }
        return selectList(weekend);
    }
    
}
