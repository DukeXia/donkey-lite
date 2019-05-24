package commons.test;

import commons.test.entity.Bean2;
import commons.test.entity.BeanA;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.ArrayList;
import java.util.List;

public class ObjectFieldUtilTest {
    public static void main(String[] args) {



        BeanA beanA = new BeanA();
        beanA.setAge(1);
        beanA.setEmail("xueyanjun@163.com");
        beanA.setName("xueyanjun");


        try {
            System.out.println(FieldUtils.readField(beanA, "email",true).toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
