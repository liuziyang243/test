package com.crscd.passengerservice.result.errorinfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * <p>
 * Create Time: 2017/11/3 9:19
 * @version v1.00
 */
public class ErrorCodeReasonMap {
    private static Map<Integer, String> statusMessageList = new HashMap<>();

    static {
        // 通用
        statusMessageList.put(200, ErrorReason.STATUS_CODE_200);
        statusMessageList.put(-2, ErrorReason.STATUS_CODE_MINUS2);
        statusMessageList.put(30, ErrorReason.STATUS_CODE_30);


        // 版本号
        statusMessageList.put(50, ErrorReason.STATUS_CODE_50);
        statusMessageList.put(51, ErrorReason.STATUS_CODE_51);

        // 用户登录
        statusMessageList.put(60, ErrorReason.STATUS_CODE_60);
        statusMessageList.put(61, ErrorReason.STATUS_CODE_61);
        statusMessageList.put(62, ErrorReason.STATUS_CODE_62);
        statusMessageList.put(63, ErrorReason.STATUS_CODE_63);
        statusMessageList.put(63, ErrorReason.STATUS_CODE_64);

        // 用户权限相关
        statusMessageList.put(100, ErrorReason.STATUS_CODE_100);
        statusMessageList.put(101, ErrorReason.STATUS_CODE_101);
        statusMessageList.put(102, ErrorReason.STATUS_CODE_102);
        statusMessageList.put(102, ErrorReason.STATUS_CODE_103);

        // 计划操作相关
        statusMessageList.put(301, ErrorReason.STATUS_CODE_301);
        statusMessageList.put(302, ErrorReason.STATUS_CODE_302);
        statusMessageList.put(303, ErrorReason.STATUS_CODE_303);
        statusMessageList.put(304, ErrorReason.STATUS_CODE_304);
        statusMessageList.put(305, ErrorReason.STATUS_CODE_305);
        statusMessageList.put(306, ErrorReason.STATUS_CODE_306);

        // 数据库
        statusMessageList.put(2001, ErrorReason.STATUS_CODE_2001);
        statusMessageList.put(2002, ErrorReason.STATUS_CODE_2002);
        statusMessageList.put(2003, ErrorReason.STATUS_CODE_2003);
        statusMessageList.put(2011, ErrorReason.STATUS_CODE_2011);
        statusMessageList.put(2012, ErrorReason.STATUS_CODE_2012);
        statusMessageList.put(2013, ErrorReason.STATUS_CODE_2013);

        // 广播计划执行
        statusMessageList.put(1101, ErrorReason.STATUS_CODE_1101);
        statusMessageList.put(1102, ErrorReason.STATUS_CODE_1102);
        statusMessageList.put(1103, ErrorReason.STATUS_CODE_1103);
        statusMessageList.put(1104, ErrorReason.STATUS_CODE_1104);
        statusMessageList.put(1105, ErrorReason.STATUS_CODE_1105);
        statusMessageList.put(1106, ErrorReason.STATUS_CODE_1106);

        // cctv
        statusMessageList.put(1201, ErrorReason.STATUS_CODE_1201);
        statusMessageList.put(1202, ErrorReason.STATUS_CODE_1202);

        // warning
        statusMessageList.put(1301, ErrorReason.STATUS_CODE_1301);

    }

    public static String getReasonByCode(Integer errorCode) {
        return statusMessageList.getOrDefault(errorCode, "Unknown error");
    }
}
