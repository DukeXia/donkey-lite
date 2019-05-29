package ${package};

import ${tableClass.fullClassName};
import ${servicePackage}.${tableClass.shortClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author donkey generator
 * @since  0.0.1
 */
@RestController
@RequestMapping(value = "/api/${tableClass.lowerCaseName}")
public class ${tableClass.shortClassName}Controller {

    @Autowired
    private ${tableClass.shortClassName}Service ${tableClass.lowerCaseName}Service;

    @RequestMapping(value = {"/list", ""}, method = RequestMethod.GET)
    public Object list() {
        List<${tableClass.shortClassName}> ${tableClass.lowerCaseName}s = ${tableClass.lowerCaseName}Service.findAllList();
        return ${tableClass.lowerCaseName}s;
    }

    @RequestMapping(value = {"/get"}, method = RequestMethod.GET)
    public Object get(@RequestParam String id) {
        ${tableClass.shortClassName} ${tableClass.lowerCaseName} = ${tableClass.lowerCaseName}Service.get(id);
        return ${tableClass.lowerCaseName};
    }

    @PostMapping(value = "/insert")
    public String insert(@RequestBody ${tableClass.shortClassName} ${tableClass.lowerCaseName}) {
        if (${tableClass.lowerCaseName}Service.insert(${tableClass.lowerCaseName}) > 0) {
            return "success";
        } else {
            return "failed";
        }
    }

    @RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
    public ResponseEntity<LinkedInfo> insertBatch(
        @RequestBody List<${tableClass.shortClassName}> ${tableClass.lowerCaseName}s) {

        ${tableClass.lowerCaseName}Service.insertBatch(${tableClass.lowerCaseName}s);
        if (${tableClass.lowerCaseName}Service.insertBatch(${tableClass.lowerCaseName}s) > 0) {
            return "success";
        } else {
            return "failed";
        }
        new ResponseEntity(linkedInfo, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody ${tableClass.shortClassName} ${tableClass.lowerCaseName}) {
        if (${tableClass.lowerCaseName}Service.update(${tableClass.lowerCaseName}) > 0) {
            return "success";
        } else {
            return "failed";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody ${tableClass.shortClassName} ${tableClass.lowerCaseName}) {
        if (${tableClass.lowerCaseName}Service.delete(${tableClass.lowerCaseName}) > 0) {
            return "success";
        } else {
            return "failed";
        }
    }
}
