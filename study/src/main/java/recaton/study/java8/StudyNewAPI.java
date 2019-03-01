package recaton.study.java8;

import java.util.stream.Stream;

public class StudyNewAPI {

    // Stream 处理原始类型数组和包装类型数组是不一样的
    public static void test(){
        int[] primitiveArr = {1, 2, 3, 4, 5};
        Stream.of(primitiveArr).forEach(System.out::println);
        Integer[] warpArr = {1, 2, 3, 4, 5};
        Stream.of(warpArr).forEach(System.out::println);
    }

}
