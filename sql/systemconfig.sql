-- broadcast area config
CREATE TABLE BroadcastArea (
  id                  NUMBER(11) NOT NULL PRIMARY KEY,
  broadcastZoneName   VARCHAR2(50),
  broadcastZoneID     NUMBER(11),
  secondaryRegionList VARCHAR2(300),
  stationName         VARCHAR2(30)
);

CREATE SEQUENCE BroadcastArea_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER BroadcastArea_Trigger
BEFORE
INSERT ON BroadcastArea
FOR EACH ROW
  BEGIN
    SELECT BroadcastArea_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- region config
CREATE TABLE StationRegionConfig (
  id                NUMBER(11) NOT NULL PRIMARY KEY,
  secondaryRegion   VARCHAR2(50),
  firstRegion       VARCHAR2(50),
  stationName       VARCHAR2(30),
  broadcastAreaList VARCHAR2(300)
);

CREATE SEQUENCE SRC_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER SRC_Trigger
BEFORE
INSERT ON StationRegionConfig
FOR EACH ROW
  BEGIN
    SELECT SRC_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- track platform map config
CREATE TABLE TrackPlatformMap (
  id          NUMBER(11) NOT NULL PRIMARY KEY,
  platform    VARCHAR2(50),
  trackNum    NUMBER(11),
  stationName VARCHAR2(30)
);

CREATE SEQUENCE TrackPlatformMap_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER TrackPlatformMap_Trigger
BEFORE
INSERT ON TrackPlatformMap
FOR EACH ROW
  BEGIN
    SELECT TrackPlatformMap_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- init data for station config
TRUNCATE TABLE STATIONREGIONCONFIG;
TRUNCATE TABLE TRACKPLATFORMMAP;
TRUNCATE TABLE BROADCASTAREA;

BEGIN
  FOR station IN (SELECT *
                  FROM STATIONCONFIG) LOOP
    -- region
    INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
    VALUES ('Wait Zone 1', 'WAIT_ZONE', station.STATIONNAME, '["wait zone"]');
    INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
    VALUES ('Wait Zone 2', 'WAIT_ZONE', station.STATIONNAME, '["wait zone"]');
    INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
    VALUES ('Etrance Port 1', 'STATION_ENTRANCE_PORT', station.STATIONNAME, '["entrance port"]');
    INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
    VALUES ('Exit Port 1', 'STATION_EXIT_PORT', station.STATIONNAME, '["exit port"]');
    INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
    VALUES ('Aboard Check Port 1', 'ABOARD_CHECK_GATE', station.STATIONNAME, '["check port"]');
    INSERT INTO StationRegionConfig (secondaryRegion, firstRegion, stationName, broadcastAreaList)
    VALUES ('Exit Check Port 1', 'EXIT_CHECK_GATE', station.STATIONNAME, '[]');
    COMMIT;
    -- track platform map
    INSERT INTO TrackPlatformMap (platform, trackNum, stationName) VALUES ('Platform 1', 1, station.STATIONNAME);
    INSERT INTO TrackPlatformMap (platform, trackNum, stationName) VALUES ('Platform 2', 2, station.STATIONNAME);
    COMMIT;
    -- broadcast area
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'platform', 1, '["Platform 1","Platform 2"]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'wait zone', 2, '["Wait Zone 1","Wait Zone 2"]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'entrance port', 3, '["Etrance Port 1"]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'exit port', 4, '["Exit Port 1"]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'check port', 5, '["Aboard Check Port 1"]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'ticket hall', 6, '[]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'platform bridge', 7, '[]');
    INSERT INTO BroadcastArea (stationName, broadcastZoneName, broadcastZoneID, secondaryRegionList)
    VALUES (station.STATIONNAME, 'baggage room', 7, '[]');
    COMMIT;
  END LOOP;
END;

-- screen map config
CREATE TABLE ScreenConfig (
  screenID           NUMBER(11) NOT NULL PRIMARY KEY,
  screenName         VARCHAR2(50),
  ScreenType         VARCHAR2(30),
  screenWidth        NUMBER(11),
  screenHeight       NUMBER(11),
  screenColor        VARCHAR2(30),
  screenIp           VARCHAR2(30),
  controllerType     VARCHAR2(30),
  serverIp           VARCHAR2(30),
  stationName        VARCHAR2(30),
  secondaryRegion    VARCHAR2(30)
);

-- screen control server config
CREATE TABLE ScreenCtrlServerConfig (
  id                 NUMBER(11) NOT NULL PRIMARY KEY,
  stationName        VARCHAR2(30),
  ip                 VARCHAR2(30),
  port               VARCHAR2(10)
);

CREATE SEQUENCE ScreenCtrlServerConfig_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER ScreenCtrlServerConfig_Trigger
BEFORE
INSERT ON ScreenCtrlServerConfig
FOR EACH ROW
  BEGIN
    SELECT ScreenCtrlServerConfig_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- Init some data
DECLARE
  v_index NUMBER(11);
BEGIN
  v_index := 201;
  FOR station IN (SELECT *
                  FROM STATIONCONFIG) LOOP

    INSERT INTO ScreenConfig (screenID, screenName, ScreenType, screenWidth, screenHeight, screenColor, screenControllerID, controllerType, stationName, secondaryRegion)
    VALUES
      (v_index, 'testScreen1', 'PLATFORM_SCREEN', 1024, 512, 'FULL_COLOR', v_index, 'ASYNC_CONTROLLER', station.STATIONNAME,
       'Platform 1');
    v_index := v_index + 1;
    INSERT INTO ScreenConfig (screenID, screenName, ScreenType, screenWidth, screenHeight, screenColor, screenControllerID, controllerType, stationName, secondaryRegion)
    VALUES
      (v_index, 'testScreen2', 'ENTRANCE_SCREEN', 1024, 512, 'FULL_COLOR', v_index, 'SYNC_CONTROLLER', station.STATIONNAME,
       'Wait Zone 1');
    v_index := v_index + 1;
    INSERT INTO ScreenConfig (screenID, screenName, ScreenType, screenWidth, screenHeight, screenColor, screenControllerID, controllerType, stationName, secondaryRegion)
    VALUES (v_index, 'testScreen3', 'TICKET_INFO_SCREEN', 1024, 512, 'FULL_COLOR', v_index, 'SYNC_CONTROLLER', station.STATIONNAME,
            'Ticket Room 1');
    v_index := v_index + 1;
    COMMIT;
  END LOOP;
END;
