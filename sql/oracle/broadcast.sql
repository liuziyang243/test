-- BroadcastTemplateGroup
CREATE TABLE BroadcastTemplateGroup (
  id                NUMBER(11) NOT NULL PRIMARY KEY,
  templateGroupName VARCHAR2(100),
  stationName       VARCHAR2(50),
  broadcastKind     VARCHAR2(20),
  revisability      NUMBER(1)
);

CREATE SEQUENCE BTGroup_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER BTGroup_Trigger
BEFORE
INSERT ON BroadcastTemplateGroup
FOR EACH ROW
  BEGIN
    SELECT BTGroup_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- BroadcastTemplate
CREATE TABLE BroadcastTemplate (
  id                   NUMBER(11) NOT NULL PRIMARY KEY,
  templateGroupName    VARCHAR2(100),
  stationName          VARCHAR2(50),
  broadcastKind        VARCHAR2(20),
  baseTime             VARCHAR2(20),
  broadcastContentName VARCHAR2(50),
  operationMode        VARCHAR2(20),
  priorityLevel        NUMBER(11),
  broadcastArea        VARCHAR2(300),
  firstRegion          VARCHAR2(100),
  timeOffset           NUMBER(11)
);

CREATE SEQUENCE BT_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER BT_Trigger
BEFORE
INSERT ON BroadcastTemplate
FOR EACH ROW
  BEGIN
    SELECT BT_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- NormalBroadcastContent
CREATE TABLE NormalBroadcastContent (
  id                NUMBER(11) NOT NULL PRIMARY KEY,
  contentName       VARCHAR2(100),
  stationName       VARCHAR2(50),
  broadcastKind     VARCHAR2(20),
  contentInLocalLan VARCHAR2(2000),
  contentInEng      VARCHAR2(2000)
);

CREATE SEQUENCE NBContent_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER NBContent_Trigger
BEFORE
INSERT ON NormalBroadcastContent
FOR EACH ROW
  BEGIN
    SELECT NBContent_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;


-- SpecialBroadcastContent
CREATE TABLE SpecialBroadcastContent (
  id               NUMBER(11) NOT NULL PRIMARY KEY,
  contentName      VARCHAR2(100),
  stationName      VARCHAR2(50),
  broadcastKind    VARCHAR2(20),
  broadcastContent VARCHAR2(2000)
);

CREATE SEQUENCE SBContent_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER SBContent_Trigger
BEFORE
INSERT ON SpecialBroadcastContent
FOR EACH ROW
  BEGIN
    SELECT SBContent_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;


-- init data for station config
TRUNCATE TABLE BroadcastTemplateGroup;
TRUNCATE TABLE BroadcastTemplate;

BEGIN
  FOR station IN (SELECT *
                  FROM STATIONCONFIG) LOOP
    -- BroadcastTemplateGroup
    INSERT INTO BroadcastTemplateGroup (templateGroupName, stationName, broadcastKind, revisability)
    VALUES ('start', station.STATIONNAME, 'ARRIVE_DEPARTURE', 1);
    INSERT INTO BroadcastTemplateGroup (templateGroupName, stationName, broadcastKind, revisability)
    VALUES ('pass', station.STATIONNAME, 'ARRIVE_DEPARTURE', 1);
    INSERT INTO BroadcastTemplateGroup (templateGroupName, stationName, broadcastKind, revisability)
    VALUES ('final', station.STATIONNAME, 'ARRIVE_DEPARTURE', 1);

    COMMIT;
    -- BroadcastTemplate
    INSERT INTO BroadcastTemplate (templateGroupName, broadcastKind, stationName, baseTime, broadcastContentName, operationMode, priorityLevel, broadcastArea, firstRegion, timeOffset)
    VALUES
      ('start', 'ARRIVE_DEPARTURE', station.STATIONNAME, 'DEPARTURE_TIME', 'startcontent', 'AUTO', 6,
       '["wait zone","platform"]',
       '[]', -20);
    INSERT INTO BroadcastTemplate (templateGroupName, broadcastKind, stationName, baseTime, broadcastContentName, operationMode, priorityLevel, broadcastArea, firstRegion, timeOffset)
    VALUES
      ('pass', 'ARRIVE_DEPARTURE', station.STATIONNAME, 'DEPARTURE_TIME', 'passcontent', 'AUTO', 6,
       '["wait zone","platform"]',
       '[]', -20);
    INSERT INTO BroadcastTemplate (templateGroupName, broadcastKind, stationName, baseTime, broadcastContentName, operationMode, priorityLevel, broadcastArea, firstRegion, timeOffset)
    VALUES
      ('final', 'ARRIVE_DEPARTURE', station.STATIONNAME, 'ARRIVE_TIME', 'finalcontent', 'AUTO', 6,
       '["wait zone","platform"]',
       '[]', 0);

    COMMIT;
    -- broadcast area
    INSERT INTO NormalBroadcastContent (stationName, contentName, broadcastKind, contentInLocalLan, contentInEng)
    VALUES (station.STATIONNAME, 'startcontent', 'ARRIVE_DEPARTURE', 'test', 'test');
    INSERT INTO NormalBroadcastContent (stationName, contentName, broadcastKind, contentInLocalLan, contentInEng)
    VALUES (station.STATIONNAME, 'passcontent', 'ARRIVE_DEPARTURE', 'test', 'test');
    INSERT INTO NormalBroadcastContent (stationName, contentName, broadcastKind, contentInLocalLan, contentInEng)
    VALUES (station.STATIONNAME, 'finalcontent', 'ARRIVE_DEPARTURE', 'test', 'test');

    COMMIT;
  END LOOP;
END;

-- Broadcast record
CREATE TABLE BroadcastRecord (
  id               NUMBER(11) NOT NULL PRIMARY KEY,
  recordKey        VARCHAR2(150),
  trainNum         VARCHAR2(30),
  stationName      VARCHAR2(30),
  broadcastKind    VARCHAR2(20),
  broadcastMode    VARCHAR2(20),
  callDriverTime   VARCHAR2(30),
  broadcastArea    VARCHAR2(200),
  operationState   VARCHAR2(20),
  recordTime       VARCHAR2(30),
  priorityLevel    NUMBER(5),
  broadcastContent VARCHAR2(2000)
);

CREATE SEQUENCE BroadcastRecord_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER BroadcastRecord_Trigger
BEFORE
INSERT ON BroadcastRecord
FOR EACH ROW
  BEGIN
    SELECT BroadcastRecord_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;