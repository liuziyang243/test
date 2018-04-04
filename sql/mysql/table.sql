/*
create table stationConfig(
  stationCode varchar(100) not null,
  stationName varchar(100) not null,
  mileage     varchar(100) not null,
  bureauCode  varchar(100) not null,
  constraint stationConfig_pk PRIMARY KEY (stationCode)
);
*/
-- userInfo
CREATE TABLE userInfo (
  userId          VARCHAR(100) NOT NULL,
  userName        VARCHAR(100) NOT NULL,
  password        VARCHAR(100) NOT NULL,
  salt            VARCHAR(100) NOT NULL,
  userLevel       INT(10)      NOT NULL,
  userDescription VARCHAR(100),
  stationNameList VARCHAR(500) NOT NULL,
  CONSTRAINT userinfo_pk PRIMARY KEY (userId)
);

-- role
CREATE TABLE role (
  roleId          VARCHAR(100) NOT NULL,
  roleName        VARCHAR(100) NOT NULL,
  roleDescription VARCHAR(100),
  CONSTRAINT role_pk PRIMARY KEY (roleId)
);

-- action
CREATE TABLE action (
  actionName        VARCHAR(100) NOT NULL,
  actionDescription VARCHAR(100),
  CONSTRAINT action_pk PRIMARY KEY (actionName)
);

-- userRole
CREATE TABLE userRole (
  id     VARCHAR(100) NOT NULL,
  userId VARCHAR(100) NOT NULL,
  roleId VARCHAR(100) NOT NULL,
  CONSTRAINT userRole_pk PRIMARY KEY (id)
);

-- roleAction
CREATE TABLE roleAction (
  id         VARCHAR(100) NOT NULL,
  roleId     VARCHAR(100) NOT NULL,
  actionName VARCHAR(100) NOT NULL,
  CONSTRAINT roleAction_pk PRIMARY KEY (id)
);

-- userTree
CREATE TABLE userTree (
  childUserId  VARCHAR(100) NOT NULL,
  parentUserId VARCHAR(100) NOT NULL,
  CONSTRAINT userTree_pk PRIMARY KEY (childUserId)
);

-- basicMap
CREATE TABLE basicMap (
  uuid         VARCHAR(100) NOT NULL,
  basicMapJson LONGTEXT     NOT NULL,
  receiveTime  VARCHAR(100) NOT NULL,
  confirmState INT(1)       NOT NULL,
  CONSTRAINT basicMap_pk PRIMARY KEY (uuid)
);

-- systemConfig
CREATE TABLE systemConfig (
  clientVersion VARCHAR(100) NOT NULL
);

-- BroadcastTemplateGroup
CREATE TABLE BroadcastTemplateGroup (
  id                INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  templateGroupName VARCHAR(100),
  stationName       VARCHAR(50),
  broadcastKind     VARCHAR(20),
  revisability      INT(1)
);

-- BroadcastTemplate
CREATE TABLE BroadcastTemplate (
  id                   INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  templateGroupName    VARCHAR(100),
  stationName          VARCHAR(50),
  broadcastKind        VARCHAR(20),
  baseTime             VARCHAR(20),
  broadcastContentName VARCHAR(50),
  operationMode        VARCHAR(20),
  priorityLevel        INT(11),
  broadcastArea        VARCHAR(300),
  firstRegion          VARCHAR(100),
  timeOffset           INT(11)
);

-- NormalBroadcastContent
CREATE TABLE NormalBroadcastContent (
  id                INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  contentName       VARCHAR(100),
  stationName       VARCHAR(50),
  broadcastKind     VARCHAR(20),
  contentInLocalLan VARCHAR(2000),
  contentInEng      VARCHAR(2000)
);

-- SpecialBroadcastContent
CREATE TABLE SpecialBroadcastContent (
  id               INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  contentName      VARCHAR(100),
  stationName      VARCHAR(50),
  broadcastKind    VARCHAR(20),
  broadcastContent VARCHAR(2000)
);

