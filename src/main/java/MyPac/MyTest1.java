package MyPac;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

public class MyTest1 {
    public static void main(String[] args) {
        String ddd = "[{\"lastCustomPhotoTime\":1543555655000,\"id\":\"1-1-1537410953-2\",\"posIndex\":1},{\"lastCustomPhotoTime\":1543555655000,\"id\":\"1-1-1537410953-2\",\"posIndex\":1}]";
        String a = "[[1,2],[3,4],[5,6]]";
        String b = "[1,2,3]";
        String f = "{}";
       Object jj =  JSONObject.parse(f);
       Object bs =  JSONObject.parse(b);
//        Object[] c = JSONObject.parseArray(ddd).toArray();

//        Object[][] sss = new Object[c.length][];
//        int i = 0;
//        for (Object j : c) {
//            Object[] m = JSONObject.parseArray(j.toString()).toArray();
//            sss[i] = m;
//            i++;
//        }

//        JSONObject s = JSONObject.parseArray(ddd).getJSONObject(0);

        System.out.println(ddd);
//
//sss
//        String[][] d = {{"1","2"},{"3","4"}};
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("d",d);
//        String m = jsonObject.toJSONString();
//
//        System.out.println(c);

    }
}
