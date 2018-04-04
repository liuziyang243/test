package com.crscd.passengerservice.plantest;

import com.crscd.passengerservice.plan.enumtype.LateEarlyReasonEnum;
import com.crscd.passengerservice.plan.enumtype.TrainTypeEnum;
import org.junit.Test;

import java.util.List;

/**
 * @author lzy
 * Date: 2017/8/15
 * Time: 16:32
 */
public class EnumTest {
    @Test
    public void LateReasonTest() {
        LateEarlyReasonEnum lateReason = LateEarlyReasonEnum.MANAGEMENT;
        List<String> reasonList = LateEarlyReasonEnum.getReasonList();
        System.out.println(lateReason.getReason());
        for (String reason : reasonList) {
            System.out.println(reason);
        }
    }

    @Test
    public void EnumDAOTest() {
        TrainTypeEnum typeEnum = TrainTypeEnum.HIGH_SPEED;
        System.out.println(typeEnum.toString());
    }

}
