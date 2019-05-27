package ${BasePackageName}.${ServicePackageName};

import ${BasePackageName}.${DaoPackageName}.${ClassName}Mapper;
import ${BasePackageName}.${EntityPackageName}.${ClassName};
${InterfaceImport}
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.donkeycode.core.collection.CollectionUtils;
import com.donkeycode.core.utils.SortUtils;
import com.donkeycode.boot.data.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;
import java.util.Map;

/**
 * @author ${Author}
 * @since  ${Date}
 */
@Service
public class ${ClassName}ServiceImpl extends BaseService<${ClassName}> implements ${ClassName}Service{


    @Autowired
    private ${ClassName}Mapper ${EntityName}Mapper;


    @Override
    public PageInfo<${ClassName}> getPageList(Map<String, String> params, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true);
        return new PageInfo<>(getList(params));
    }

    @Override
    public void deletes(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            deleteByPrimaryKeys(ids);
        }
    }

    @Override
    public List<${ClassName}> getList(Map<String, String> param) {
        Weekend<${ClassName}> weekend = new Weekend<>(${ClassName}.class);
        WeekendCriteria<${ClassName}, Object> criteria = weekend.weekendCriteria();
        if (null != param) {
            //criteria.andEqualTo(${ClassName}::getEffStatus, StatusEnum.ENABLE.getCode());
            String order = SortUtils.getOrderString(param.get(SortUtils.SORT_FIELDS));

            if (StringUtils.isNotEmpty(order)) {
                weekend.setOrderByClause(order);
            }
        }
        return mapper.selectByExample(weekend);
    }
}
