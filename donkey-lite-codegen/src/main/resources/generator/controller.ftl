package ${package};

import ${tableClass.fullClassName};
import ${servicePackage}.${tableClass.shortClassName}Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.donkeycode.core.page.PageFilter;
import com.donkeycode.core.page.ListFilter;
import com.donkeycode.core.utils.ObjectUtils;
import com.donkeycode.core.validation.ValidateUtils;
import com.donkeycode.web.HttpCode;
import com.donkeycode.web.HttpServletParamUtils;
import com.donkeycode.web.ResponseCode;
import com.donkeycode.web.WebConstants;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 资源Web接口
 *
 *
 * @author ${author}
 * @since  ${date}
 */
@RestController
@RequestMapping(value = "/api/${tableClass.lowerCaseName}")
@Api(tags="${tableClass.shortClassName}")
public class ${tableClass.shortClassName}Controller extends BaseController{

    @Autowired
    ${tableClass.shortClassName}Service ${tableClass.lowerCaseName}Service;

    /**
     * 获取资源
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/page")
    @ApiOperation(value="获取资源列表 - 分页", notes="通过不同过滤条件查询数据")
    public ResponseEntity<Object> page(HttpServletRequest request) {
        PageFilter pageFilter = HttpServletParamUtils.pageFilter(request);
        PageInfo<${tableClass.shortClassName}> page = ${tableClass.lowerCaseName}Service.selectPage(getCurrentUserId(), pageFilter);
        return ResponseEntity.ok(page);
    }

    /**
     * 获取资源
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value="获取资源列表", notes="通过不同过滤条件查询数据")
    public ResponseEntity<Object> list(HttpServletRequest request) {
        ListFilter listFilter = HttpServletParamUtils.listFilter(request);
        List<${tableClass.shortClassName}> pageList = ${tableClass.lowerCaseName}Service.selectList(getCurrentUserId(), listFilter);
        return ResponseEntity.ok(pageList);
    }

    /**
     * 获取资源
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value="获取资源详情", notes="根据Id获取详细")
    public ResponseEntity<Object> get(@PathVariable("id") String id) {
        ValidateUtils.notBlank(id, "资源主键不能为空");
        return ResponseEntity.ok(${tableClass.lowerCaseName}Service.selectByKey(id));
    }

    /**
     * 新增资源
     *
     * @param beanJson
     * @return
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="新增资源", notes="创建资源")
    public ResponseCode save(@RequestBody String json) {
        ${tableClass.shortClassName} ${tableClass.lowerCaseName} = JSON.parseObject(json, ${tableClass.shortClassName}.class);
        ${tableClass.lowerCaseName}Service.save(${tableClass.lowerCaseName});
        return ResponseCode.returnResponse(HttpCode.OK, "保存资源成功");
    }

    /**
     * 更新资源
     *
     * @param json
     * @return
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="更新资源", notes="创建资源")
    public ResponseCode update(@RequestBody String json) {
        ${tableClass.shortClassName} ${tableClass.lowerCaseName} = JSON.parseObject(json, ${tableClass.shortClassName}.class);
        ${tableClass.lowerCaseName}Service.save(${tableClass.lowerCaseName});
        return ResponseCode.returnResponse(HttpCode.OK, "更新资源成功");
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value="删除资源", notes="根据Id删除资源")
    public ResponseCode delete(@PathVariable("id") String id) {
        ${tableClass.lowerCaseName}Service.deleteByKey(id);
        return ResponseCode.returnResponse(HttpCode.OK, "删除资源成功");
    }
}
