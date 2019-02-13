package MyPac;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest3 {
    public static void main(String[] args0) throws NoSuchFieldException, IllegalAccessException {
//        TVO tvo = new TVO();
//        TModel tModel = new TModel();
//        List<Integer> id = new ArrayList<>();
//        Map<Integer, Long> name = new HashMap<>();
//        id.add(1);
//        id.add(2);
//        id.add(3);
//        name.put(1, 1L);
//        name.put(2, 2L);
//        name.put(3, 3L);
//        tvo.setId(id);
//        tvo.setName(name);
//        initAvatarBigFieldsModel(tModel, tvo);

        TVO tvo = new TVO();
        TModel tModel = new TModel();
        tModel.setId("[29008]");
        tModel.setName("{1:0,8:1544167184675,10:0,11:1,13:20,14:20}");
        deAvatarBigFieldsModel(tModel, tvo);
    }

    private static void deAvatarBigFieldsModel(TModel tModel, TVO tvo) {
        Field[] modelFields = tModel.getClass().getDeclaredFields();
        for (Field field : modelFields) {
            field.setAccessible(true);
            try {
                Field tvoField = tvo.getClass().getDeclaredField(field.getName());
                tvoField.setAccessible(true);
                Type s = tvoField.getGenericType();
                ParameterizedType mm = (ParameterizedType) s;
                Class<?> t = (Class) mm.getActualTypeArguments()[0];
                if (tvoField.getType() == List.class) {
                    tvoField.set(tvo, JSONArray.parseArray((String) field.get(tModel), t));
                    System.out.println("1");
                } else if (tvoField.getType() == Map.class) {
                    tvoField.set(tvo, JSONObject.parseObject((String) field.get(tModel), Map.class));
                    System.out.println("1");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }


    private static void initAvatarBigFieldsModel(TModel tModel, TVO tvo) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = tModel.getClass().getDeclaredFields();
        for (Field f : fields) {
            Field ff = tvo.getClass().getDeclaredField(f.getName());
            ff.setAccessible(true);
            Object a = ff.get(tvo);
            if (a instanceof List) {
                List c = (List) a;
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(c);
                String d = jsonArray.toJSONString();
                System.out.println(d);
                f.setAccessible(true);
                f.set(tModel, d);
            }

            System.out.println(ff);
        }
    }
}
