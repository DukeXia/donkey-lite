package ${BasePackageName}.${InterfacePackageName};

import com.donkeycode.boot.data.base.IBaseService;
import ${BasePackageName}.${EntityPackageName}.${ClassName};

import java.util.List;
import com.github.pagehelper.PageInfo;
import java.util.Map;

/**
 * @author ${Author}
 * @since  ${Date}
 */
public interface ${ClassName}Service extends IBaseService<${ClassName}>{

    List<${ClassName}> getList(Map<String, String> param);

    PageInfo<${ClassName}> getPageList(Map<String, String> param, int pageNum, int pageSize);

    void deletes(List<Long> ids);
}