-- Broadcast record
CREATE TABLE BroadcastRecord (
  id               INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  recordKey        VARCHAR(150),
  trainNum         VARCHAR(30),
  stationName      VARCHAR(30),
  broadcastKind    VARCHAR(20),
  broadcastMode    VARCHAR(20),
  callDriverTime   VARCHAR(30),
  broadcastArea    VARCHAR(200),
  operationState   VARCHAR(20),
  recordTime       VARCHAR(30),
  priorityLevel    INT(5),
  broadcastContent VARCHAR(2000)
);

-- basicPlan
CREATE TABLE basicplan (
  id                INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  generateTimestamp VARCHAR(20),
  trainNum          VARCHAR(50) UNIQUE,
  uniqueTrainNum    VARCHAR(50),
  trainType         VARCHAR(30),
  startStation      VARCHAR(50),
  finalStation      VARCHAR(50),
  validPeriodStart  VARCHAR(20),
  validPeriodEnd    VARCHAR(20),
  trainSuspendStart VARCHAR(20),
  trainSuspendEnd   VARCHAR(20)
);

-- 列车时刻表-车站信息
CREATE TABLE basictrainstationinfo (
  id                  INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  trainNum            VARCHAR(50),
  stationName         VARCHAR(50),
  startStation        VARCHAR(50),
  finalStation        VARCHAR(50),
  planedDepartureTime VARCHAR(20),
  planedArriveTime    VARCHAR(20),
  planedTrackNum      VARCHAR(20),
  arriveDelayDays     INT(5),
  departureDelayDays  INT(5)
);

DROP TABLE stationconfig;
--  车站信息配置
CREATE TABLE stationconfig (
  id          INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  stationcode VARCHAR(50),
  stationName VARCHAR(50),
  mileage     INT(11),
  bureaucode  VARCHAR(50)
);

-- broadcast area config
CREATE TABLE BroadcastArea (
  id                  INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  broadcastZoneName   VARCHAR(50),
  broadcastZoneID     INT(11),
  secondaryRegionList VARCHAR(300),
  stationName         VARCHAR(30),
  groupName           VARCHAR(50)
);

-- region config
CREATE TABLE StationRegionConfig (
  id                INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  secondaryRegion   VARCHAR(50),
  firstRegion       VARCHAR(50),
  stationName       VARCHAR(30),
  broadcastAreaList VARCHAR(300)
);

-- track platform map config
CREATE TABLE TrackPlatformMap (
  id          INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  platform    VARCHAR(50),
  trackNum    INT(11),
  stationName VARCHAR(30)
);

-- screen map config
CREATE TABLE ScreenConfig (
  screenID           INT(11) NOT NULL PRIMARY KEY,
  screenName         VARCHAR(50),
  ScreenType         VARCHAR(30),
  screenWidth        INT(11),
  screenHeight       INT(11),
  screenColor        VARCHAR(30),
  screenIp           VARCHAR(30),
  screenControllerID INT(11),
  controllerType     VARCHAR(30),
  serverIp           VARCHAR(30),
  stationName        VARCHAR(30),
  secondaryRegion    VARCHAR(30)
);

-- screen control server config
CREATE TABLE ScreenCtrlServerConfig (
  id          INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  stationName VARCHAR(30),
  ip          VARCHAR(30),
  port        VARCHAR(10)
);

-- play list Information
CREATE TABLE PlayListInfo (
  id       INT(11) NOT NULL PRIMARY KEY,
  listName VARCHAR(50),
  version  VARCHAR(50),
  listType VARCHAR(30),
  playType VARCHAR(2),
  content1 VARCHAR(4000),
  content2 VARCHAR(4000)
);

