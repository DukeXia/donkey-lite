package ${targetPackage};

import com.donkeycode.boot.IBaseService;
import ${tableClass.fullClassName};

/**
 * ${tableClass.alias}服务接口定义
 *
 * @author ${author}
 * @since  ${date}
 */
public interface ${tableClass.shortClassName}Service extends IBaseService<${tableClass.shortClassName}>{

    /**
     * 新增${tableClass.alias}
     *
     * @param ids
     * @return 返回操作是否成功
     */
    boolean save(${tableClass.shortClassName} entity);
}
