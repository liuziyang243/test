package com.crscd.passengerservice.authority.util;

import com.crscd.passengerservice.authority.po.UserInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class PasswordHelper {

    private static final int HASH_ITERATIONS = 2;
    private static String algorithmName = "md5";

    // 单向加密是为了防止数据库泄露，此时黑客仍然无法得到用户的密码信息
    public static void encryptPassword(UserInfo user) {

        user.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();

        user.setPassword(newPassword);
    }

    public static boolean verifyPassword(UserInfo user, String password) {
        String logInPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
        return user.getPassword().equals(logInPassword);
    }
}