-- dispatch plan
CREATE TABLE DISPATCHSTATIONPLAN
(
  ID                   INT(11) NOT NULL
    PRIMARY KEY AUTO_INCREMENT,
  GENERATETIMESTAMP    VARCHAR(20),
  PLANKEY              VARCHAR(50)
    UNIQUE,
  TRAINNUM             VARCHAR(10),
  UNIQUETRAINNUM       VARCHAR(10),
  TRAINTYPE            VARCHAR(10),
  PLANDATE             VARCHAR(10),
  STATIONTYPE          VARCHAR(10),
  TRAINDIRECTION       VARCHAR(5),
  STARTSTATION         VARCHAR(30),
  FINALSTATION         VARCHAR(30),
  PRESENTSTATION       VARCHAR(30),
  VALIDPERIODSTART     VARCHAR(10),
  VALIDPERIODEND       VARCHAR(10),
  TRAINSUSPENDSTART    VARCHAR(10),
  TRAINSUSPENDEND      VARCHAR(10),
  MANUALSUSPENDFLAG    INT(1),
  PLANEDTRACKNUM       INT(11),
  ACTUALTRACKNUM       INT(11),
  PLANEDDEPARTURETIME  VARCHAR(20),
  ACTUALDEPARTURETIME  VARCHAR(20),
  DEPARTURESTATEREASON VARCHAR(100),
  PLANEDARRIVETIME     VARCHAR(20),
  ACTUALARRIVETIME     VARCHAR(20),
  ARRIVESTATEREASON    VARCHAR(100),
  LASTEDITEDTIMESTAMP  VARCHAR(20)
);

-- passenger plan
CREATE TABLE PASSENGERSTATIONPLAN
(
  ID                         INT(11) NOT NULL
    PRIMARY KEY                           AUTO_INCREMENT,
  GENERATETIMESTAMP          VARCHAR(20),
  PLANKEY                    VARCHAR(50)
    UNIQUE,
  TRAINNUM                   VARCHAR(10),
  UNIQUETRAINNUM             VARCHAR(10),
  TRAINTYPE                  VARCHAR(10),
  PLANDATE                   VARCHAR(10),
  STATIONTYPE                VARCHAR(10),
  TRAINDIRECTION             VARCHAR(5),
  STARTSTATION               VARCHAR(30),
  FINALSTATION               VARCHAR(30),
  PRESENTSTATION             VARCHAR(30),
  VALIDPERIODSTART           VARCHAR(10),
  VALIDPERIODEND             VARCHAR(10),
  TRAINSUSPENDSTART          VARCHAR(10),
  TRAINSUSPENDEND            VARCHAR(10),
  MANUALSUSPENDFLAG          INT(1),
  PLANEDTRACKNUM             INT(11),
  ACTUALTRACKNUM             INT(11),
  PLANEDDEPARTURETIME        VARCHAR(20),
  ACTUALDEPARTURETIME        VARCHAR(20),
  DEPARTURESTATEREASON       VARCHAR(100),
  PLANEDARRIVETIME           VARCHAR(20),
  ACTUALARRIVETIME           VARCHAR(20),
  ARRIVESTATEREASON          VARCHAR(100),
  STARTABOARDCHECKBASE       VARCHAR(20),
  STARTABOARDCHECKTIMEOFFSET INT(11),
  STARTABOARDCHECKTIME       VARCHAR(20),
  STOPABOARDCHECKBASE        VARCHAR(20),
  STOPABOARDCHECKTIMEOFFSET  INT(11),
  STOPABOARDCHECKTIME        VARCHAR(20),
  WAITZONE                   VARCHAR(200) DEFAULT NULL,
  STATIONENTRANCEPORT        VARCHAR(200) DEFAULT NULL,
  STATIONEXITPORT            VARCHAR(200) DEFAULT NULL,
  ABOARDCHECKGATE            VARCHAR(200) DEFAULT NULL,
  EXITCHECKGATE              VARCHAR(200) DEFAULT NULL,
  LASTEDITEDTIMESTAMP        VARCHAR(20)
);

