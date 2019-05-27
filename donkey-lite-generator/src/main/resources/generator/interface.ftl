package ${BasePackageName}.${InterfacePackageName};

import com.donkeycode.boot.data.base.IBaseService;
import ${BasePackageName}.${EntityPackageName}.${ClassName};

import java.util.List;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * 测试
 *
 * @author ${Author}
 * @since  ${Date}
 */
public interface ${ClassName}Service extends IBaseService<${ClassName}>{

    List<${ClassName}> getList(Map<String, String> params);

    PageInfo<${ClassName}> getPageList(Map<String, String> params, int pageNum, int pageSize);

    void deletes(List<Long> ids);
}
