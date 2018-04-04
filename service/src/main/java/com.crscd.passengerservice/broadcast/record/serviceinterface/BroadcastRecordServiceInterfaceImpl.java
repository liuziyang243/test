package com.crscd.passengerservice.broadcast.record.serviceinterface;

import com.crscd.passengerservice.broadcast.record.business.BroadcastRecordManager;
import com.crscd.passengerservice.broadcast.record.domainobject.BroadcastRecord;
import com.crscd.passengerservice.broadcast.record.dto.BroadcastRecordDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzy
 * Date: 2017/9/14
 * Time: 14:40
 */
public class BroadcastRecordServiceInterfaceImpl implements BroadcastRecordServiceInterface {
    private BroadcastRecordManager manager;

    public void setManager(BroadcastRecordManager manager) {
        this.manager = manager;
    }

    @Override
    public List<BroadcastRecordDTO> getBroadcastRecord(String stationName, String trainNum, String startDate, String endDate) {
        List<BroadcastRecord> records = manager.getBroadcastRecordByStationAndDate(stationName, trainNum, startDate, endDate);
        List<BroadcastRecordDTO> recordDTOList = new ArrayList<>();
        for (BroadcastRecord record : records) {
            recordDTOList.add(getDTOFromDO(record));
        }
        return recordDTOList;
    }

    private BroadcastRecordDTO getDTOFromDO(BroadcastRecord record) {
        MapperFactory mapperFactory = getMapperFactory();
        MapperFacade mapper = mapperFactory.getMapperFacade();
        return mapper.map(record, BroadcastRecordDTO.class);
    }

    private MapperFactory getMapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(BroadcastRecord.class, BroadcastRecordDTO.class)
                .fieldMap("callDriverTime", "actualBroadcastTime")
                .add().byDefault().register();

        return mapperFactory;
    }
}
