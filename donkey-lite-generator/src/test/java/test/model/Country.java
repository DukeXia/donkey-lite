package test.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "`COUNTRY`")
public class Country {
    @Id
    @Column(name = "`ID`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`COUNTRYNAME`")
    private String countryname;

    @Column(name = "`COUNTRYCODE`")
    private String countrycode;

    @Column(name = "`VERSION`")
    private Integer version;

    public static final String ID = "id";

    public static final String COUNTRYNAME = "countryname";

    public static final String COUNTRYCODE = "countrycode";

    public static final String VERSION = "version";
}