-- guide plan
CREATE TABLE GUIDESTATIONPLAN
(
  ID                         INT(11) NOT NULL
    PRIMARY KEY                           AUTO_INCREMENT,
  GENERATETIMESTAMP          VARCHAR(20),
  PLANKEY                    VARCHAR(50)
    UNIQUE,
  TRAINNUM                   VARCHAR(10),
  UNIQUETRAINNUM             VARCHAR(10),
  TRAINTYPE                  VARCHAR(10),
  PLANDATE                   VARCHAR(10),
  STATIONTYPE                VARCHAR(10),
  TRAINDIRECTION             VARCHAR(5),
  STARTSTATION               VARCHAR(30),
  FINALSTATION               VARCHAR(30),
  PRESENTSTATION             VARCHAR(30),
  VALIDPERIODSTART           VARCHAR(10),
  VALIDPERIODEND             VARCHAR(10),
  TRAINSUSPENDSTART          VARCHAR(10),
  TRAINSUSPENDEND            VARCHAR(10),
  MANUALSUSPENDFLAG          INT(1),
  PLANEDTRACKNUM             INT(11),
  ACTUALTRACKNUM             INT(11),
  PLANEDDEPARTURETIME        VARCHAR(20),
  ACTUALDEPARTURETIME        VARCHAR(20),
  DEPARTURESTATEREASON       VARCHAR(100),
  PLANEDARRIVETIME           VARCHAR(20),
  ACTUALARRIVETIME           VARCHAR(20),
  ARRIVESTATEREASON          VARCHAR(100),
  STARTABOARDCHECKBASE       VARCHAR(20),
  STARTABOARDCHECKTIMEOFFSET INT(11),
  STARTABOARDCHECKTIME       VARCHAR(20),
  STOPABOARDCHECKBASE        VARCHAR(20),
  STOPABOARDCHECKTIMEOFFSET  INT(11),
  STOPABOARDCHECKTIME        VARCHAR(20),
  WAITZONE                   VARCHAR(200) DEFAULT NULL,
  STATIONENTRANCEPORT        VARCHAR(200) DEFAULT NULL,
  STATIONEXITPORT            VARCHAR(200) DEFAULT NULL,
  ABOARDCHECKGATE            VARCHAR(200) DEFAULT NULL,
  EXITCHECKGATE              VARCHAR(200) DEFAULT NULL,
  LASTEDITEDTIMESTAMP        VARCHAR(20)
);

-- broadcast plan
CREATE TABLE BROADCASTSTATIONPLAN
(
  ID                         INT(11) NOT NULL
    PRIMARY KEY                           AUTO_INCREMENT,
  GENERATETIMESTAMP          VARCHAR(20),
  PLANKEY                    VARCHAR(50)
    UNIQUE,
  TRAINNUM                   VARCHAR(10),
  UNIQUETRAINNUM             VARCHAR(10),
  TRAINTYPE                  VARCHAR(10),
  PLANDATE                   VARCHAR(10),
  STATIONTYPE                VARCHAR(10),
  TRAINDIRECTION             VARCHAR(5),
  STARTSTATION               VARCHAR(30),
  FINALSTATION               VARCHAR(30),
  PRESENTSTATION             VARCHAR(30),
  VALIDPERIODSTART           VARCHAR(10),
  VALIDPERIODEND             VARCHAR(10),
  TRAINSUSPENDSTART          VARCHAR(10),
  TRAINSUSPENDEND            VARCHAR(10),
  MANUALSUSPENDFLAG          INT(1),
  PLANEDTRACKNUM             INT(11),
  ACTUALTRACKNUM             INT(11),
  PLANEDDEPARTURETIME        VARCHAR(20),
  ACTUALDEPARTURETIME        VARCHAR(20),
  DEPARTURESTATEREASON       VARCHAR(100),
  PLANEDARRIVETIME           VARCHAR(20),
  ACTUALARRIVETIME           VARCHAR(20),
  ARRIVESTATEREASON          VARCHAR(100),
  STARTABOARDCHECKBASE       VARCHAR(20),
  STARTABOARDCHECKTIMEOFFSET INT(11),
  STARTABOARDCHECKTIME       VARCHAR(20),
  STOPABOARDCHECKBASE        VARCHAR(20),
  STOPABOARDCHECKTIMEOFFSET  INT(11),
  STOPABOARDCHECKTIME        VARCHAR(20),
  WAITZONE                   VARCHAR(200) DEFAULT NULL,
  STATIONENTRANCEPORT        VARCHAR(200) DEFAULT NULL,
  STATIONEXITPORT            VARCHAR(200) DEFAULT NULL,
  ABOARDCHECKGATE            VARCHAR(200) DEFAULT NULL,
  EXITCHECKGATE              VARCHAR(200) DEFAULT NULL,
  BROADCASTKIND              VARCHAR(20),
  BROADCASTBASETIME          VARCHAR(20),
  BROADCASTTIMEOFFSET        INT(11),
  BROADCASTTIME              VARCHAR(20),
  BROADCASTCONTENTNAME       VARCHAR(50),
  BROADCASTOPERATIONMODE     VARCHAR(10),
  BROADCASTPRIORITYLEVEL     INT(11),
  BROADCASTAREA              VARCHAR(200),
  BROADCASTSTATE             VARCHAR(20),
  LASTEDITEDTIMESTAMP        VARCHAR(20)
);

