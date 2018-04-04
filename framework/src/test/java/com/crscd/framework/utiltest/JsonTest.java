package com.crscd.framework.utiltest;

import com.crscd.framework.util.text.JsonUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuishiqing on 2017/7/18.
 */
public class JsonTest {

    @Test
    public void objToJSONTest() {

        User user1 = new User();
        user1.setName("张三");
        user1.setAge("20");
        user1.setSexy("男");
        user1.setGroup("1");

        System.out.print(JsonUtil.toJSON(user1));

    }

    @Test
    public void listToJSONTest() {
        User user1 = new User();
        user1.setName("张三");
        user1.setAge("20");
        user1.setSexy("男");
        user1.setGroup("1");

        User user2 = new User();
        user2.setName("李四");
        user2.setAge("25");
        user2.setSexy("女");
        user2.setGroup("1");

        User user3 = new User();
        user3.setName("王五");
        user3.setAge("30");
        user3.setSexy("男");
        user3.setGroup("1");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        String s = JsonUtil.toJSON(users);
        System.out.print(s);

        List<User> resultList = JsonUtil.jsonToList(s, User.class);
        for (User us : resultList) {
            System.out.print(us);
        }

    }

    @Test
    public void mapToJSONTest() {
        User user1 = new User();
        user1.setName("张三");
        user1.setAge("20");
        user1.setSexy("男");
        user1.setGroup("1");

        User user2 = new User();
        user2.setName("李四");
        user2.setAge("25");
        user2.setSexy("女");
        user2.setGroup("1");

        Map<String, User> amap = new HashMap<>();
        amap.put("01", user1);
        amap.put("02", user2);

        String s = JsonUtil.toJSON(amap);
//        System.out.print(s);

        Map<String, User> m = JsonUtil.jsonToMap(s, String.class, User.class);
    }

    @Test
    public void JsonToObj() {
        User user1 = new User();
        user1.setName("张三");
        user1.setAge("20");
        user1.setSexy("男");
        user1.setGroup("1");
//        String s = JsonUtil.toJSON(user1);
//        User userResult = JsonUtil.fromJSON(s, User.class);
//        System.out.print(userResult);

        User user2 = new User();
        user2.setName("李四");
        user2.setAge("40");
        user2.setSexy("女");
        user2.setGroup("2");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Userlist ul = new Userlist();
        ul.setDate("2016-02-02");
        ul.setID("001");
        ul.setUsers(users);

        String s = JsonUtil.toJSON(ul);
        Userlist ul2 = new Userlist();
        ul2 = JsonUtil.fromJSON(s, Userlist.class);
        List<User> ur = ul2.getUsers();
        System.out.print(ur.get(0).getName());
    }

    @Test
    public void ListTest() {
        List<String> array = new ArrayList<>();
//        array.add("platform 1");
        String json = JsonUtil.toJSON(array);
        System.out.println(json);
        List<String> jsonArray = JsonUtil.jsonToList(json, String.class);
        System.out.println(jsonArray.size());
    }


}

