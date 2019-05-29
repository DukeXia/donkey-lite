package ${package};

import ${tableClass.fullClassName};
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
* @since  0.0.1
*/
@Repository
@Mapper
public interface ${tableClass.shortClassName}${mapperSuffix} extends ${baseMapper!"tk.mybatis.mapper.common.Mapper"}<${tableClass.shortClassName}> {

}




