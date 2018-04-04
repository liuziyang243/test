package com.crscd.framework.ioctest;

import com.crscd.framework.orm.DataSet;
import com.crscd.framework.util.base.ContextUtil;
import com.crscd.framework.util.time.DateTimeFormatterUtil;
import com.crscd.framework.util.time.DateTimeUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author lzy
 * Date: 2017/7/15
 * Time: 10:00
 */
public class IocTest {

    @Test
    public void IocInittest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        DataSet oracleDataSet = ctx.getBean("oracleDataSet", DataSet.class);
        assertNotNull(ctx);
        assertNotNull(oracleDataSet);
    }

    @Test
    public void CURDtest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        DataSet mysqlDataSet = ctx.getBean("mysqlDataSet", DataSet.class);
        TestPersonBean person = new TestPersonBean();
        person.setName("Tim");
        person.setSex(Sex.MALE_sex);
        person.setBirthday("1989-01-01");
        List<String> schools = new ArrayList<>();
        schools.add("school1");
        schools.add("school2");
        schools.add("school3");
        schools.add("school4");
        schools.add("school5");
        person.setSchoolList(schools);
        boolean result = mysqlDataSet.insert(person);
        List<TestPersonBean> beanList = mysqlDataSet.selectList(TestPersonBean.class);

        assertTrue(result);
        assertNotNull(beanList);
    }

    @Test
    public void DateTimeTest() {
        LocalTime t = LocalTime.now();
        String t2 = DateTimeUtil.getCurrentTimeString();
        System.out.println(t2);
        String string = "15:03:13";
        System.out.println(DateTimeFormatterUtil.isValidTime(string));
        LocalTime time = DateTimeFormatterUtil.convertStringToTimeNoSecond(string);
        System.out.println(time);

        ApplicationContext ctx = ContextUtil.getInstance();
        DataSet mysqlDataSet = ctx.getBean("mysqlDataSet", DataSet.class);
        TestPlanBean bean = new TestPlanBean();
        System.out.println(LocalDate.now());
        bean.setDate(LocalDate.now());
        bean.setTime(LocalTime.now());
        bean.setLocalDateTime(LocalDateTime.now());

        mysqlDataSet.insert(bean);

        List<TestPlanBean> planBeanList = mysqlDataSet.selectList(TestPlanBean.class);
        System.out.println(planBeanList.size());

        assertNotNull(planBeanList);
    }

    @Test
    public void Clobtest() {
        ApplicationContext ctx = ContextUtil.getInstance();
        DataSet oracleDataSet = ctx.getBean("oracleDataSet", DataSet.class);
        TestClobBean person = new TestClobBean();
        person.setName("Tim");
        person.setSex("male");
        List<String> schools = new ArrayList<>();
        schools.add("school1");
        schools.add("school2");
        schools.add("school3");
        schools.add("school4");
        schools.add("school5");
        person.setSchoolList(schools);
        String description = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\n" +
                "<LY><LN>33</LN><VN>2</VN><FN>1</FN><R>1</R><TM>3000</TM><CM>0</CM><FR><Duriation>33210</Duriation><GP /><SW><ID>3321010</ID><X0>2</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Train Number</T><A>0</A><S>0</S></SW><SW><ID>3321011</ID><X0>120</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Final Station</T><A>0</A><S>0</S></SW><SW><ID>3321013</ID><X0>240</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Departure Time</T><A>0</A><S>0</S></SW><SW><ID>3321014</ID><X0>360</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Arrive Time</T><A>0</A><S>0</S></SW><SW><ID>3321015</ID><X0>480</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Departure State</T><A>0</A><S>0</S></SW><SW><ID>3321016</ID><X0>620</X0><Y0>31</Y0><W>200</W><H>25</H><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>13</TS><TX>1</TX><TP>4</TP><T>Thunderstorm 20C-25C</T><A>0</A><S>0</S></SW><SW><ID>3321017</ID><X0>620</X0><Y0>58</Y0><W>200</W><H>180</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Dear passengers, Train No. K112 from Nairobi to Voi has been canceled for the bad weather! Please sit on your chairs and wait for new notification. Wish you have a pleasant journey!</T><A>0</A><S>0</S></SW><DT><ID>3321018</ID><X0>0</X0><Y0>30</Y0><W>600</W><H>210</H><TP>6</TP><NR>7</NR><NC>5</NC><HR>30,30,30,30,30,30,30</HR><WC>120,120,120,120,120</WC><TR>30</TR><DL>0</DL><DS>0</DS><COL><LN>1</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>2</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>3</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>4</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>5</LN><SE>2</SE><DV>*,delay</DV><BC>-1,-1</BC><TC>65280,65280</TC><TT>宋体,宋体</TT><TS>10,10</TS><TX>1,1</TX><RA>2,2</RA><CA>1,1</CA><A>0,0</A><S>0,0</S><WL>1</WL></COL></DT><CL><ID>3321020</ID><X0>617</X0><Y0>5</Y0><W>206</W><H>23</H><BC>-1</BC><TC>255</TC><TT>宋体</TT><TS>12</TS><TX>1</TX><TP>1</TP><CP>0</CP><F>YYYY-MM-DD HH:mm:SS w</F><A>0</A><S>0</S></CL></FR></LY><?xml version=\"1.0\" encoding=\"gb2312\"?>\n" +
                "<LY><LN>33</LN><VN>2</VN><FN>1</FN><R>1</R><TM>3000</TM><CM>0</CM><FR><Duriation>33210</Duriation><GP /><SW><ID>3321010</ID><X0>2</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Train Number</T><A>0</A><S>0</S></SW><SW><ID>3321011</ID><X0>120</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Final Station</T><A>0</A><S>0</S></SW><SW><ID>3321013</ID><X0>240</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Departure Time</T><A>0</A><S>0</S></SW><SW><ID>3321014</ID><X0>360</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Arrive Time</T><A>0</A><S>0</S></SW><SW><ID>3321015</ID><X0>480</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Departure State</T><A>0</A><S>0</S></SW><SW><ID>3321016</ID><X0>620</X0><Y0>31</Y0><W>200</W><H>25</H><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>13</TS><TX>1</TX><TP>4</TP><T>Thunderstorm 20C-25C</T><A>0</A><S>0</S></SW><SW><ID>3321017</ID><X0>620</X0><Y0>58</Y0><W>200</W><H>180</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Dear passengers, Train No. K112 from Nairobi to Voi has been canceled for the bad weather! Please sit on your chairs and wait for new notification. Wish you have a pleasant journey!</T><A>0</A><S>0</S></SW><DT><ID>3321018</ID><X0>0</X0><Y0>30</Y0><W>600</W><H>210</H><TP>6</TP><NR>7</NR><NC>5</NC><HR>30,30,30,30,30,30,30</HR><WC>120,120,120,120,120</WC><TR>30</TR><DL>0</DL><DS>0</DS><COL><LN>1</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>2</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>3</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>4</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>5</LN><SE>2</SE><DV>*,delay</DV><BC>-1,-1</BC><TC>65280,65280</TC><TT>宋体,宋体</TT><TS>10,10</TS><TX>1,1</TX><RA>2,2</RA><CA>1,1</CA><A>0,0</A><S>0,0</S><WL>1</WL></COL></DT><CL><ID>3321020</ID><X0>617</X0><Y0>5</Y0><W>206</W><H>23</H><BC>-1</BC><TC>255</TC><TT>宋体</TT><TS>12</TS><TX>1</TX><TP>1</TP><CP>0</CP><F>YYYY-MM-DD HH:mm:SS w</F><A>0</A><S>0</S></CL></FR></LY><?xml version=\"1.0\" encoding=\"gb2312\"?>\n" +
                "<LY><LN>33</LN><VN>2</VN><FN>1</FN><R>1</R><TM>3000</TM><CM>0</CM><FR><Duriation>33210</Duriation><GP /><SW><ID>3321010</ID><X0>2</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Train Number</T><A>0</A><S>0</S></SW><SW><ID>3321011</ID><X0>120</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Final Station</T><A>0</A><S>0</S></SW><SW><ID>3321013</ID><X0>240</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Departure Time</T><A>0</A><S>0</S></SW><SW><ID>3321014</ID><X0>360</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Arrive Time</T><A>0</A><S>0</S></SW><SW><ID>3321015</ID><X0>480</X0><Y0>0</Y0><W>120</W><H>32</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Departure State</T><A>0</A><S>0</S></SW><SW><ID>3321016</ID><X0>620</X0><Y0>31</Y0><W>200</W><H>25</H><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>13</TS><TX>1</TX><TP>4</TP><T>Thunderstorm 20C-25C</T><A>0</A><S>0</S></SW><SW><ID>3321017</ID><X0>620</X0><Y0>58</Y0><W>200</W><H>180</H><BC>-1</BC><TC>65535</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><TP>4</TP><T>Dear passengers, Train No. K112 from Nairobi to Voi has been canceled for the bad weather! Please sit on your chairs and wait for new notification. Wish you have a pleasant journey!</T><A>0</A><S>0</S></SW><DT><ID>3321018</ID><X0>0</X0><Y0>30</Y0><W>600</W><H>210</H><TP>6</TP><NR>7</NR><NC>5</NC><HR>30,30,30,30,30,30,30</HR><WC>120,120,120,120,120</WC><TR>30</TR><DL>0</DL><DS>0</DS><COL><LN>1</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>2</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>3</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>4</LN><SE>1</SE><DV /><BC>-1</BC><TC>65280</TC><TT>宋体</TT><TS>10</TS><TX>1</TX><RA>1</RA><CA>1</CA><A>0</A><S>0</S><WL>1</WL></COL><COL><LN>5</LN><SE>2</SE><DV>*,delay</DV><BC>-1,-1</BC><TC>65280,65280</TC><TT>宋体,宋体</TT><TS>10,10</TS><TX>1,1</TX><RA>2,2</RA><CA>1,1</CA><A>0,0</A><S>0,0</S><WL>1</WL></COL></DT><CL><ID>3321020</ID><X0>617</X0><Y0>5</Y0><W>206</W><H>23</H><BC>-1</BC><TC>255</TC><TT>宋体</TT><TS>12</TS><TX>1</TX><TP>1</TP><CP>0</CP><F>YYYY-MM-DD HH:mm:SS w</F><A>0</A><S>0</S></CL></FR></LY>";
        person.setDescription(description);
        boolean result = oracleDataSet.insert(person);
        List<TestClobBean> beanList = oracleDataSet.selectList(TestClobBean.class);
        /*
        for(TestClobBean bean : beanList){
            System.out.println(bean.getDescription());
        }
        */
        assertTrue(result);
        assertNotNull(beanList);
    }
}
