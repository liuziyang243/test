-- DocumentResourceInfo
CREATE TABLE DocumentResourceInfo(
  id                NUMBER(11) NOT NULL PRIMARY KEY,
  fileName          VARCHAR2(100),
  fileType          VARCHAR2(20),
  fileDescription   VARCHAR2(1000),
  approvalStatus    VARCHAR2(20),
  approvalResult    VARCHAR2(20),
  sendingStatus     VARCHAR2(20),
  uploadTime        VARCHAR2(20),
  uploader          VARCHAR2(50),
  approveTime       VARCHAR2(20),
  approver          VARCHAR2(50)
);

CREATE SEQUENCE DocumentResourceInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER DocumentResourceInfo_Trigger
  BEFORE
INSERT ON DocumentResourceInfo
FOR EACH ROW
BEGIN
  SELECT DocumentResourceInfo_Sequence.nextval
  INTO :New.id
  FROM dual;
END;

-- PictureResourceInfo
CREATE TABLE PictureResourceInfo(
  id                   NUMBER(11) NOT NULL PRIMARY KEY,
  fileName             VARCHAR2(100),
  pictureWidth         NUMBER(10),
  pictureHeight        NUMBER(10),
  pictureType          VARCHAR2(20),
  pictureDescription   VARCHAR2(1000),
  approvalStatus       VARCHAR2(20),
  approvalResult       VARCHAR2(20),
  sendingStatus        VARCHAR2(20),
  uploadTime           VARCHAR2(20),
  uploader             VARCHAR2(50),
  approveTime          VARCHAR2(20),
  approver             VARCHAR2(50)
);

CREATE SEQUENCE PictureResourceInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER PictureResourceInfo_Trigger
BEFORE
INSERT ON PictureResourceInfo
FOR EACH ROW
  BEGIN
    SELECT PictureResourceInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- ResourceSendingStatus
CREATE TABLE ResourceSendingStatus(
  id                   NUMBER(11) NOT NULL PRIMARY KEY,
  fileName             VARCHAR2(100),
  controllerID         VARCHAR2(20),
  sendingStatus        VARCHAR2(5)
);

CREATE SEQUENCE ResourceSendingStatus_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER ResourceSendingStatus_Trigger
BEFORE
INSERT ON ResourceSendingStatus
FOR EACH ROW
  BEGIN
    SELECT ResourceSendingStatus_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- StreamMediaInfo
CREATE TABLE StreamMediaInfo(
  id                   NUMBER(11) NOT NULL PRIMARY KEY,
  smName               VARCHAR2(100),
  url                  VARCHAR2(500),
  description          VARCHAR2(1000),
  status               VARCHAR2(10)
);

CREATE SEQUENCE StreamMediaInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER StreamMediaInfo_Trigger
BEFORE
INSERT ON StreamMediaInfo
FOR EACH ROW
  BEGIN
    SELECT StreamMediaInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- VideoResourceInfo
CREATE TABLE VideoResourceInfo(
  id                   NUMBER(11) NOT NULL PRIMARY KEY,
  fileName             VARCHAR2(100),
  videoDuration        VARCHAR2(100),
  videoType            VARCHAR2(20),
  videoDescription     VARCHAR2(1000),
  approvalStatus       VARCHAR2(20),
  approvalResult       VARCHAR2(20),
  sendingStatus        VARCHAR2(20),
  uploadTime           VARCHAR2(20),
  uploader             VARCHAR2(50),
  approveTime          VARCHAR2(20),
  approver             VARCHAR2(50)
);

CREATE SEQUENCE VideoResourceInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER VideoResourceInfo_Trigger
BEFORE
INSERT ON VideoResourceInfo
FOR EACH ROW
  BEGIN
    SELECT VideoResourceInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;