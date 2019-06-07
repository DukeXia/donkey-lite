package commons.test;

import com.donkeycode.core.utils.StringEncaseUtils;

public class HtmlUtilTest {
    public static void main(String[] args) {

       String html="http://localhost:9020/api/role/page?pageNo=1&pageSize=10&sortField=roleName&sortOrder=descend&orderBys[]=roleName:descend";
        System.out.println(StringEncaseUtils.escapeHtml4(html));

    }


}
