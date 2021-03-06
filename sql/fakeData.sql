--stationconfig
insert into stationconfig (stationCode,stationName,mileage,bureauCode) values ('0','center','100','0');
insert into stationconfig (stationCode,stationName,mileage,bureauCode) values ('1','test1','100','0');
insert into stationconfig (stationCode,stationName,mileage,bureauCode) values ('2','test2','100','0');
insert into stationconfig (stationCode,stationName,mileage,bureauCode) values ('3','test3','100','0');
insert into stationconfig (stationCode,stationName,mileage,bureauCode) values ('4','test4','100','0');
--action
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('deviceBasicInformationMaintenance','123456456');
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('dispatchPlan','dispatch');
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('firealarmEquipmentStatus','123456');
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('pageConfiguration',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('passengerPlan',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('broadcastPlan',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('guidePlan',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('scheduleComparison',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('scheduleManagement',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('broadcastTemplates',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('guideRules',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('passengerOrganizeTemplates',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('planGeneration',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('scheduleConfiguration',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('manualBroadcasting',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('volumemodulation',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('territorialClassification',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('specialBroadcastMaintenance',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('trainBroadcastMaintenance',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('saveMatirial',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('matirialApprove',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('formatEditor',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('formatmanagement',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('managementOfTicketsScreen',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('videoMonitor',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('videotapeReplay',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('pollGroupConfigure',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('statusOfBroadcastingEquipment',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('statusOfGuidingEquipment',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('intrusionAlarmAndAccessEquipmentStatus',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('warning',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('trainTimeTable',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('remainingTickets',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('trainAnnouncement',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('operationLog',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('authorityConfiguration',null);
Insert into ACTION (ACTIONNAME,ACTIONDESCRIPTION) values ('dictconfig',null);
--role
insert into role (roleId,roleName) values ('11111','role1');
insert into role (roleId,roleName) values ('22222','role2');
--roleaction
insert into roleAction (id, roleid, actionName) values ('11111dispatchPlan','11111','dispatchPlan');
insert into roleAction (id, roleid, actionName) values ('11111guidePlan','11111','guidePlan');
insert into roleAction (id, roleid, actionName) values ('11111passengerPlan','11111','passengerPlan');
insert into roleAction (id, roleid, actionName) values ('22222dispatchPlan','22222','dispatchPlan');
insert into roleAction (id, roleid, actionName) values ('22222broadcastPlan','22222','broadcastPlan');
commit;
