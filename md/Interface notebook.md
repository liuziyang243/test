# Passenger Service v2.0接口开发使用指南

---------- 

最后更新于 2017/11/9 10:29:46    

### 0. 后台接口设计划分说明 ### {#header0}
目前后台接口分为如下几个endpoint:

- BroadcastServiceInterface  
- GuideServiceInterface  
- LogServiceInterface  
- PlanServiceInterface  
- SystemServiceInterface  
- ExosystemServiceInterface

上述接口的职责划分如下：

接口 | 接口说明 
-----|------
SystemServiceInterface | 主要用于提供给系统配置信息和系统相关信息获取，包括权限功能接口、告警信息接口。  
PlanServiceInterface | 主要提供列车时刻表、计划信息的操作接口，包括notice信息接口。
LogServiceInterface | 用于提供前台在后台记录日志接口。 
GuideServiceInterface | 提供导向业务相关接口，包括版式编辑、绑定下发等操作。
BroadcastServiceInterface | 提供广播计划执行、广播词、广播模版、人工广播等业务的操作接口。
ExosystemServiceInterface | 提供外系统相关的服务接口，例如cctv中的获取cctv系统登陆用户名密码信息，以及轮询组接口；FAS/门禁系统的设备信息接口等。后续考虑将device页面对应的接口统一放在这个路径下。

### 1. 系统信息接口

系统信息接口用于前台在启动的时候向后台获取基本数据信息，主要包括各个车站的基本配置，包括站名站码、车站区域配置信息，广播区划分信息等。

> **Note:** 在新版本系统下，系统内部统一使用站名作为识别车站的唯一标识，站码只在与外系统进行交互的时候使用。

**后台接口定义如下：**

	SystemInfoDTO getSystemInfo(String lan);

