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
@Table(name = "`USER_INFO`")
public class UserInfo {
    @Id
    @Column(name = "`ID`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`USERNAME`")
    private String username;

    @Column(name = "`PASSWORD`")
    private String password;

    @Column(name = "`USERTYPE`")
    private String usertype;

    @Column(name = "`ENABLED`")
    private Integer enabled;

    @Column(name = "`REALNAME`")
    private String realname;

    @Column(name = "`QQ`")
    private String qq;

    @Column(name = "`EMAIL`")
    private String email;

    @Column(name = "`ADDRESS`")
    private String address;

    @Column(name = "`TEL`")
    private String tel;

    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String USERTYPE = "usertype";

    public static final String ENABLED = "enabled";

    public static final String REALNAME = "realname";

    public static final String QQ = "qq";

    public static final String EMAIL = "email";

    public static final String ADDRESS = "address";

    public static final String TEL = "tel";
}