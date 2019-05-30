package ${package};

import com.donkeycode.boot.IBaseService;
import ${tableClass.fullClassName};

import java.util.List;
import com.donkeycode.core.page.PageFilter;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * 资源服务接口定义
 *
 * @author ${author}
 * @since  ${date}
 */
public interface ${tableClass.shortClassName}Service extends IBaseService<${tableClass.shortClassName}>{

    /**
     * 查找资源
     *
     * @param params
     * @return
     */
    List<${tableClass.shortClassName}> getList(Map<String, String> params);

    /**
     * 以分页的形式查找资源
     *
     * @param pageFilter
     * @return
     */
    PageInfo<${tableClass.shortClassName}> getPageList(PageFilter pageFilter);

    /**
     * 通过主键删除资源
     *
     * @param ids
     */
    void deletes(List<Long> ids);

    /**
     * 新增资源
     *
     * @param ids
     * @return 返回操作是否成功
     */
    boolean save(${tableClass.shortClassName} entity);
}