**接口位置：**[ /Service/SystemService](http://xxx/Service/SystemService)

参数为语言类型，传递用户登录时选择的语言类型，后台根据该类型确定提供给前台systemInfoDTO中的翻译语言。

SystemInfoDTO 的数据定义如下：

	private String systemState;

    private HashMap<String, LateEarlyReasonEnum> lateEarlyReason;

    private List<StationInfoDTO> stationInfoList;

#### 1.1 车站列表信息 ####
车站信息列表，每个车站信息StationInfoDTO类型的数据定义如下：

	/* 站名,由站码确定 */
    private String stationName;
    /* 站码 */
    private String stationCode;
    /* 地理区域配置 */
    private ArrayList<SecondaryRegionDTO> geographicalRegionList;
    /* 广播区域配置 */
    private ArrayList<BroadcastAreaDTO> broadcastAreaList;
    /* 股道列表*/
    private List<Integer> trackList;

地理区域配置信息SecondaryRegionDTO数据包括：

	// 区域名称
    private String regionName;
    // 翻译后的区域名称
    private String translatedRegionName;
    // 隶属的一级区域
    private FirstRegionEnum firstRegion;

广播区域配置数据信息BroadcastAreaDTO数据包括：
	
	// 区域名称
    private String regionName;
    // 翻译后的区域名称
    private String translatedRegionName;
    // 广播区分组名称
    private String groupName;
    // 翻译后的分组名称
    private String translatedGroupName;

#### 1.2 晚点原因列表 ####
HashMap<String, LateEarlyReasonEnum>属性为晚点原因列表。

其中函数参数lan为界面使用语言，后台根据前台语言提供不同语言的晚点原因字符串。返回值Key为晚点原因，value为晚点原因枚举值，用于给后台传递参数使用。

LateEarlyReasonEnum 是前后台关于晚点原因的枚举接口，其定义如下：

    public enum LateEarlyReasonEnum {
    NONE("None."),
    WEATHER("Weather reason."),
    MAINTENANCE("Train maintenance reason."),
    MANAGEMENT("Management reason."),
    CTC_MODIFY("Changed from ctc system."),
    TICKET_MODIFY("Changed from ticket system."),
    DISPATCH_PLAN_MODIFY("Changed from dispatch plan."),
    PASSENGER_PLAN_MODIFY("Changed from passenger plan.");
    }

#### 1.3 后台工作状态 ####
systemState为后台工作状态，表示后台程序是中心状态还是车站状态。


#### 1.4 TBD ####
后续根据界面显示的需要，会继续增加上述数据的属性。

### 2. 列车时刻表页面接口
列车时刻表提供的功能主要包括对列车时刻表显示和手动编辑，界面布局与原有保持一致，左侧为列车时刻表基本信息的编辑和显示，右侧为选中一条列车时刻表信息时，本条列车时刻表对应的全部列车进过车站列表。
> **前台显示需求**
> 
> 1. 当显示左侧列车时刻表基本信息的时候，需要前台完成根据车次号进行默认排序；
> 2. 当选中一个时刻表，右侧显示车站信息的时候，需要前台完成根据里程进行默认排序。

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

#### 2.1 列车时刻表基本信息接口 ####
对于列车时刻表基本信息的增删改查接口如下：

    // 获取车次信息列表
    // 列车车次号和列车类型允许为空，当为null的时候，表示无此过滤条件
    // 车站名称是为了显示当前车站到发时间，如
    // 列车车次统一采用模糊搜索的方式返回结果
    @WebResult(name = "getBasicPlanMainInfoListResult")
    List<BasicPlanDTO> getBasicPlanMainInfoList(@WebParam(name = "stationName") String stationName, 
	@WebParam(name = "trainNum") String trainNum, @WebParam(name = "trainType") TrainTypeEnum trainType);

    // 添加车次信息
    @WebResult(name = "updateBasicStationUnitInfoResult")
    ResultMessage addBasicPlanMainInfo(@WebParam(name = "dto") BasicPlanDTO dto);

    // 编辑车次信息
    @WebResult(name = "updateBasicPlanMainInfoResult")
    ResultMessage updateBasicPlanMainInfo(@WebParam(name = "dto") BasicPlanDTO dto);

    // 删除车次信息
    @WebResult(name = "updateBasicPlanMainInfoResult")
    ResultMessage removeBasicPlanMainInfo(@WebParam(name = "trainNum") String trainNum);

BasicPlanDTO是前后台列车时刻表基本信息数据接口，其定义如下:

    public class BasicPlanDTO {
    /* 列车车次号 */
    private String trainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    /* 用于显示本站发车时间 */
    private String arriveTime;
    /* 用于显示本站到达时间 */
    private String departureTime;
	}

在创建和修改列车时刻表信息的时候，需要将BasicPlanDTO对象传给后台，在修改的时候，除了修改项，需要将其他值保持原值。

TrainTypeEnum是前后台数据接口，其定义如下：

    public enum TrainTypeEnum {
    HIGH_SPEED,
    INTERCITY,
    PASSENGER_SPECIAL,
    NORMAL;

TrainDirectionEnum是前后台数据接口，其定义如下：

    public enum TrainDirectionEnum {
    UP, DOWN;

当确定了列车的起始站和终到站后，通过调用后台接口可以获取列车开行方向。  
调用的接口如下，接口位置：[ /Service/SystemService](http://xxx/Service/SystemService)

    // 通过起始车站和终到站获取列车开行方向
    @WebResult(name = "getTrainDirectionResult")
    TrainDirectionEnum getTrainDirection(@WebParam(name = "startStation") 
	String startStation, @WebParam(name = "finalStation") String finalStation);

> **说明**  
> 对于列车开行方向，是后台程序自动计算，因此当前台传递列车时刻表信息的时候，可以将Enum值的设置状态设为false，即不用传递给后台。

#### 2.2 列车时刻表车站信息接口 ####
列车时刻表车站信息接口定义如下:

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

    // 选择车次信息后获取车站列表信息
    @WebResult(name = "getBasicStationInfoListResult")
    List<BasicTrainStationDTO> getBasicStationInfoList(@WebParam(name = "trainNum") String trainNum);

    // 添加车站信息
    @WebResult(name = "addBasicStationUnitInfoResult")
    ResultMessage addBasicPlanStationInfo(@WebParam(name = "trainNum") String trainNum, @WebParam(name = 
	"dto") BasicTrainStationDTO dto);

    // 编辑车站信息
    @WebResult(name = "updateBasicStationUnitInfoResult")
    ResultMessage updateBasicPlanStationInfo(@WebParam(name = "trainNum") String trainNum, @WebParam(name = 
	"dto") BasicTrainStationDTO dto);

    // 删除车站信息
    @WebResult(name = "removeBasicStationUnitInfoResult")
    ResultMessage removeBasicPlanStationInfo(@WebParam(name = "trainNum") String trainNum, @WebParam(name = 
	"stationName") String stationName);


获取列车里程计算用于用户添加车站时，自动计算添加车站到始发站的距离。  
**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

    // 获取车站里程计算
    @WebResult(name = "calculateMileageResult")
    float calculateMileage(@WebParam(name = "startStation") String startStation, @WebParam(name = 
	"presentStation") String presentStation);

> **说明**  
> 对于车站里程，是后台程序自动计算生成，只有当首次添加车站的时候，前台需要调用这个接口获取显示值，后续当从后台获取车站信息的时候，会直接从数据BasicTrainStationDTO获取到里程，无需再次调用接口。  
> 因此这个数值不允许用户进行编辑，前台直接设置为带有边框的TextBlock即可。

BasicTrainStationDTO是前后台关于车站信息的数据接口，其定义如下：

    public class BasicTrainStationDTO {
    /* 车站信息 */
    private String stationName;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 为了能够兼容跨天的车次，到达时间可以+1天 */
    //到达出发的相对天数
    private int arriveDelayDays;
    private int departureDelayDays;
    /* 计划股道号 */
    private int planedTrackNum;
    /* 从起始站的车站里程 */
    private float mileageFromStartStation;

> *注：  *  
> *2017/10/17 11:07:42*    
> 根据业务需求，将跨天调整为到达时间和发车时间独立累加偏移日期数，支持到达为今日，发车为明日这种特殊情况，同时支持一部分的到发时间在今日，另一半旅程的时间在下一日。


### 3. 客运组织业务模版页面接口 ###
客运组织业务模版页面接口主要提供客运组织业务模版的编辑、显示和筛选功能。

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

后台提供的接口如下：

    // 获取全部列车时刻表（配置基本图）
    // 根据列车车次和列车类型过滤列表，车次和列车类型允许为空，表示无此过滤条件
    // 默认采用模糊查询车次的方式
    @WebResult(name = "getOrganizeTemplateInterfaceResult")
    List<OrganizeTemplateDTO> getOrganizeTemplate(@WebParam(name = "stationName") String stationName, 
	@WebParam(name = "trainNum") String trainNum, @WebParam(name = "trainType") TrainTypeEnum trainType);

    // 修改基本图
    @WebResult(name = "modifyOrganizeTemplateResult")
    ResultMessage modifyOrganizeTemplate(@WebParam(name = "dto") OrganizeTemplateDTO dto);

    // 检查筛选未配置基本图的时刻表列表
    @WebResult(name = "getUnconfigedOrganizeTemplateResult")
    List<OrganizeTemplateDTO> getUnconfiguredOrganizeTemplate(@WebParam(name = "stationName") String 
	stationName);

其中TrainTypeEnum是前后台列车类型的数据接口，定义为：

    public enum TrainTypeEnum {
    HIGH_SPEED,
    INTERCITY,
    PASSENGER_SPECIAL,
    NORMAL,
    ALL;

如果选取全部类型，则需要将trainType设置为**TrainTypeEnum.ALL**。

OrganizeTemplateDTO是前后台数据接口，其定义为：

    public class OrganizeTemplateDTO {
    /***************** 列车时刻表信息 ******************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; // 判断是否有效
    /* 为了能够兼容跨天的车次，到达时间可以+1天 */
    private int delayDays;

    /* 模版对应的车站名称 */
    private String stationName;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 计划股道号 */
    private int planedTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /***************** 客运组织业务模版信息 ******************/
    /* 进站上车开检时间基准 */
    private TrainTimeBaseEnum startAboardCheckBase;
    /* 进站上车开检相对时间, 单位是分钟 */
    private int startAboardCheckTimeOffset;
    /* 进站上车停检时间基准, 单位是分钟 */
    private TrainTimeBaseEnum stopAboardCheckBase;
    /* 进站上车停检相对时间 */
    private int stopAboardCheckTimeOffset;
    // 候车区列表
    private List<String> waitZoneList;
    // 车站进站口
    private List<String> stationEntrancePort;
    // 车站出站口
    private List<String> stationExitPort;
    // 乘车检票口，如果在进站口开设检票口，则检票口与进站口会重合
    private List<String> aboardCheckGate;
    // 出站检票口，根据实际情况，出站检票口可能与车站出站口指同一位置
    private List<String> exitCheckGate;
    // 广播模版组名称
    private String broadcastTemplateGroupName;
    // 是否数据库中已经存在编辑过的信息
    private boolean modifiedFlag;
    }

当用户编辑保存模版信息的时候，修改的是**客运组织业务模版信息**中的内容。
> **Note：**
> 
> 前台需要对用户编辑数据进行基本的检测，相对时间单位是分钟，需要明确在界面标识。
> 
> 开检和停检的时间范围暂定为[-600,600]，即±10小时。
> 
> 广播组名称必须为非空（后台提供的默认值为default，但是用户编辑后不能为default），否则不能成功保存编辑。
> 界面最好带有一定的智能提醒功能，一旦广播组名称为空，则属性颜色和textbox边框变为红色。
> 
> 广播组名称需要在广播接口中获取。

获取广播组名称接口定义如下：

    // 获取广播组列表
    @WebResult(name = "getBroadcastGroupListResult")
    List<BroadcastTemplateGroupDTO> getBroadcastGroupList(@WebParam(name = "stationName") String stationName, 
	@WebParam(name = "kind") BroadcastKindEnum kind);

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

其中BroadcastKindEnum是前后台关于广播类型的数据接口，其定义为：

    public enum BroadcastKindEnum {
    ARRIVE_DEPARTURE,   // 到发
    ALTERATION,  // 变更
    TRAIN_MANUAL,  // 车次
    OTHERS,  // 其他
    ALL;  //全部

在本界面需要将kind赋值为ARRIVE_DEPARTURE

获取到的广播组列表数据类型BroadcastTemplateGroupDTO的定义如下：

    public class BroadcastTemplateGroupDTO {
    // 数据库ID
    private long id;
    // 广播组名称
    private String templateGroupName;
    // 广播组所属车站站名
    private String stationName;
    // 广播属性：到发或者变更 onArrive, alteration
    private BroadcastKindEnum broadcastKind;
    // 是否可以修改，1表示可以修改，0表示不可修改
    private int revisability;

前台需要绑定的广播模版组名称为属性templateGroupName。

**接口位置：** [/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

### 4. 调度计划页面对应接口 ###
调度计划接口主要提供计划的显示和修改功能。

接口定义如下：

    // 获取调度计划信息
    // 对车次号支持模糊查询
    @WebResult(name = "getPeriodDispatchPlanResult")
    List<DispatchPlanDTO> getPeriodDispatchPlan(@WebParam(name = "stationName") String stationName, 
	@WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam
	(name = "endDate") String endDate);

    // 修改调度计划信息
    @WebResult(name = "updateDispatchPlanResult")
	ResultMessage updateDispatchPlan(@WebParam(name = "planKey") String planKey, @WebParam(name = 
	"modifyList") HashMap<DispatchPlanModifyEnum, String> modifyList, @WebParam(name = 
	"arriveTimeModifyReason") LateEarlyReasonEnum arriveTimeModifyReason, @WebParam(name = 
	"departureTimeModifyReason") LateEarlyReasonEnum departureTimeModifyReason);

> **Note:**  
> 修改计划信息的时候，需要前台判断修改后的信息是否与修改前的信息一致，如果信息一致，则需要提醒用户修改无效。  
> 另外需要注意的是获取列车时刻表的时候不要使站名为null，否则会返回空列表。

其中DispatchPlanDTO为前后台的数据接口，其定义如下：
    
    /* plan key */
    private String planKey;
    /*************   车次信息   ****************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 计划日期 */
    private String planDate;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;

    /*************** 有效期及停开信息 ******************/
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; //自动计算
    /* 停开起止 */
    private boolean manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /* 计划股道,来自列车时刻表 */
    private int planedTrackNum;
    /* 计划股道,来自界面修改 */
    private int actualTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的实际出发时间 */
    private String actualDepartureTime;
    /* 列车实际出发时间状态 */
    private TrainTimeStateEnum departureTimeState; //自动计算
    /* 列车实际出发时间早点或晚点原因 */
    private LateEarlyReasonEnum departureStateReason;

    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 列车在当前车站的实际到达时间 */
    private String actualArriveTime;
    /* 列车实际到达时间状态 */
    private TrainTimeStateEnum arriveTimeState; //自动计算
    /* 列车实际到达时间早点或晚点原因 */
    private LateEarlyReasonEnum arriveStateReason;

    /**************** 记录是否修改 *****************/
    private boolean PlanEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; //判断计划是否已经失效

在修改接口中，包含一个前后台数据约定项：

    public enum DispatchPlanModifyEnum {
    ACTUAL_DEPARTURE_TIME,
    ACTUAL_ARRIVE_TIME,
    ACTUAL_TRACK_NUM,
    MANUAL_SUSPEND
    }

通过枚举类型，限定了前台能够修改的数据属性，前台需要将<修改属性，修改后的值>通过Dictionary的方式传递给后台，修改后的值只接受string类型，因此需要做相应的装换。

> **Note： 当前台修改的对象为List数组时，需要将List数组转换为Json数据字符串，再保存到Dictionary中传递给后台。** 

调度计划生成notice接口定义如下：

    @WebResult(name = "generateDispatchPlanModifyNoticeResult")
    Map<ReceiverEnum, ResultMessage> generateDispatchPlanModifyNotice(@WebParam(name = "user") String user, 
	@WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") 
	HashMap<DispatchPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList);

其中ReceiverEnum为前后台数据约定，表示可以接收notice的对象，定义如下：

    public enum ReceiverEnum {
    DISPATCH_PLAN,
    PASSENGER_PLAN,
    GUIDE_PLAN,
    BROADCAST_PLAN
	}

在此处，前台只能选择后三项。

调度页面在修改列车到发时间的时候，调用后台系统配置信息获取列车晚点原因。

### 5. 客运计划页面接口 ###
客运计划接口主要提供计划的显示和修改功能。

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

接口定义如下：
    
    // 获取调度计划信息
    // 对车次号支持模糊查询
    @WebResult(name = "getPeriodPassengerPlanResult")
    List<PassengerStationPlanDTO> getPeriodPassengerPlan(@WebParam(name = "stationName") String stationName, 
	@WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam
	(name = "endDate") String endDate);

    // 对调度计划进行修改
    // 修改调度计划信息
    @WebResult(name = "updatePassengerPlanResult")
    ResultMessage updatePassengerPlan(@WebParam(name = "planKey") String planKey, @WebParam(name = 
	"modifyList") HashMap<PassengerPlanModifyEnum, String> modifyList);

> **Note:**  
> 修改计划信息的时候，需要前台判断修改后的信息是否与修改前的信息一致，如果信息一致，则需要提醒用户修改无效。  
> 另外需要注意的是获取列车时刻表的时候不要使站名为null，否则会返回空列表。

其中PassengerStationPlanDTO为前后台的数据接口，其定义如下：

    public class PassengerStationPlanDTO {
    /* plan key */
    private String planKey;
    /*************   车次信息   ****************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 计划日期 */
    private String planDate;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;

    /*************** 有效期及停开信息 ******************/
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; //自动计算
    /* 停开起止 */
    private boolean manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /* 计划股道,来自列车时刻表 */
    private int planedTrackNum;
    /* 计划股道,来自界面修改 */
    private int actualTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的实际出发时间 */
    private String actualDepartureTime;
    /* 列车实际出发时间状态 */
    private TrainTimeStateEnum departureTimeState; //自动计算
    /* 列车实际出发时间早点或晚点原因 */
    private LateEarlyReasonEnum departureStateReason;

    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 列车在当前车站的实际到达时间 */
    private String actualArriveTime;
    /* 列车实际到达时间状态 */
    private TrainTimeStateEnum arriveTimeState; //自动计算
    /* 列车实际到达时间早点或晚点原因 */
    private LateEarlyReasonEnum arriveStateReason;

    /**************** 记录是否修改 *****************/
    private boolean PlanEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; //判断计划是否已经失效

    /**************** 检票信息 *****************/
    /* 进站上车开检时间基准 */
    private TrainTimeBaseEnum startAboardCheckBase;
    /* 进站上车开检相对时间, 单位是分钟 */
    private int startAboardCheckTimeOffset;
    /* 手动检票时间设置，允许直接修改 */
    private String startAboardCheckTime;

    /* 进站上车停检时间基准, 单位是分钟 */
    private TrainTimeBaseEnum stopAboardCheckBase;
    /* 进站上车停检相对时间 */
    private int stopAboardCheckTimeOffset;
    /* 手动检票时间设置，允许直接修改 */
    private String stopAboardCheckTime;

    /*
     * 当前是否处于检票状态
     * 属性自动计算
     */
    private CheckStateEnum checkState;

    /**************** 区域信息 *****************/
    // 候车区列表
    private List<String> waitZone;
    // 车站进站口
    private List<String> stationEntrancePort;
    // 车站出站口
    private List<String> stationExitPort;
    // 乘车检票口，如果在进站口开设检票口，则检票口与进站口会重合
    private List<String> aboardCheckGate;
    // 出站检票口，根据实际情况，出站检票口可能与车站出站口指同一位置
    private List<String> exitCheckGate;

在修改接口中，包含一个前后台数据约定项：

    public enum PassengerPlanModifyEnum {
    START_CHECK_TIME,
    STOP_CHECK_TIME,
    WAIT_ZONE,
    ENTRANCE_PORT,
    EXIT_PORT,
    ABOARD_CHECK_GATE,
    EXIT_CHECK_GATE
	}

通过枚举类型，限定了前台能够修改的数据属性，前台需要将<修改属性，修改后的值>通过Dictionary的方式传递给后台，修改后的值只接受string类型，因此需要做相应的装换。

> **Note： 当前台修改的对象为List数组时，需要将List数组转换为Json数据字符串，再保存到Dictionary中传递给后台。** 

调度计划生成notice接口定义如下：

    @WebResult(name = "generatePassengerPlanModifyNoticeResult")
    Map<ReceiverEnum, ResultMessage> generatePassengerPlanModifyNotice(@WebParam(name = "user") String user, 
	@WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") 
	HashMap<PassengerPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList);

其中ReceiverEnum为前后台数据约定，表示可以接收notice的对象，定义如下：

    public enum ReceiverEnum {
    DISPATCH_PLAN,
    PASSENGER_PLAN,
    GUIDE_PLAN,
    BROADCAST_PLAN
	}

在此处，前台只能选择后两项。

### 6. 导向计划页面接口 ###
客运计划接口主要提供计划的显示和修改功能。

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

接口定义如下：

    // 获取调度计划信息
    // 对车次号支持模糊查询
    @WebResult(name = "getPeriodGuidePlanResult")
    List<GuideStationPlanDTO> getPeriodGuidePlan(@WebParam(name = "stationName") String stationName, 
	@WebParam(name = "trainNum") String trainNum, @WebParam(name = "startDate") String startDate, @WebParam
	(name = "endDate") String endDate);

    // 修改调度计划信息
    @WebResult(name = "updateGuidePlanResult")
    ResultMessage updateGuidePlan(@WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") 
	HashMap<GuidePlanModifyEnum, String> modifyList, @WebParam(name = "arriveTimeModifyReason") 
	LateEarlyReasonEnum arriveTimeModifyReason, @WebParam(name = "departureTimeModifyReason") 
	LateEarlyReasonEnum departureTimeModifyReason);

> **Note:**  
> 修改计划信息的时候，需要前台判断修改后的信息是否与修改前的信息一致，如果信息一致，则需要提醒用户修改无效。  
> 另外需要注意的是获取列车时刻表的时候不要使站名为null，否则会返回空列表。

其中PassengerStationPlanDTO为前后台的数据接口，其定义如下：

    public class GuideStationPlanDTO {
    /* plan key */
    private String planKey;
    /*************   车次信息   ****************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 计划日期 */
    private String planDate;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;

    /*************** 有效期及停开信息 ******************/
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; //自动计算
    /* 停开起止 */
    private boolean manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /* 计划股道,来自列车时刻表 */
    private int planedTrackNum;
    /* 计划股道,来自界面修改 */
    private int actualTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的实际出发时间 */
    private String actualDepartureTime;
    /* 列车实际出发时间状态 */
    private TrainTimeStateEnum departureTimeState; //自动计算
    /* 列车实际出发时间早点或晚点原因 */
    private LateEarlyReasonEnum departureStateReason;

    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 列车在当前车站的实际到达时间 */
    private String actualArriveTime;
    /* 列车实际到达时间状态 */
    private TrainTimeStateEnum arriveTimeState; //自动计算
    /* 列车实际到达时间早点或晚点原因 */
    private LateEarlyReasonEnum arriveStateReason;

    /**************** 记录是否修改 *****************/
    private boolean PlanEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; //判断计划是否已经失效

    /**************** 检票信息 *****************/
    /* 进站上车开检时间基准 */
    private TrainTimeBaseEnum startAboardCheckBase;
    /* 进站上车开检相对时间, 单位是分钟 */
    private int startAboardCheckTimeOffset;
    /* 手动检票时间设置，允许直接修改 */
    private String startAboardCheckTime;

    /* 进站上车停检时间基准, 单位是分钟 */
    private TrainTimeBaseEnum stopAboardCheckBase;
    /* 进站上车停检相对时间 */
    private int stopAboardCheckTimeOffset;
    /* 手动检票时间设置，允许直接修改 */
    private String stopAboardCheckTime;

    /*
     * 当前是否处于检票状态
     * 属性自动计算
     */
    private CheckStateEnum checkState;

    /**************** 区域信息 *****************/
    // 候车区列表
    private List<String> waitZone;
    // 车站进站口
    private List<String> stationEntrancePort;
    // 车站出站口
    private List<String> stationExitPort;
    // 乘车检票口，如果在进站口开设检票口，则检票口与进站口会重合
    private List<String> aboardCheckGate;
    // 出站检票口，根据实际情况，出站检票口可能与车站出站口指同一位置
    private List<String> exitCheckGate;

导向页面在修改列车到发时间的时候，调用后台系统配置信息获取列车晚点原因。


### 7. 广播计划页面接口 ###
广播计划接口主要提供计划的显示和修改功能，修改包括执行模式、执行时间的修改。
#### 7.1 到发/变更广播计划页面接口 ####
**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

计划相关的接口定义如下：

    // 获取广播计划信息,分为到发广播计划和变更广播计划
    // 对车次号支持模糊查询
    @WebResult(name = "getPeriodGuidePlanResult")
    List<BroadcastStationPlanDTO> getPeriodBroadcastPlan(@WebParam(name = "stationName") 
	String stationName, @WebParam(name = "trainNum") String trainNum, @WebParam(name = 
	"broadcastKind") BroadcastKindEnum broadcastKind, @WebParam(name = "startDate") 
	String startDate, @WebParam(name = "endDate") String endDate);

    // 修改广播计划执行时间
    @WebResult(name = "modifyBroadcastPlanExecuteTimeResult")
    ResultMessage modifyBroadcastPlanExecuteTime(@WebParam(name = "planKey") String planKey, 
	@WebParam(name = "executeTime") String executeTime);

    // 修改广播计划执行区域
    @WebResult(name = "modifyBroadcastPlanExecuteAreaResult")
    ResultMessage modifyBroadcastPlanExecuteArea(@WebParam(name = "planKey") String planKey, 
	@WebParam(name = "executeArea") List<String> executeArea);

    // 将一个车站某日未执行的到发广播计划调整为手动模式
    @WebResult(name = "changePeriodBroadcastPlanModeToManualModeResult")
	GroupResultMessage changePeriodBroadcastPlanModeToManualMode(@WebParam(name = "stationName") 
	String stationName, @WebParam(name = "startDate") String startDate, 
	@WebParam(name = "endDate") String endDate);

    // 将一个车站某日未执行的到发广播计划调整为自动模式
    @WebResult(name = "changePeriodBroadcastPlanModeToAutoModeResult")
    GroupResultMessage changePeriodBroadcastPlanModeToAutoMode(@WebParam(name = "stationName") 
	String stationName, @WebParam(name = "startDate") String startDate, @WebParam(name = 
	"endDate") String endDate);

    // 将一个单条的到发广播计划执行模式调整为手动模式
    @WebResult(name = "changePeriodBroadcastPlanModeToManualModeResult")
    ResultMessage changeSingleBroadcastPlanModeToManualMode
	(@WebParam(name = "planKey") String planKey);

    // 将一个单条的到发广播计划执行模式调制为手动模式
    @WebResult(name = "changePeriodBroadcastPlanModeToAutoModeResult")
    ResultMessage changeSingleBroadcastPlanModeToAutoMode
	(@WebParam(name = "planKey") String planKey);

其中GroupResultMessage是前后台数据约定：

    public class GroupResultMessage extends ResultMessage {
        // 状态码
        private int statusCode;
        // 结果
        private boolean result;
        // 携带信息
        private String message;
        // 详细返回值结果
        private HashMap<String, Boolean> resultMap;
    }

其中hasMap中存放的是组操作结果，当其中有一个为false时，result为false。

> **Note:**  
> 修改计划信息的时候，需要前台判断修改后的信息是否与修改前的信息一致，如果信息一致，则需要提醒用户修改无效。  
> 另外需要注意的是获取列车时刻表的时候不要使站名为null，否则会返回空列表。

通过getPeriodBroadcastPlan()接口可以实现对到发广播计划和变更广播计划的搜索显示，对车次的输入采用模糊搜索的方式进行，计划日期起止为同一天的时候，表示指定单日。

变更广播计划为系统自动生成，需要手动执行，无法改变广播模式，界面上不要显示广播执行时间、基准等相关数据。对于变更广播计划只能调整广播区域。

计划中的广播状态为广播接口机根据执行情况进行回写（与广播记录中的状态是同一个状态），因此界面需要刷新计划状态，才能更新每条广播计划的执行状态。

作业区域修改显示的区域为广播区列表。

> **注意**  
> 本次允许单条广播计划直接修改广播执行时间，因此可能会与通过notice接收出发和终到修改有覆盖冲突。以及与客票系统推送的检票时间和停止检票时间会发生冲突。  
> 前台在设计逻辑的时候，如果接收到出发、终到时间修改notice的时候，需要提醒用户修改列车到发时间可能会引起检票时间的连带修改，询问用户是否接受对检票时间的修改。

其中BroadcastStationPlanDTO为前后台的数据接口，其定义如下：

	/* plan key */
    private String planKey;
    /*************   车次信息   ****************/
    /* 列车车次号 */
    private String trainNum;
    /* 列车车次号，暂时备用 */
    private String uniqueTrainNum;
    /* 列车类型 */
    private TrainTypeEnum trainType;
    /* 计划日期 */
    private String planDate;
    /* 列车在当前车站是始发、通过还是终到 */
    private StationTypeEnum stationType;
    /* 列车开行方向 */
    private TrainDirectionEnum trainDirection;
    /* 始发站 */
    private String startStation;
    /* 终到站 */
    private String finalStation;

    /*************** 有效期及停开信息 ******************/
    /* 有效期起止 */
    private String validPeriodStart;
    private String validPeriodEnd;
    private boolean validFlag; //自动计算
    /* 停开起止 */
    private boolean manualSuspendFlag;

    /**************** 列车运行计划信息 *****************/
    /* 计划股道,来自列车时刻表 */
    private int planedTrackNum;
    /* 计划股道,来自界面修改 */
    private int actualTrackNum;
    /* 停靠站台 */
    private String dockingPlatform; //自动计算

    /* 列车在当前车站的计划出发时间 */
    private String planedDepartureTime;
    /* 列车在当前车站的实际出发时间 */
    private String actualDepartureTime;
    /* 列车实际出发时间状态 */
    private TrainTimeStateEnum departureTimeState; //自动计算
    /* 列车实际出发时间早点或晚点原因 */
    private LateEarlyReasonEnum departureStateReason;

    /* 列车在当前车站的计划到达时间 */
    private String planedArriveTime;
    /* 列车在当前车站的实际到达时间 */
    private String actualArriveTime;
    /* 列车实际到达时间状态 */
    private TrainTimeStateEnum arriveTimeState; //自动计算
    /* 列车实际到达时间早点或晚点原因 */
    private LateEarlyReasonEnum arriveStateReason;

    /**************** 记录是否修改 *****************/
    private boolean PlanEditedState; // 计划是否被修改过,自动计算
    private boolean overTimeFlag; //判断计划是否已经失效

    /**************** 检票信息 *****************/
    /* 手动检票时间设置，允许直接修改 */
    private String startAboardCheckTime;

    /* 手动检票时间设置，允许直接修改 */
    private String stopAboardCheckTime;

    /*
     * 当前是否处于检票状态
     * 属性自动计算
     */
    private CheckStateEnum checkState;

    /**************** 广播业务内容 **********************/
    // 广播执行时间，可以手动设置
    private String broadcastTime;

    // 作业内容->广播内容模版名称
    private String broadcastContentName;
    // 作业模式
    private BroadcastModeEnum broadcastOperationMode;
    // 优先级
    private int broadcastPriorityLevel;
    // 广播区列表<-根据广播业务模版生成
    private List<String> broadcastArea;

    // 广播计划执行状态
    private BroadcastStateEnum broadcastState;

**广播执行接口：**

广播执行接口是指手动执行一条广播或者停止一条广播的播放，执行广播的作用对象是一条执行模式设置为“手动”的广播计划，停止广播的对象有两个：一个是单条的正在播放或者在队列中的广播记录（一条广播执行一次就产生一条广播记录，同一条广播可以多次执行，产生多个广播记录），另一个是停止某条广播计划的播放，停止该计划的播放会导致全部未执行的播放记录均停止执行，例如一条自动广播计划先调整为手动模式，执行了两次，然后再次调整为自动模式，此时对于该条广播计划而言，有一条正在执行的广播，有一条等待执行的广播，还有一条计划执行的广播，停止该计划的广播执行会终止以上三个执行中/未执行的广播播放。

广播执行接口定义的位置为：
[/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

接口定义为：

    // 手动播放广播计划
    @WebResult(name = "makeManualBroadcastResult")
    ResultMessage makeManualBroadcast(@WebParam(name = "planKey") String planKey);

    // 停止单条广播计划的播放
    // 由于一条广播计划可能被执行多次，因此界面需要提醒用户相关广播都会停止
    @WebResult(name = "stopSingleBroadcastResult")
    HashMap<String, ResultMessage> stopBroadcastPlan(@WebParam(name = "planKey") 
    String planKey);

    // 针对广播记录，停止单条广播的播放
    @WebResult(name = "stopSingleBroadcastResult")
    ResultMessage stopSingleBroadcast(@WebParam(name = "recordKey") String recordKey);

> **说明：**  
> 停止单条广播计划播放和停止广播记录对应的广播播放的区别在于：    
> 每执行一次广播计划，都会产生一条相应的广播记录，此时如果用户需要停止针对该条计划的全部广播，则执行针对广播计划的停止接口；
> 对于每一条广播播放执行，用户进行停止操作的接口是第二个接口，即针对广播记录操作的停止接口。
>
> 使用第一个针对广播计划的停止播放接口，相当于进行了一个组操作。 

本接口新加入了停止广播计划播放的接口，因此需要提醒用户，停止计划会导致相关的播放都取消，包括手动广播。

另外注意停止单条广播执行是针对广播记录的，允许操作的对象是正在执行或者队列中的广播计划，调用后台接口传递的参数是recordKey，而停止广播计划播放的操作针对的是计划，传递的参数是planKey。

后台定义的广播状态如下：

    public enum BroadcastStateEnum {
    // 后台执行幅值
    WAIT_EXECUTE,  // 待执行
    MANUAL_STOP, // 手动停止播放
    CONTENT_EMPTY_QUIT,  // 广播内容为空
    BROADCAST_AREA_EMPTY_QUIT, //广播区列表空

    // 驱动回写幅值
    IN_QUEUE,  // 队列中，调用驱动后接口机首先回写任务进入队列
    BROADCASTING,  // 播放中
    TIME_OUT_QUIT,  // 超时
    ERROR_QUIT,  // 错误退出
    COMPLETED  // 播放完毕
    }

#### 7.2 车次广播 ####
对于车次广播页面，接口位置为：
[/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

    // 播放车次广播计划
    @WebResult(name = "makeTrainManualBroadcastResult")
    ResultMessage makeTrainManualBroadcast(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "trainNum") String trainNum, @WebParam(name = "localContent") 
    String localContent, @WebParam(name = "EngContent") String EngContent, 
    @WebParam(name = "broadcastArea") List<String> broadcastArea, 
    @WebParam(name = "priorityLevel") int priorityLevel);

    // 获取车次广播界面下的列车车次列表
    @WebResult(name = "getTrainNumListResult")
    List<String> getTrainNumList(@WebParam(name = "stationName") String stationName);

    // 获取车次广播的优先级配置列表
    @WebResult(name = "getPriorityListResult")
    List<Integer> getPriorityList(@WebParam(name = "stationName") String stationName);

首先车次列表指的是当前存在客运计划的列车车次，因为广播播放过程中，替换标签所采用的属性来自于当天客运计划，因此车次列表实际为客运计划车次列表。

车次广播界面的作业区域指的是广播区，因此提供给用户编辑的是广播区列表。

播放车次广播的各个参数都不能为空，界面需要进行校验，保证参数有效。

车次广播没有广播计划，但是有广播记录，因此可以通过广播记录停止一条正在播放的车次广播。

车次广播获取指定种类的广播词接口如下：

    // 获取到发/变更/其他广播词列表
    @WebResult(name = "getNormalContentResult")
    List<NormalBroadcastContentDTO> getNormalContent(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

其中BroadcastKindEnum是前后台约定的数据契约，其定义为：

    public enum BroadcastKindEnum {
        ARRIVE_DEPARTURE,   // 到发
        ALTERATION,  // 变更
        TRAIN_MANUAL,  // 车次
        OTHERS,  // 其他
        ALL;  //全部
    }

一般根据业务，界面使用的广播词为到发、变更和车次即可。

返回值为广播词列表，其定义为：

    public class NormalBroadcastContentDTO {
    // id
    private long id;
    // 广播内容名称 -> 作业内容(广播业务模版)
    private String contentName;
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 站名
    private String stationName;
    // 本地语言广播详细内容
    private String contentInLocalLan;
    // 英文广播详细内容
    private String contentInEng;

#### 7.3 广播记录接口 ####
广播记录接口位置为：
[/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

接口定义如下：

    // 根据站名获取广播记录列表
    // 需要指定起始日期和结束日期
    @WebResult(name = "getBroadcastRecordResult")
    List<BroadcastRecordDTO> getBroadcastRecord(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "startDate") 
    String startDate, @WebParam(name = "endDate") String endDate);

其中BroadcastRecordDTO是前后台关于广播记录的数据接口，其定义如下：

    public class BroadcastRecordDTO {
    // 广播计划key
    // key ->关联单条广播计划的key，车次广播计划也根据规则拼接出相应的key
    private String key;
    // 车次
    private String trainNum;
    // 广播类型
    private BroadcastKindEnum broadcastKind;
    // 执行模式
    private BroadcastModeEnum broadcastMode;
    // 计划执行时间
    private String planedBroadcastTime;
    // 实际执行时间
    private String actualBroadcastTime;
    // 广播区域
    private List<String> broadcastArea;
    // 广播内容
    private String broadcastContent;
    // 执行状态：unexecuted: 未执行， wait:等待播放，active:正在播放，finish：执行完成, fail：执行失败
    private BroadcastStateEnum operationState;
    // 广播优先级
    private int priorityLevel;
    }

后台定义的广播状态如下：

    public enum BroadcastStateEnum {
    // 后台执行幅值
    WAIT_EXECUTE,  // 待执行
    MANUAL_STOP, // 手动停止播放
    CONTENT_EMPTY_QUIT,  // 广播内容为空
    BROADCAST_AREA_EMPTY_QUIT, //广播区列表空

    // 驱动回写幅值
    IN_QUEUE,  // 队列中，调用驱动后接口机首先回写任务进入队列
    BROADCASTING,  // 播放中
    TIME_OUT_QUIT,  // 超时
    ERROR_QUIT,  // 错误退出
    COMPLETED  // 播放完毕
    }

### 8. 生成计划页面接口 ###
生成计划页面对应的接接口如下：

    // 查询可以生成计划的时刻表
    // 即已经配置了客运组织业务模版
    // 允许车次号模糊查询
    @WebResult(name = "getValidBasicPlanResult")
    public List<BasicPlanDTO> getValidBasicPlan(@WebParam(name = "trainNum")
	String trainNum, @WebParam(name = "stationName") String stationName);  

    // 批量生成计划
    @WebResult(name = "generatePlanListResult")
    public HashMap<String, String> generatePlanList(@WebParam(name = "trainNumList")
	List<String> trainNumList, @WebParam(name = "stationName") String stationName, 
	@WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);  

    // 批量删除计划
    @WebResult(name = "delPlanListResult")
    public HashMap<String, String> delPlanList(@WebParam(name = "trainNumList") 
	List<String> trainNumList, @WebParam(name = "stationName") String stationName, 
	@WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate);  

> **Note：**
> 前台需要对生成计划参数进行约束，包括  
> - 生成开始时间不能在生成截止时间之后；  
> - 生成计划的最长时间为30天（后续根据情况调整）；  
> - 列车车次最大不超过10次。  
> - 不支持重复生成计划，即如果已经存在计划，则不再生成。如果想重新生成计划，需要首先删除计划，然后再生成。

生成计划和删除计划返回值为key-value结构，key为生成计划的plankey，value为生成结果，如果出错包含错误原因。

### 9. Notice功能接口 ###

**接口位置：** [/Service/PlanService](http://xxx/Service/PlanService "/Service/PlanService")

#### 9.1 notice本身操作接口 ####
Notice功能为调度计划、客运计划、导向计划、广播计划提供公共方法如下：

    // 获取全部未处理notice
    @WebResult(name = "getUnhandledNoticeListResult")
    List<NoticeMessageDTO> getUnhandledNoticeList();

    // 获取指定计划的未处理notice
    @WebResult(name = "getNoticeMessageByStationResult")
    List<NoticeMessageDTO> getNoticeMessageByStation(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "pageName") ReceiverEnum pageName, @WebParam(name = "startDate") String startDate, 
    @WebParam(name = "endDate") String endDate);

    // 获取指定计划notice处理记录
    @WebResult(name = "getOperationLogByStationResult")
    List<OperationLogDTO> getOperationLogByStation(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "pageName") ReceiverEnum pageName, @WebParam(name = "startDate") String startDate, 
    @WebParam(name = "endDate") String endDate);

    // 接收noticce
    @WebResult(name = "acceptNoticeResult")
    GroupResultMessage acceptNotice(@WebParam(name = "info") String processUser, 
    @WebParam(name = "messageId") ArrayList<Long> idList, 
    @WebParam(name = "pageName") ReceiverEnum pageName);

    // 拒绝notice
    @WebResult(name = "rejectNoticeResult")
    GroupResultMessage rejectNotice(@WebParam(name = "info") String processUser, 
    @WebParam(name = "messageId") ArrayList<Long> idList);

    // 开启自动接收客票系统修改
    @WebResult(name = "startAutoProcessTicketNoticeResult")
    ResultMessage startAutoProcessTicketNotice();

    // 停止自动接收客票系统修改
    @WebResult(name = "stopAutoProcessTicketNoticeResult")
    ResultMessage stopAutoProcessTicketNotice();

    // 开启自动接收CTC系统修改
    @WebResult(name = "startAutoProcessCTCNoticeResult")
    ResultMessage startAutoProcessCTCNotice();

    // 停止自动接收CTC系统修改
    @WebResult(name = "stopAutoProcessCTCNoticeResult")
    ResultMessage stopAutoProcessCTCNotice();

> 前台需要将user设置为当前登录用户名。  
> pageName表示接收notice的plan，例如在导向计划页面，将pageName设置为ReceiverEnum.GUIDE_PLAN即可。

其中NoticeMessageDTO和OperationLogDTO为前后台数据接口，其定义分别如下：

NoticeMessageDTO是未处理notice信息：

    public class NoticeMessageDTO {
    /********** notice消息头 ***********/
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;
    /* 产生notice消息的时间戳 */
    private String generateTimeStamp;

    private ArrayList<SingleNoticeMessageDTO> noticeList;

SingleNoticeMessageDTO是单条的notice信息，NoitceMessageDTO可以存放一组来自同一个修改源的多个修改。

	public class SingleNoticeMessageDTO {
    /* ID */
    private long id;
    /* notice描述 */
    private String description;
    /*********** notice消息体 ************/
    private String trainNum;
    /************ notice状态  ************/
    // 如果notice携带的修改内容已经与plan相同，则认为修改无效
    // 如果修改对象已经不存在，则认为notice已经失效
    // 如果修改对象（计划）已经无效，则认为notice也随之失效
    private boolean overTimeFlag;

GroupResultMessage是接收和发送一组noitce的返回值，其定义为：

    public class GroupResultMessage{
    // 状态码
    private int statusCode;
    @TranslateAttribute
    // 携带信息
    private String message;
    // 操作结果
    private boolean result;
    // 一组操作结果，存放在map中
    private HashMap<String, Boolean> resultMap;

当一组操作结果全部为true的时候，操作结果即为true；否则如果有一个操作为false，则返回结果为false。

> **overTimeFlag说明**  
> 对于未处理notice信息中的overTimeFlag, 每次前台调用后台接口的时候，都会检查该notice的有效状态，后台将notice的状态分成了如下几个情况：  
>   VALID,  // notice有效  
PLAN_OUT_OF_DATE, // 修改的计划已经过期  
PLAN_NO_EXIT, // 计划已经不存在  
SAME_WITH_PLAN  // notice修改内容与plan内容一样   
> 后续如果前台显示需要扩展，可以考虑将boolean替换为ValidStateEnum。

OperationLogDTO是已经处理过的历史信息：

    /********** notice消息头 ***********/
    /* ID */
    private long id;
    /* 生成notice的源 */
    private SenderEnum sender;
    /* 生成notice消息的用户，也可能是系统 */
    private String generateUser;
    /* 产生notice消息的时间戳 */
    private String generateTimeStamp;
    /* notice描述 */
    private String description;
    /* 消息处理人（ip地址或者用户） */
    private String processUser;
    /* 消息处理的时间 */
    private String processTime;
    /* notice消息处理装态 */
    private ProcessStateEnum rocessState;

    /*********** notice消息体 ************/
    private String trainNum;

ProcessStateEnum是前后台数据接口，表示用户在处理notice的时候，是选择了接受还是拒绝。
    
    public enum ProcessStateEnum {
    UN_HANDLE,
    REJECT,
    ACCEPT
    }

在log中不允许出现UN_HANDLE的状态，只有处理过的message才有log。

#### 9.2 修改计划相关的接口 ####
在导向页面和客运计划页面修改计划后，将修改下发到其他计划的接口如下：

    /**
     * 将导向页面生成的修改作为notice发送到其他页面
     * @param user
     * @param planKey
     * @param modifyList
     * @param receiverList
     * @return
     */
    @WebResult(name = "generateDispatchPlanModifyNoticeResult")
    HashMap<ReceiverEnum, ResultMessage> generateDispatchPlanModifyNotice(@WebParam(name = "user") 
    String user, @WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") 
    HashMap<DispatchPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList);

    /**
     * 将客运页面生成的修改作为notice发送到其他页面
     * @param user
     * @param planKey
     * @param modifyList
     * @param receiverList
     * @return
     */
    @WebResult(name = "generatePassengerPlanModifyNoticeResult")
    HashMap<ReceiverEnum, ResultMessage> generatePassengerPlanModifyNotice(@WebParam(name = "user") 
    String user, @WebParam(name = "planKey") String planKey, @WebParam(name = "modifyList") 
    HashMap<PassengerPlanModifyEnum, String> modifyList, List<ReceiverEnum> receiverList);

    /**
     * 将客运计划页面的notice转发给导向计划和广播计划
     * @param user
     * @param dto
     * @param receiverList
     * @return
     */
    @WebResult(name = "forwardPassengerPlanNoticeResult")
    HashMap<ReceiverEnum, ResultMessage> forwardPassengerPlanNotice(@WebParam(name = "user")String user, 
    NoticeMessageDTO dto, @WebParam(name = "receiverList") List<ReceiverEnum> receiverList);

其中第三个接口对应的业务逻辑是在客运计划页面将收到的notice转发给导向计划和广播计划。该业务场景发生在当调度计划只向客运计划发送修改noitce信息，而客运计划操作人员希望将修改同步到导向或者广播计划中。``

其中receiverList是指需要接收修改的计划类型，定义如下：

    public enum ReceiverEnum {
    DISPATCH_PLAN,
    PASSENGER_PLAN,
    GUIDE_PLAN,
    BROADCAST_PLAN
    }

### 10. 导向业务规则 ###
导向业务规则主要负责各种类型屏幕的导向规则编辑，导向规则采用统一的基于基准加偏移量的计算方式。

需要对原有界面进行修改，去掉设备列表和屏幕展示，直接将规则设置布置在界面上。

导向业务规则后台接口主要提供增删改查服务，去掉原来的列表操作模式，直接对单条规则进行数据库操作。

接口位置：
[/Service/GuideService](http://xxx/Service/GuideService "/Service/GuideService")

接口定义如下：
    
    // 获取导向规则列表
    @WebResult(name = "getScreenGuideRuleListResult")
    List<ScreenGuideRuleDTO> getScreenGuideRuleList(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "type") ScreenTypeEnum type);

    // 编辑导向规则
    @WebResult(name = "modifyScreenGuideRuleResult")
    ResultMessage modifyScreenGuideRule(@WebParam(name = "dto") ScreenGuideRuleDTO dto);

    // 增加导向规则
    @WebResult(name = "addScreenGuideRuleResult")
    ResultMessage addScreenGuideRule(@WebParam(name = "dto") ScreenGuideRuleDTO dto);

    // 删除导向规则
    @WebResult(name = "delScreenGuideRuleResult")
    ResultMessage delScreenGuideRule(@WebParam(name = "id") long id);

其中导向规则ScreenGuideRuleDTO是前后台的数据接口，其定义如下：

    public class ScreenGuideRuleDTO {
    /* 列车类型 */
    protected TrainTypeEnum trainType;
    /* 列车在当前车站是始发、通过还是终到 */
    protected StationTypeEnum stationType;
    // 自增长ID
    @OrmIgnore
    private long id;
    // 显示规则归属车站
    private String stationName;
    // 屏幕类型
    private ScreenTypeEnum screenType;
    // 上屏时间基准：arriveTime:到点 departureTime: 发点
    private TrainTimeBaseEnum upTimeReference;
    // 上屏相对时间（可是负值，时间单位：分钟）
    private int upTimeOffset;
    // 下屏时间基准 arriveTime: 到点 departureTime：发点
    private TrainTimeBaseEnum downTimeReference;
    // 下屏相对时间（可是负值，时间单位：分钟）
    private int downTimeOffset;

上下屏相对时间的范围是-10h ~ +10h。前台需要进行约束。

在一个车站，列车类型和车站类型的组合必须是唯一的，否则数据库会报错，数据库已经加上了唯一约束。

另外需要根据车站类型限制用户可以选择的到发点时间基准：

- 终到站时间基准不能是发车时间
- 始发站时间基准不能是到达时间

### 11. 广播业务模版 ###
#### 11.1 普通业务模版 ####
普通业务模版用于自动广播计划的生成，左侧名称是广播模版组名称，一个模版组包含多条不同/相同内容的广播，每个广播模版需要指定执行时间基准、相对时间、广播词名称、作业模式、优先级以及广播区域信息。

为了防止基本的广播模版缺失造成自动广播业务无法执行，对于系统默认的应用于始发车站、终到车站和通过车站的三组广播组不允许用户删除（内容可以清空），对于用户自建的广播组可以进行删除操作。

普通业务模版的接口位置：
[/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

接口定义如下：

    // 获取广播组列表
    @WebResult(name = "getBroadcastGroupListResult")
    List<BroadcastTemplateGroupDTO> getBroadcastGroupList(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

    // 获取广播组下的模版列表
    @WebResult(name = "getBroadcastTemplateListResult")
    List<BroadcastTemplateDTO> getBroadcastTemplateList(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "groupName") String groupName);

    // 增加广播组
    @WebResult(name = "addBroadcastGroupResult")
    ResultMessage addBroadcastGroup(@WebParam(name = "group") BroadcastTemplateGroupDTO group);

    // 增加广播业务模版
    @WebResult(name = "addBroadcastTemplateResult")
    ResultMessage addBroadcastTemplate(@WebParam(name = "template") BroadcastTemplateDTO template);

    // 修改广播组名称
    @WebResult(name = "modifyGroupNameResult")
    ResultMessage modifyGroupName(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "oldGroupName") String oldGroupName, 
    @WebParam(name = "newGroupName") String newGroupName);

    // 修改广播业务模版
    @WebResult(name = "modifyBroadcastTemplateResult")
    ResultMessage modifyBroadcastTemplate(@WebParam(name = "template") BroadcastTemplateDTO template);

    // 删除广播组
    @WebResult(name = "delBroadcastGroupResult")
    ResultMessage delBroadcastGroup(@WebParam(name = "groupID") long groupID);

    // 删除广播业务模版
    @WebResult(name = "delBroadcastTemplateResult")
    ResultMessage delBroadcastTemplate(@WebParam(name = "templateID") long templateID);

广播模版组（原广播业务类型）BroadcastTemplateGroupDTO是前后台约定的数据接口，其定义为：

    public class BroadcastTemplateGroupDTO {
    // 数据库ID
    private long id;
    // 广播组名称
    private String templateGroupName;
    // 广播组所属车站站名
    private int stationName;
    // 广播属性：到发或者变更 onArrive, alteration
    private BroadcastKindEnum broadcastKind;
    // 是否可以修改，1表示可以修改，0表示不可修改
    private int revisability;

广播模版BroadcastTemplateDTO是前后台约定的数据接口，其定义为：

    public class BroadcastTemplateDTO {
    // ID
    private long id;
    // 模版名称->列车作业名称--用于插入新模版
    private String templateGroupName;
    // 模版使用的车站站名--用于插入新模版
    private String stationName;
    // 时间基准：到点：arriveTime,发点：departureTime,开检:startCheckTime,停检：stopCheckTime
    private TrainTimeBaseEnum baseTime;
    // 相对时间,单位是分钟
    private int timeOffset;
    // 作业内容->广播内容模版名称
    private String broadcastContentName;
    // 作业模式
    private BroadcastModeEnum operationMode;
    // 优先级
    private int priorityLevel;
    // 作业区域
    private List<String> broadcastArea;
    // 一级区列表
    private List<FirstRegionEnum> firstRegion;
    // 广播类型：到发或者变更 toArrive, alteration
    private BroadcastKindEnum broadcastKind;

其中使用的几个枚举类型定义如下：

    public enum BroadcastKindEnum {
    ARRIVE_DEPARTURE,   // 到发
    ALTERATION,  // 变更
    TRAIN_MANUAL,  // 车次
    OTHERS;  // 其他

    public enum BroadcastModeEnum {
    AUTO, MANUAL
    }

    public enum TrainTimeBaseEnum {
    ARRIVE_TIME,
    DEPARTURE_TIME,
    START_CHECK,
    STOP_CHECK,
    NOT_VALID  // 专门用于无需计算时间的情况，例如变更广播计划
    }


获取广播作业名称，即广播词的名称列表接口如下：
    
    // 获取广播词列表，主要用于广播业务模版编辑
    @WebResult(name = "getNormalContentNameListResult")
    List<String> getNormalContentNameList(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "kind") BroadcastKindEnum kind);

#### 11.2 变更广播业务模版 ####
变更广播业务模版用于系统根据广播计划的修改生成一些修改的提示广播，例如候车区、站台修改的提醒广播。

变更广播可编辑的选项只包括作业名称、优先级和作业区域，广播播放方式只能为手动。

变更模版操作函数与普通模版页面相同，需要页面隐藏不许编辑的项目。不能修改广播模版组，可以编辑广播模版组内的模版。

    // 获取广播组列表
    @WebResult(name = "getBroadcastGroupListResult")
    List<BroadcastTemplateGroupDTO> getBroadcastGroupList(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

    // 获取广播组下的模版列表
    @WebResult(name = "getBroadcastTemplateListResult")
    List<BroadcastTemplateDTO> getBroadcastTemplateList(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "groupName") String groupName);

    // 增加广播业务模版
    @WebResult(name = "addBroadcastTemplateResult")
    ResultMessage addBroadcastTemplate(@WebParam(name = "template") BroadcastTemplateDTO template);

    // 修改广播业务模版
    @WebResult(name = "modifyBroadcastTemplateResult")
    ResultMessage modifyBroadcastTemplate(@WebParam(name = "template") BroadcastTemplateDTO template);

    // 删除广播业务模版
    @WebResult(name = "delBroadcastTemplateResult")
    ResultMessage delBroadcastTemplate(@WebParam(name = "templateID") long templateID);

### 12.行车广播词维护 ###
行车广播词维护功能主要是维护到发、变更、其他三种类型的广播词。

接口位置为：
[/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

主要接口定义为：

    // 获取到发/变更/其他广播词列表
    @WebResult(name = "getNormalContentResult")
    List<NormalBroadcastContentDTO> getNormalContent(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "kind") BroadcastKindEnum kind);

    // 增加到发/变更/其他广播词
    @WebResult(name = "addNormalContentResult")
    ResultMessage addNormalContent(@WebParam(name = "dto") NormalBroadcastContentDTO dto);

    // 修改到发/变更/其他广播词
    @WebResult(name = "modifyNormalContentResult")
    ResultMessage modifyNormalContent(@WebParam(name = "dto") NormalBroadcastContentDTO dto);

    // 删除到发/变更/其他广播词
    @WebResult(name = "delNormalContentResult")
    ResultMessage delNormalContent(@WebParam(name = "id") long id);

其中NormalBroadcastContentDTO是前后台的数据接口，其定义如下：

    public class NormalBroadcastContentDTO {
    // id
    private long id;
    // 广播内容名称 -> 作业内容(广播业务模版)
    private String contentName;
    // 广播属性：到发、变更或者其他， toArrive, alteration, others
    private BroadcastKindEnum broadcastKind;
    // 站名
    private String stationName;
    // 本地语言广播详细内容
    private String contentInLocalLan;
    // 英文广播详细内容
    private String contentInEng;

其中广播类型枚举定义为：

	public enum BroadcastKindEnum {
    ARRIVE_DEPARTURE,   // 到发
    ALTERATION,  // 变更
    TRAIN_MANUAL,  // 车次
    OTHERS;  // 其他
    }

在到发广播词部分使用的枚举类型为除了车次之外的其他三类。

提供给前台替换标签的列表接口为：
    
    // 提供到发广播词插入变量列表
    @WebResult(name = "getNormalContentSubstitutionListResult")
    List<BroadcastContentSubstitutionDTO> getNormalContentSubstitutionList();

NormalBroadcastContentSubstitution是替换标签数据接口，其定义为：

    public class BroadcastContentSubstitutionDTO {
    // 替换名称 @xxx的形式
    private String tag;
    // 注释
    private String explain;

### 13. 专题广播 ###
专题广播页面允许编辑类型及广播词。

接口地址为：
[/Service/BroadcastService](http://xxx/Service/BroadcastService "/Service/BroadcastService")

接口定义如下：

    // 获取专题广播类型列表
    @WebResult(name = "getSpecialContentKindListResult")
    List<String> getSpecialContentKindList(@WebParam(name = "stationName") String stationName);

    // 增加专题广播类型
    @WebResult(name = "addSpecialContentKindResult")
    ResultMessage addSpecialContentKind(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "kind") String kind);

    // 删除专题广播类型
    @WebResult(name = "delSpecialContentKindResult")
    ResultMessage delSpecialContentKind(@WebParam(name = "stationName") String stationName, 
    @WebParam(name = "kind") String kind);

    // 根据类别获取专题广播列表
    @WebResult(name = "getSpecialContentResult")
    List<SpecialBroadcastContentDTO> getSpecialContent(@WebParam(name = "stationName") 
    String stationName, @WebParam(name = "kind") String kind);

    // 增加专题广播
    @WebResult(name = "addSpecialContentResult")
    ResultMessage addSpecialContent(@WebParam(name = "dto") SpecialBroadcastContentDTO dto);

    // 修改专题广播
    @WebResult(name = "modifySpecialContentResult")
    ResultMessage modifySpecialContent(@WebParam(name = "dto") SpecialBroadcastContentDTO dto);

    // 删除专题广播
    @WebResult(name = "delSpecialContentResult")
    ResultMessage delSpecialContent(@WebParam(name = "id") long id);

获取替换标签列表的接口如下：

    // 提供专题广播词插入变量列表
    @WebResult(name = "getSpecialContentSubstitutionListResult")
    List<BroadcastContentSubstitutionDTO> getSpecialContentSubstitutionList();

其中BroadcastContentSubstitutionDTO为专题广播替换数据约定，与行车广播词相同。

SpecialBroadcastContentDTO为专题广播词数据接口，其定义如下：

	public class SpecialBroadcastContentDTO {
    private long id;
    // 专题广播名称
    private String contentName;
    // 专题广播内容
    private String broadcastContent;
    // 专题广播类型
    private String contentType;
    // 专题广播使用的车站
    private int stationName;

### 14. 操作日志 ###
#### 14.1 日志查询 ####
日志查询页面的业务。包括根据当前登录用户列出其可查看的用户列表。当用户选中操作时间，操作人，业务类型后进行的操作日志的查询。

接口地址为：
[/Service/SystemService](http://xxx/Service/SystemService "/Service/BroadcastService")

接口定义如下：

    // 获取可查询的用户列表
        @WebResult(name = "getOperatorListResult")
        List<String> getOperatorList(@WebParam(name = "currentUser") String currentUser);

        //查询日志
        @WebResult(name = "logQueryByConditionResult")
        List<OperationLogDTO> logQueryByCondition(@WebParam(name = "operationTimeStart") String operationTimeStart, @WebParam(name = "operationTimeEnd") String operationTimeEnd, @WebParam(name = "operator") String operator, @WebParam(name = "serviceType") String serviceType, @WebParam(name = "language") String language, @WebParam(name = "stationName") String stationName, @WebParam(name = "currentUser") String currentUser);

其中OperationLogDTO为操作日志数据接口，其定义如下：

    public class OperationLogDTO {
        //操作日志的id，UUID
        private String id;
        //操作时间
        private String operationTime;
        //操作人
        private String operator;
        //业务类型
        private String serviceType;
        //操作内容
        private String operationContent;
        //操作结果
        private String operationResult;
        //详细操作信息
        private String operationDetail;
        //操作车站
        private String stationName;

#### 14.2 日志记录 ####
规则：在需要进行日志记录的方法上加注解@LogRecord，并在数据库中将方法名与其对应的业务类型写入methodRelation表中。（此版本只有业务类型，不再区分操作类型）
如： methodName：logIn  ， serviceType ： logIn


