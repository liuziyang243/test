-- ScreenGuideRule
CREATE TABLE ScreenGuideRule (
  id                NUMBER(11) NOT NULL,
  stationName       VARCHAR2(50),
  trainType         VARCHAR2(20),
  stationType       VARCHAR2(20),
  screenType        VARCHAR2(20),
  upTimeReference   VARCHAR2(20),
  upTimeOffset      NUMBER(11),
  downTimeReference VARCHAR2(20),
  downTimeOffset    NUMBER(11),
  CONSTRAINT uc_rule UNIQUE (trainType, stationType, screenType),
  CONSTRAINT ScreenGuideRule_pk PRIMARY KEY (id)
);

CREATE SEQUENCE ScreenGuideRule_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER GuideRule_Trigger
BEFORE
INSERT ON ScreenGuideRule
FOR EACH ROW
  BEGIN
    SELECT ScreenGuideRule_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- DataTableRowVar
CREATE TABLE DataTableRowVar(
  id                NUMBER(11) NOT NULL,
  frameName         VARCHAR2(100),
  elementID         VARCHAR2(50),
  dataSource        VARCHAR2(50),
  colID             VARCHAR2(50),
  variateName       VARCHAR2(50),
  lang              VARCHAR2(50),
  CONSTRAINT DataTableRowVar_pk PRIMARY KEY (id)
);

CREATE SEQUENCE DataTableRowVar_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER DataTableRowVar_Trigger
BEFORE
INSERT ON DataTableRowVar
FOR EACH ROW
  BEGIN
    SELECT DataTableRowVar_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- DefaultFormatInfo
CREATE TABLE DefaultFormatInfo(
  id                NUMBER(11) NOT NULL,
  formatData        VARCHAR2(4000),
  screenType        VARCHAR2(50),
  screenWidth       NUMBER(10),
  screenHeight      NUMBER(10),
  screenColor       VARCHAR2(50),
  formatName        VARCHAR2(100),
  CONSTRAINT DefaultFormatInfo_pk PRIMARY KEY (id)
);

CREATE SEQUENCE DefaultFormatInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER DefaultFormatInfo_Trigger
BEFORE
INSERT ON DefaultFormatInfo
FOR EACH ROW
  BEGIN
    SELECT DefaultFormatInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- FormatInfo
CREATE TABLE FormatInfo(
  id                NUMBER(11) NOT NULL,
  storagePath       VARCHAR2(200),
  screenType        VARCHAR2(50),
  stationName       VARCHAR2(50),
  screenWidth       VARCHAR2(10),
  screenHeight      VARCHAR2(10),
  screenColor       VARCHAR2(50),
  formatName        VARCHAR2(100),
  formatID          VARCHAR2(50),
  version           VARCHAR2(100),
  CONSTRAINT FormatInfo_pk PRIMARY KEY (id)
);

CREATE SEQUENCE FormatInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER FormatInfo_Trigger
BEFORE
INSERT ON FormatInfo
FOR EACH ROW
  BEGIN
    SELECT FormatInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- FrameInfo
CREATE TABLE FrameInfo(
  id                NUMBER(11) NOT NULL,
  frameName         VARCHAR2(100),
  showName          VARCHAR2(100),
  stationName       VARCHAR2(50),
  screenType        VARCHAR2(50),
  screenWidth       VARCHAR2(10),
  screenHeight       VARCHAR2(10),
  screenColor       VARCHAR2(50),
  usedFormat        VARCHAR2(4000),
  storagePath       VARCHAR2(200),
  CONSTRAINT FrameInfo_pk PRIMARY KEY (id)
);

CREATE SEQUENCE FrameInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER FrameInfo_Trigger
BEFORE
INSERT ON FrameInfo
FOR EACH ROW
  BEGIN
    SELECT FrameInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- ScreenManage
CREATE TABLE ScreenManage(
  screenID          NUMBER(11) NOT NULL,
  onOff             VARCHAR2(10),
  currentFormatNo   VARCHAR2(50),
  standbyFormatNo   VARCHAR2(50),
  fasFormatNo       VARCHAR2(50),
  usedFormat        VARCHAR2(50),
  version           VARCHAR2(100),
  formatSendStatus  VARCHAR2(50),
  CONSTRAINT ScreenManage_pk PRIMARY KEY (screenID)
);

-- TicketWinScreenContent
CREATE TABLE TicketWinScreenContent(
  id                NUMBER(11) NOT NULL,
  screenID          NUMBER(11),
  stationName       VARCHAR2(50),
  winNum            VARCHAR2(5),
  content           VARCHAR2(500),
  groupName         VARCHAR2(200),
  CONSTRAINT TicketWinScreenContent_pk PRIMARY KEY (id)
);

CREATE SEQUENCE TWScreenContent_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER TWScreenContent_Trigger
BEFORE
INSERT ON TicketWinScreenContent
FOR EACH ROW
  BEGIN
    SELECT TWScreenContent_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- PlayListInfo
CREATE TABLE PlayListInfo(
  id                NUMBER(11) NOT NULL,
  listName          VARCHAR2(50),
  version           VARCHAR2(50),
  listType          VARCHAR2(30),
  playType          VARCHAR2(2),
  content1          VARCHAR2(4000),
  content2          VARCHAR2(4000),
    CONSTRAINT PlayListInfo_pk PRIMARY KEY (id)
);