-- OrganizeTemplate
CREATE TABLE OrganizeTemplate
(
  ID                         INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  TRAINNUM                   VARCHAR(10),
  STATIONNAME                VARCHAR(10),
  STARTABOARDCHECKBASE       VARCHAR(20),
  STARTABOARDCHECKTIMEOFFSET INT(11),
  STOPABOARDCHECKBASE        VARCHAR(20),
  STOPABOARDCHECKTIMEOFFSET  INT(11),
  WAITZONELIST               VARCHAR(200)                 DEFAULT NULL,
  STATIONENTRANCEPORT        VARCHAR(200)                 DEFAULT NULL,
  STATIONEXITPORT            VARCHAR(200)                 DEFAULT NULL,
  ABOARDCHECKGATE            VARCHAR(200)                 DEFAULT NULL,
  EXITCHECKGATE              VARCHAR(200)                 DEFAULT NULL,
  BROADCASTTEMPLATEGROUPNAME VARCHAR(50)
);

-- entry
CREATE TABLE Entry (
  id              INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  originalWord    VARCHAR(100),
  translationWord VARCHAR(200),
  languageName    VARCHAR(30),
  reviseFlag      INT(1)
);

-- language
CREATE TABLE Language (
  languageName VARCHAR(30) NOT NULL PRIMARY KEY
);

-- noticeMessage
CREATE TABLE noticeMessage (
  ID                INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sender            VARCHAR(50),
  receiver          VARCHAR(50),
  generateUser      VARCHAR(50),
  generateTimeStamp VARCHAR(50),
  processState      VARCHAR(50),
  processUser       VARCHAR(50),
  processTime       VARCHAR(50),
  trainNum          VARCHAR(50),
  stationName       VARCHAR(50),
  planDate          VARCHAR(50),
  modifiedDataMap   VARCHAR(50)
);

-- methodRelation
CREATE TABLE methodRelation (
  methodName  VARCHAR(50) NOT NULL PRIMARY KEY,
  serviceType VARCHAR(50) NOT NULL
);

-- operationLog
CREATE TABLE operationLog (
  id               INT(11)     NOT NULL PRIMARY KEY AUTO_INCREMENT,
  operationTime    VARCHAR(50) NOT NULL,
  operator         VARCHAR(50) NOT NULL,
  serviceType      VARCHAR(50) NOT NULL,
  operationContent VARCHAR(4000),
  operationResult  VARCHAR(50) NOT NULL,
  stationName      VARCHAR(50) NOT NULL

);

-- PlayListSendingStatus
CREATE TABLE PlayListSendingStatus (
  id            INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  playListId    VARCHAR(100),
  version       VARCHAR(100),
  serverIp      VARCHAR(20),
  sendingStatus VARCHAR(20)
);

-- -- InterfaceMachineWarning
CREATE TABLE InterfaceMachineWarning (
  id             INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  identification VARCHAR(100),
  generateTime   VARCHAR(30),
  belongSystem   VARCHAR(30),
  warningLevel   VARCHAR(20),
  warningMessage VARCHAR(500),
  confirmUser    VARCHAR(30),
  confirmTime    VARCHAR(30),
  confirmState   VARCHAR(20),
  machineType    VARCHAR(30),
  station        VARCHAR(20)
);

