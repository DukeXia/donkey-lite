package ${package};

import com.donkeycode.boot.IBaseService;
import ${tableClass.fullClassName};

import java.util.List;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * 通用模版
 *
 * @author xueyanjun
 * @since  0.0.1
 */
public interface ${tableClass.shortClassName}Service extends IBaseService<${tableClass.shortClassName}>{

    /**
     * @param params
     * @return
     */
    List<${tableClass.shortClassName}> getList(Map<String, String> params);

    /**
     * @param params
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<${tableClass.shortClassName}> getPageList(Map<String, String> params, int pageNum, int pageSize);

    /**
     * @param ids
     */
    void deletes(List<Long> ids);
}
