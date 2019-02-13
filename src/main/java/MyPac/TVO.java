package MyPac;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TVO {

    private List<Integer> id;
    private Map<Integer, Long> name;
}
