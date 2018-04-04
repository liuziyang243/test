package com.crscd.passengerservice.multimedia.util;

/**
 * Created by cuishiqing on 2017/9/20.
 */
public class SizeConverterUtil {

    /**
     * @param units 支持KB、MB、GB、TB
     * @param size
     * @return
     * @see "文件大小转换为bit"
     */
    public static long ConvertToBit(String units, String size) {
        long size_Long = Long.parseLong(size);
        long size_B = 0;
        switch (units) {
            case "KB":
                size_B = size_Long * 1024;
                break;
            case "MB":
                size_B = size_Long * 1024 * 1024;
                break;
            case "GB":
                size_B = size_Long * 1024 * 1024 * 1024;
                break;
            case "TB":
                size_B = size_Long * 1024 * 1024 * 1024 * 1024;
                break;
            default:
                break;
        }
        return size_B;
    }
}
