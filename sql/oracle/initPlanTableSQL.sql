-- 列车时刻表-车次信息
-- 判断表是否存在，如果存在则删除
DECLARE
  num NUMBER;
BEGIN
  SELECT count(1)
  INTO num
  FROM all_tables
  WHERE TABLE_NAME = 'basicplan' AND OWNER = 'LZY';
  IF num = 1
  THEN
    EXECUTE IMMEDIATE 'DROP TABLE basicplan';
  END IF;
END;

CREATE TABLE basicplan (
  id                NUMBER(11) NOT NULL PRIMARY KEY,
  generateTimestamp VARCHAR2(20),
  trainNum          VARCHAR2(50) UNIQUE,
  uniqueTrainNum    VARCHAR2(50),
  trainType         VARCHAR2(30),
  startStation      VARCHAR2(50),
  finalStation      VARCHAR2(50),
  validPeriodStart  VARCHAR2(20),
  validPeriodEnd    VARCHAR2(20),
  trainSuspendStart VARCHAR2(20),
  trainSuspendEnd   VARCHAR2(20)
);

CREATE SEQUENCE BASICPLAN_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER BASICPLAN_Sequence
BEFORE
INSERT ON basicplan
FOR EACH ROW
  BEGIN
    SELECT BASICPLAN_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;


-- 列车时刻表-车站信息
-- 判断表是否存在，如果存在则删除
DECLARE
  num NUMBER;
BEGIN
  SELECT count(1)
  INTO num
  FROM all_tables
  WHERE TABLE_NAME = 'basictrainstationinfo' AND OWNER = 'LZY';
  IF num = 1
  THEN
    EXECUTE IMMEDIATE 'DROP TABLE basictrainstationinfo';
  END IF;
END;

CREATE TABLE basictrainstationinfo (
  id                  NUMBER(11) NOT NULL PRIMARY KEY,
  trainNum            VARCHAR2(50),
  stationName         VARCHAR2(50),
  startStation        VARCHAR2(50),
  finalStation        VARCHAR2(50),
  planedDepartureTime VARCHAR2(20),
  planedArriveTime    VARCHAR2(20),
  planedTrackNum      VARCHAR2(20),
  arriveDelayDays     int(5),
  departureDelayDays  int(5)
);

CREATE SEQUENCE BASICSTATIONINFO_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER BASICSTATIONINFO_Sequence
BEFORE
INSERT ON basictrainstationinfo
FOR EACH ROW
  BEGIN
    SELECT BASICSTATIONINFO_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;



--  车站信息配置
-- 判断表是否存在，如果存在则删除
DECLARE
  num NUMBER;
BEGIN
  SELECT count(1)
  INTO num
  FROM all_tables
  WHERE TABLE_NAME = 'stationconfig' AND OWNER = 'LZY';
  IF num = 1
  THEN
    EXECUTE IMMEDIATE 'DROP TABLE stationconfig';
  END IF;
END;

CREATE TABLE stationconfig (
  id          NUMBER(11) NOT NULL PRIMARY KEY,
  stationcode VARCHAR2(50),
  stationName VARCHAR2(50),
  mileage     NUMBER(11),
  bureaucode  VARCHAR2(50)
);

CREATE SEQUENCE STATIONCONFIG_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER STATIONCONFIG_Sequence
BEFORE
INSERT ON stationconfig
FOR EACH ROW
  BEGIN
    SELECT STATIONCONFIG_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('4', 'Mariakani', 0, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('17', 'Mtito Andei', 12, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('32', 'Athi River', 23, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('25', 'Emali', 36, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('2', 'Mombasa', 678, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('8', 'Miasenyi', 123, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('11', 'Voi', 234, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('20', 'Kibwezi', 313, '0');
INSERT INTO stationconfig (stationcode, stationName, mileage, bureaucode) VALUES ('33', 'Nairobi', 876, '0');

