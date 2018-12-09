package jevilmod;

import java.util.function.Consumer;

public class MezUtils {
    public static void repeat(int count, Consumer<Integer> func){
        for(int i = 0; i < count; i++){func.accept(i);}
    }
}
