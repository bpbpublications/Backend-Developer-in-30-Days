package hello;

import com.google.common.collect.ImmutableList;
import java.util.List;

public class Application {
 
    public static void main(String [] args) {
        List<String> items = ImmutableList.of("cheese", "tomato sauce", "sourdough");

        items.stream()
            .forEach(System.out::println);
    }
}
