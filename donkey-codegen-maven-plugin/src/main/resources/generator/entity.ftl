package ${targetPackage};


import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.donkeycode.core.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 *
 * @author ${author}
 * @since  ${date}
 */
@Setter
@Getter
@SuppressWarnings("serial")
@Table(name ="${tableClass.tableName}")
public class ${tableClass.shortClassName} extends BaseEntity {


    ${Properties}

    public ${tableClass.shortClassName}(){
    }
}