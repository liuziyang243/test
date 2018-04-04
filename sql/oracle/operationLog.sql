-- methodRelation
CREATE TABLE methodRelation (
  methodName VARCHAR(50) not null,
  serviceType VARCHAR(50) not null,
  constraint methodRelation_pk PRIMARY KEY (methodName)
);

-- operationLog
CREATE TABLE operationLog (
  id int(11) not null,
  operationTime VARCHAR(50) not null,
  operator VARCHAR(50) not null,
  serviceType VARCHAR(50) not null,
  operationContent clob,
  operationResult VARCHAR(50) not null,
  stationName VARCHAR(50) not null,
  constraint operationLog_pk PRIMARY KEY (id)
);

CREATE SEQUENCE S_operationLog
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER T_operationLog
BEFORE
INSERT ON operationLog
FOR EACH ROW
  BEGIN
    SELECT S_operationLog.nextval
    INTO :New.id
    FROM dual;
  END;