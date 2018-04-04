-- init data for station config
TRUNCATE TABLE BroadcastTemplateGroup;
TRUNCATE TABLE BroadcastTemplate;
TRUNCATE TABLE NormalBroadcastContent;
-- init data for station config
TRUNCATE TABLE STATIONREGIONCONFIG;
TRUNCATE TABLE TRACKPLATFORMMAP;
TRUNCATE TABLE BROADCASTAREA;
TRUNCATE TABLE  ScreenConfig;

drop procedure if exists proc_tmp;
DELIMITER ;;
create procedure proc_tmp()
  BEGIN
    declare done int default 0;  /*用于判断是否结束循环*/
    declare station_Name varchar(20);
    declare v_index int default 201;


    /*定义游标*/
    declare cur cursor for SELECT stationName FROM STATIONCONFIG;
    /*定义 设置循环结束标识done值怎么改变 的逻辑*/
    declare continue handler for not FOUND set done = 1; /*done = true;亦可*/

    open cur;  /*打开游标*/

    /* 循环开始 */
    REPEAT
      fetch cur into station_Name;
      if not done THEN  /*数值为非0，MySQL认为是true*/
      -- broadcast template group
      INSERT INTO BroadcastTemplateGroup (templateGroupName, stationName, broadcastKind, revisability)
        VALUES ('start', station_Name, 'ARRIVE_DEPARTURE', 1);
        INSERT INTO BroadcastTemplateGroup (templateGroupName, stationName, broadcastKind, revisability)
        VALUES ('pass', station_Name, 'ARRIVE_DEPARTURE', 1);
        INSERT INTO BroadcastTemplateGroup (templateGroupName, stationName, broadcastKind, revisability)
        VALUES ('final', station_Name, 'ARRIVE_DEPARTURE', 1);
        -- BroadcastTemplate
        INSERT INTO BroadcastTemplate (templateGroupName, broadcastKind, stationName, baseTime, broadcastContentName, operationMode, priorityLevel, broadcastArea, firstRegion, timeOffset)
        VALUES
          ('start', 'ARRIVE_DEPARTURE', station_Name, 'DEPARTURE_TIME', 'startcontent', 'AUTO', 6,
           '["wait zone","platform"]',
           '[]', -20);
        INSERT INTO BroadcastTemplate (templateGroupName, broadcastKind, stationName, baseTime, broadcastContentName, operationMode, priorityLevel, broadcastArea, firstRegion, timeOffset)
        VALUES
          ('pass', 'ARRIVE_DEPARTURE', station_Name, 'DEPARTURE_TIME', 'passcontent', 'AUTO', 6,
           '["wait zone","platform"]',
           '[]', -20);
        INSERT INTO BroadcastTemplate (templateGroupName, broadcastKind, stationName, baseTime, broadcastContentName, operationMode, priorityLevel, broadcastArea, firstRegion, timeOffset)
        VALUES
          ('final', 'ARRIVE_DEPARTURE', station_Name, 'ARRIVE_TIME', 'finalcontent', 'AUTO', 6,
           '["wait zone","platform"]',
           '[]', 0);

        -- broadcast area
        INSERT INTO NormalBroadcastContent (stationName, contentName, broadcastKind, contentInLocalLan, contentInEng)
        VALUES (station_Name, 'startcontent', 'ARRIVE_DEPARTURE', 'test', 'test');
        INSERT INTO NormalBroadcastContent (stationName, contentName, broadcastKind, contentInLocalLan, contentInEng)
        VALUES (station_Name, 'passcontent', 'ARRIVE_DEPARTURE', 'test', 'test');
        INSERT INTO NormalBroadcastContent (stationName, contentName, broadcastKind, contentInLocalLan, contentInEng)
        VALUES (station_Name, 'finalcontent', 'ARRIVE_DEPARTURE', 'test', 'test');

       -- screen Config
        INSERT INTO ScreenConfig (screenID, screenName, ScreenType, screenWidth, screenHeight, screenColor, screenControllerID, controllerType, stationName, secondaryRegion)
        VALUES
          (v_index, 'testScreen1', 'PLATFORM_SCREEN', 1024, 512, 'FULL_COLOR', v_index, 'ASYNC_CONTROLLER', station_Name, 'Platform 1');
      set v_index = v_index + 1;
      INSERT INTO ScreenConfig (screenID, screenName, ScreenType, screenWidth, screenHeight, screenColor, screenControllerID, controllerType, stationName, secondaryRegion)
      VALUES
        (v_index, 'testScreen2', 'ENTRANCE_SCREEN', 1024, 512, 'FULL_COLOR', v_index, 'SYNC_CONTROLLER', station_Name, 'Wait Zone 1');
      set v_index = v_index + 1;
    INSERT INTO ScreenConfig (screenID, screenName, ScreenType, screenWidth, screenHeight, screenColor, screenControllerID, controllerType, stationName, secondaryRegion)
    VALUES (v_index, 'testScreen3', 'TICKET_INFO_SCREEN', 1024, 512, 'FULL_COLOR', v_index, 'SYNC_CONTROLLER', station_Name,
            'Ticket Room 1');
    set v_index = v_index + 1;

      -- region
      INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
      VALUES ('Wait Zone 1', 'WAIT_ZONE', station_Name, '["wait zone"]');
      INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
      VALUES ('Wait Zone 2', 'WAIT_ZONE', station_Name, '["wait zone"]');
      INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
      VALUES ('Etrance Port 1', 'STATION_ENTRANCE_PORT', station_Name, '["entrance port"]');
      INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
      VALUES ('Exit Port 1', 'STATION_EXIT_PORT', station_Name, '["exit port"]');
      INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
      VALUES ('Aboard Check Port 1', 'ABOARD_CHECK_GATE', station_Name, '["check port"]');
      INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
      VALUES ('Exit Check Port 1', 'EXIT_CHECK_GATE', station_Name, '[]');
      -- track platform map
      INSERT INTO TrackPlatformMap (platform, trackNum, stationName) VALUES ('Platform 1', 1, station_Name);
      INSERT INTO TrackPlatformMap (platform, trackNum, stationName) VALUES ('Platform 2', 2, station_Name);
      -- broadcast area
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'platform', 1, '["Platform 1","Platform 2"]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'wait zone', 2, '["Wait Zone 1","Wait Zone 2"]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'entrance port', 3, '["Etrance Port 1"]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'exit port', 4, '["Exit Port 1"]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'check port', 5, '["Aboard Check Port 1"]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'ticket hall', 6, '[]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'platform bridge', 7, '[]');
      INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
      VALUES (station_Name, 'baggage room', 7, '[]');

      end if;

    until done end repeat;

    close cur;  /*关闭游标*/
  END
/* 循环结束 */
;;
DELIMITER ;
call proc_tmp();
drop procedure proc_tmp;  /*删除临时存储过程*/