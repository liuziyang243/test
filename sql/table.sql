--config.po
create table stationConfig(
stationCode varchar2(100) not null,
stationName varchar2(100) not null,
mileage     varchar2(100) not null,
bureauCode  varchar2(100) not null,
constraint stationConfig_pk PRIMARY KEY (stationCode)
);
--authority.po
create table userinfo(
userId varchar2(100) not null,--uuid
userName varchar2(100) not null,
password varchar2(100) not null,
salt varchar2(100) not null,
userLevel Number(10) not null,
userDescription varchar2(100),
stationNameList varchar2(500) not null,
constraint userinfo_pk PRIMARY KEY (userId)
);
create table role(
roleId varchar2(100) not null,--uuid
roleName varchar2(100) not null,
roleDescription varchar2(100),
constraint role_pk PRIMARY KEY (roleId)
);
create table action(
actionName varchar2(100) not null,
actionDescription varchar2(100),
constraint action_pk PRIMARY KEY (actionName)
);
create table userRole(
id varchar2(100) not null, --userId+roleId
userId varchar2(100) not null,
roleId varchar2(100) not null,
constraint userRole_pk PRIMARY KEY (id)
);
create table roleAction(
id varchar2(100) not null, --roleId+actionName
roleId varchar2(100) not null,
actionName varchar2(100) not null,
constraint roleAction_pk PRIMARY KEY (id)
);
create table userTree(
childUserId varchar2(100) not null,--userId
parentUserId varchar2(100) not null,
constraint userTree_pk PRIMARY KEY (childUserId)
);

--ctc.po
create table basicMap(
uuid varchar2(100) not null,--uuid
basicMapJson clob not null,
receiveTime varchar2(100) not null,
confirmState number(1) not null,
constraint basicMap_pk PRIMARY KEY (uuid)
);

--systemconfig.sql
create table systemConfig(
clientVersion varchar2(100) not null
);
