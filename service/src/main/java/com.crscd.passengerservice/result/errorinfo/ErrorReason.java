package com.crscd.passengerservice.result.errorinfo;

/**
 * Created by Administrator on 2017/8/4.
 */
public interface ErrorReason {

    // 通用
    String STATUS_CODE_200 = "Execute success";
    String STATUS_CODE_MINUS2 = "Init failed";
    String STATUS_CODE_30 = "Part of Results turns false.";

    // 广播控制器接口返回信息
    String STATUS_CODE_MINUS1 = "The broadcast controller driver failed.";
    String STATUS_CODE_1 = "Executed successfully.";
    String STATUS_CODE_2 = "Can't get communication address of the broadcast controller. ";
    String STATUS_CODE_3 = "The broadcast content or broadcast area is null.";
    String STATUS_CODE_4 = "Broadcast's priority is low,the broadcast area or source is occupied by higher priority broadcast.";
    String STATUS_CODE_5 = "Broadcast source number is wrong.";
    String STATUS_CODE_6 = "No valid broadcast source.";
    String STATUS_CODE_7 = "The monitor broadcast area is not playing any broadcast content.";
    String STATUS_CODE_8 = "Can't get communication address of the broadcast remote module.";
    String STATUS_CODE_9 = "The broadcast remote module driver failed.";
    String STATUS_CODE_10 = "The operation on database failed.";
    String STATUS_CODE_11 = "The audition audio is occupied.";
    String STATUS_CODE_12 = "The broadcast area is occupied by microphone , please try again later.";
    String STATUS_CODE_13 = "The broadcast area is occupied by other broadcast with higher priority, please try again later.";

    // 版本号相关
    String STATUS_CODE_50 = "Client version is too old, please update client.";
    String STATUS_CODE_51 = "Please update actions.";

    // 登录相关
    String STATUS_CODE_60 = "Username is wrong or username and password don't match.";
    String STATUS_CODE_61 = "User has already logged in at other address.";
    String STATUS_CODE_62 = "Please configure ip range and restart background process.";
    String STATUS_CODE_63 = "Ip is illegal.";
    String STATUS_CODE_64 = "Wrong operation.";

    // 用户权限相关
    String STATUS_CODE_100 = "Failed to operate database.";
    String STATUS_CODE_101 = "User already exits.";
    String STATUS_CODE_102 = "Role already exits.";
    String STATUS_CODE_103 = "Current user can't create new user.";

    // notice操作相关
    String STATUS_CODE_301 = "The notice has been handled already, it can't be handled again.";
    String STATUS_CODE_302 = "The notice message has been invalid.The plan is not exit or out of date.";
    String STATUS_CODE_303 = "The notice message has been invalid.The message in the notice has already bean set in the plan.";
    String STATUS_CODE_304 = "Can not forward notice from CTC or Ticket system.";
    String STATUS_CODE_305 = "The single modify list in the notice is empty.";
    String STATUS_CODE_306 = "Can not find notice in the database.";

    // 广播词返STATUS
    String STATUS_CODE_1001 = "The broadcast contents are duplicated.";
    String STATUS_CODE_1002 = "The database operation is failed.";
    String STATUS_CODE_1003 = "There isn't any broadcast content to be updated in database.";

    // 广播播放执行结果返回
    String STATUS_CODE_1101 = "The broadcast plan is null.";
    String STATUS_CODE_1102 = "The broadcast content is null.";
    String STATUS_CODE_1103 = "The broadcast area is null.";
    String STATUS_CODE_1104 = "The executing state is wrong.";
    String STATUS_CODE_1105 = "The broadcast plan is in auto executing mode, please change it to manual mode before executing it manually.";
    String STATUS_CODE_1106 = "The broadcast record is null.";

    // cctv
    String STATUS_CODE_1201 = "The group name has already existed.";
    String STATUS_CODE_1202 = "The group name has not existed.";

    // warning
    String STATUS_CODE_1301 = "There is something wrong when confirm the warning message.";


    // 数据库操作
    String STATUS_CODE_2001 = "Failed to insert data";
    String STATUS_CODE_2002 = "Failed to update data";
    String STATUS_CODE_2003 = "Failed to deletion data";
    String STATUS_CODE_2011 = "Failed to insert data because there already has a same one";
    String STATUS_CODE_2012 = "Failed to update data because the data is not in database";
    String STATUS_CODE_2013 = "Failed to deletion data because the data is not in database";

    // 监控组插STATUS
    String STATUS_CODE_3001 = "Monitor group names are duplicated.";
    String STATUS_CODE_3002 = "Failed to insert data.";
    String STATUS_CODE_3003 = "Failed to update data.";
    String STATUS_CODE_3004 = "Failed to deletion data.";
    String STATUS_CODE_3005 = "Data isn't available.";
    String STATUS_CODE_3006 = "This camera has already been locked by other client.";
    String STATUS_CODE_3007 = "Unknown camera locking error.";
    String STATUS_CODE_3008 = "This camera is not locked.";
    String STATUS_CODE_3009 = "No camera is locked by this client.";
    String STATUS_CODE_3010 = "This camera is locked by other than this client";
}
