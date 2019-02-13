package MyPac;

import tk.mybatis.mapper.mapperhelper.EntityHelper;

public class Mytest2 {
    public static void main(String[] args0){
       String a = EntityHelper.getSelectColumns(DFTest.class);
       System.out.println(a);
    }
}
