package com.crscd.framework.utiltest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuziyang
 * Create date: 2017/8/17
 */
public class MapTest {
    @Test
    public void testRemoveUnexitKey() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("t", "t");
        testMap.put("t1", "t2");

        testMap.remove("k");

        System.out.println(testMap.toString());
    }
}
