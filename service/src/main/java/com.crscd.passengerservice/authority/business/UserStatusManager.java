package com.crscd.passengerservice.authority.business;

import com.crscd.framework.core.ConfigHelper;
import com.crscd.passengerservice.authority.domainobject.UserStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zs
 * @date 2017/8/29
 */
public class UserStatusManager {
    private static Map<String, UserStatus> userIpAddrStatus = new ConcurrentHashMap<>();

    //删除一定时间内未更新的用户
    public static void updateStatus() {
        if (userIpAddrStatus.size() != 0) {
            Iterator<Map.Entry<String, UserStatus>> entries = userIpAddrStatus.entrySet().iterator();
            LocalDateTime nowTime = LocalDateTime.now();
            while (entries.hasNext()) {
                UserStatus userStatus = entries.next().getValue();
                LocalDateTime lastUpdateTime = userStatus.getDateTime();
                long minutesDiff = ChronoUnit.MINUTES.between(lastUpdateTime, nowTime);
                if (minutesDiff >= ConfigHelper.getInt("TimeOutOfService", 1)) {
                    entries.remove();
                }
            }
        }
    }

    public UserStatus getStatus(String userName) {
        return userIpAddrStatus.get(userName);
    }

    public void putStatus(String userName, UserStatus userStatus) {
        userIpAddrStatus.put(userName, userStatus);
    }

    public void removeStatus(String userName) {
        userIpAddrStatus.remove(userName);
    }
}
