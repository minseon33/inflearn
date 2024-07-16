package tobyspring.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {

    Sort sort;
    @BeforeEach
    void beforeEach(){
        sort = new Sort();
    }

    
    @Test
    void sort(){
        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));
        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sort3Items(){
        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("aa","ccc", "b"));
        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa","ccc"));
    }

    @Test
    void sortAlreadySorted(){
        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa","ccc"));
        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa","ccc"));
    }

}
