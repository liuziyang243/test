package com.crscd.passengerservice.display.ScreenControlServer;

import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.business.Authentication;
import org.junit.Test;

/**
 * Created by cuishiqing on 2017/12/18.
 */
public class AuthenticationTest {
    Authentication authentication = ContextHelper.getAuthentication();

    @Test
    public void loginTest() {
        authentication.login("10.2.34.134");
        String tokenId = authentication.getTokenByIp("10.2.34.134");
        System.out.print(tokenId);
    }
}
