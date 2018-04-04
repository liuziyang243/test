/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crscd.framework.daotest;

import com.crscd.framework.dao.extdbutils.ExtBeanProcessor;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BeanHandlerTest
 */
public class BeanHandlerTest extends BaseTestCase {

    public void testHandle() throws SQLException {
        ResultSetHandler<TestBean> h = new BeanHandler<TestBean>(TestBean.class);
        TestBean results = h.handle(this.rs);

        assertNotNull(results);
        assertEquals("1", results.getOne());
        assertEquals("2", results.getTwo());
        assertEquals(TestBean.Ordinal.THREE, results.getThree());
        assertEquals("not set", results.getDoNotSet());
    }

    public void testHandleList() throws SQLException {
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        ResultSetHandler<TestBean> h = new BeanHandler<TestBean>(TestBean.class, new BasicRowProcessor(new ExtBeanProcessor()));
        TestBean results = h.handle(this.rs);
        assertNotNull(results);
        assertEquals(results.getsList(), list);

        ResultSetHandler<List<TestBean>> hList = new BeanListHandler<>(TestBean.class, new BasicRowProcessor(new ExtBeanProcessor()));
        List<TestBean> beanList = hList.handle(this.rs);
        assertNotNull(beanList);
    }

    public void testEmptyResultSetHandle() throws SQLException {
        ResultSetHandler<TestBean> h = new BeanHandler<TestBean>(TestBean.class);
        TestBean results = h.handle(this.emptyResultSet);

        assertNull(results);
    }

}
