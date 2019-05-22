package ${BasePackageName}.${DaoPackageName};

import ${BasePackageName}.${EntityPackageName}.${ClassName};
import org.apache.ibatis.annotations.Mapper;
import com.donkeycode.boot.data.utils.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author ${Author}
 * @since  ${Date}
 */
@Repository
@Mapper
public interface ${ClassName}Mapper extends BaseMapper<${ClassName}>{

}