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
import com.donkeycode.core.utils.ObjectUtils;
import com.donkeycode.core.validation.ValidateUtils;
import com.donkeycode.web.HttpCode;
import com.donkeycode.web.HttpServletParamUtils;
import com.donkeycode.web.ResponseCode;
import com.donkeycode.web.WebConstants;
import com.github.pagehelper.PageInfo;


/**
 * 资源Web接口
 *
 *
 * @author ${author}
 * @since  ${date}
 */
@RestController
@RequestMapping(value = "/api/${tableClass.lowerCaseName}")
public class ${tableClass.shortClassName}Controller {

    @Autowired
    ${tableClass.shortClassName}Service ${tableClass.lowerCaseName}Service;

    /**
     * 获取资源
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/page")
    public ResponseEntity<Object> page(HttpServletRequest request) {
        
        PageFilter pageFilter = HttpServletParamUtils.pageFilter(request);
        PageInfo<${tableClass.shortClassName}> page = ${tableClass.lowerCaseName}Service.getPageList(pageFilter);
        Map<String, Object> pageVm = new HashMap<>();
        pageVm.put(WebConstants.PAGE_TOTAL, ObjectUtils.isNotNull(page) ? page.getTotal() : 0);
        pageVm.put(WebConstants.PAGE_DATA, ObjectUtils.isNotNull(page) ? page.getList() : Collections.emptyList());
        pageVm.put(WebConstants.PAGE_SIZE, pageFilter.getPageSize());
        pageVm.put(WebConstants.PAGE_INDEX, pageFilter.getPageNum());
        return ResponseEntity.ok(pageVm);
    }

    /**
     * 获取资源
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseEntity<Object> list(HttpServletRequest request) {
        Map<String, String> params = HttpServletParamUtils.requestToMap(request);
        List<${tableClass.shortClassName}> pageList = ${tableClass.lowerCaseName}Service.getList(params);
        return ResponseEntity.ok(pageList);
    }

    /**
     * 获取资源
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") String id) {
        ValidateUtils.notBlank(id, "资源主键不能为空");
        return ResponseEntity.ok(${tableClass.lowerCaseName}Service.selectByPrimaryKey(id));
    }

    /**
     * 新增资源
     *
     * @param beanJson
     * @return
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseCode delete(@PathVariable("id") String id) {
        ${tableClass.lowerCaseName}Service.deleteByPrimaryKey(id);
        return ResponseCode.returnResponse(HttpCode.OK, "删除资源成功");
    }
}
