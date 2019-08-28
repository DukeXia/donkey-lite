package ${targetPackage};

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
     * 新增资源
     *
     * @param ids
     * @return 返回操作是否成功
     */
    boolean save(${tableClass.shortClassName} entity);
}
