package top.moflowerlkh.msg.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class genericUtilTest {

    @Test
    void md5() {
        String s = genericUtil.md5(Arrays.asList(
                "bc30a3c8-b96b-49d2-bb9f-1c28f9408eb3",
                "101",
                "6",
                "3319392274522755259",
                "1649695716802"
        ));
//        ee1b3419344e55f700a8d64729af64fb
//        35f7c53372e243f8353013a75edc2752
        System.out.println(s);
    }
}