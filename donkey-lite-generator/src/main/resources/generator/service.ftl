package ${package};

import ${mapperPackage}.${tableClass.shortClassName}Mapper;
import ${tableClass.fullClassName};

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.donkeycode.core.utils.CollectionUtils;
import com.donkeycode.boot.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;
import java.util.Map;

/**
 * @author donkey lite
 * @since  0.0.1
 */
@Service
public class ${tableClass.shortClassName}ServiceImpl extends BaseService<${tableClass.shortClassName}> implements ${tableClass.shortClassName}Service{


    @Autowired
    private ${tableClass.shortClassName}Mapper ${tableClass.lowerCaseName}Mapper;


    @Override
    public PageInfo<${tableClass.shortClassName}> getPageList(Map<String, String> params, int pageNum, int pageSize) {
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
    public List<${tableClass.shortClassName}> getList(Map<String, String> param) {
        Weekend<${tableClass.shortClassName}> weekend = new Weekend<>(${tableClass.shortClassName}.class);
        WeekendCriteria<${tableClass.shortClassName}, Object> criteria = weekend.weekendCriteria();
        if (null != param) {
            //criteria.andEqualTo(${tableClass.shortClassName}::getEffStatus, StatusEnum.ENABLE.getCode());
            //String order = SortUtils.getOrderString(param.get(SortUtils.SORT_FIELDS));

            //if (StringUtils.isNotEmpty(order)) {
              //  weekend.setOrderByClause(order);
            //}
        }
        return mapper.selectByExample(weekend);
    }
}
