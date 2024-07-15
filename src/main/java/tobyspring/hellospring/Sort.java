package tobyspring.hellospring;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {

    public static void main(String[] args) {
        List<String> scores = Arrays.asList("z","x","Spring","java");
        // Comparator 인터페이스 한번 살펴보자.
        Collections.sort(scores, (o1, o2) -> o1.length()-o2.length());
        scores.forEach(System.out::println);
    }
}
