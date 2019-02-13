package SpringTest.Utils;

import javafx.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AspectUtil {
    /**
     * key:切面 value-key:切点 value-value:前置通知 后置通知
     */
    public static Map<String, Map<String, Pair<List<String>, List<String>>>> aspectMap = Collections.synchronizedMap(new HashMap<>());
}
