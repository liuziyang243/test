package com.crscd.passengerservice.display.ScreenControlServer;

import com.crscd.passengerservice.context.ContextHelper;
import com.crscd.passengerservice.display.screencontrolserver.business.Material;
import com.crscd.passengerservice.display.screencontrolserver.domainobject.MaterialInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuishiqing on 2017/12/22.
 */
public class MaterialTest {
    Material material = ContextHelper.getMaterial();

    @Test
    public void materialSendTest() {
        MaterialInfo materialInfo = new MaterialInfo();
        materialInfo.setName("test.doc");
        materialInfo.setSize("23489");
        materialInfo.setLastedittime("2016-06-13 10:10:11");
        List<MaterialInfo> materialInfos = new ArrayList<>();
        materialInfos.add(materialInfo);

        boolean result = material.materialSend(materialInfos, "10.2.34.134");
    }

    @Test
    public void materialAllInfoGetTest() {
        List<MaterialInfo> materialInfos = material.materialAllInfoGet("10.2.34.134");
        for (MaterialInfo info : materialInfos) {
            System.out.print(info.getName());
        }
    }


}
