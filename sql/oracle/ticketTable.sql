-- TicketScreenData
CREATE TABLE TicketScreenData(
id NUMBER(11) NOT NULL PRIMARY KEY,
trainNum VARCHAR2(50),
stationName VARCHAR2(50),
seatName VARCHAR2(50),
ticketNum NUMBER(10),
startStation VARCHAR2(50),
finalStation VARCHAR2(50),
planArriveTime VARCHAR2(50),
planDepartureTime VARCHAR2(50),
ticketDate VARCHAR2(50)
);

CREATE SEQUENCE TSData_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER TSData_Trigger
BEFORE
INSERT ON TicketScreenData
FOR EACH ROW
  BEGIN
    SELECT TSData_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;

-- PassengerFlowInfo
CREATE TABLE PassengerFlowInfo(
id NUMBER(11) NOT NULL PRIMARY KEY,
trainNum VARCHAR2(50),
stationName VARCHAR2(50),
ticketDate VARCHAR2(50),
passengerGetOn NUMBER(10),
passengerGetOff NUMBER(10),
updateTime VARCHAR2(50)
);

CREATE SEQUENCE PassengerFlowInfo_Sequence
INCREMENT BY 1   -- 每次加几个
START WITH 1     -- 从1开始计数
NOMAXVALUE -- 不设置最大值
NOCYCLE; -- 一直累加，不循环

CREATE TRIGGER PassengerFlowInfo_Trigger
BEFORE
INSERT ON PassengerFlowInfo
FOR EACH ROW
  BEGIN
    SELECT PassengerFlowInfo_Sequence.nextval
    INTO :New.id
    FROM dual;
  END;