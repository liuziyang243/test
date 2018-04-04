package com.crscd.framework.orm;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.dao.DatabaseHelper;
import com.crscd.framework.dao.SqlHelper;
import com.crscd.framework.util.collection.ArrayUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.reflect.ObjectUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供与实体相关的数据库操作
 *
 * @author lzy
 * @since 1.0
 */
public class DataSet {

    private String dbtype;

    private DatabaseHelper dbhelper;

    public DataSet(String dbtype, DatabaseHelper dbhelper) {
        this.dbtype = dbtype;
        this.dbhelper = dbhelper;
    }

    public DatabaseHelper getDbhelper() {
        return dbhelper;
    }

    public void setDbhelper(DatabaseHelper dbhelper) {
        this.dbhelper = dbhelper;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    /**
     * 查询指定字段多条数据，需要自组sql语句
     */
    public <T> List<T> select(String sql, Object... params) {
        return dbhelper.queryColumnList(sql, params);
    }

    /**
     * 查询单条数据，并转为相应类型的实体
     */
    public <T> T select(Class<T> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "");
        return dbhelper.queryEntity(entityClass, sql, params);
    }

    /**
     * 查询多条数据，并转为相应类型的实体列表
     */
    public <T> List<T> selectList(Class<T> entityClass) {
        return selectListWithConditionAndSort(entityClass, "", "");
    }

    /**
     * 查询多条数据，并转为相应类型的实体列表（带有查询条件与查询参数）
     */
    public <T> List<T> selectListWithCondition(Class<T> entityClass, String condition, Object... params) {
        return selectListWithConditionAndSort(entityClass, condition, "", params);
    }

    /**
     * 查询多条数据，并转为相应类型的实体列表（带有排序方式）
     */
    public <T> List<T> selectListWithSort(Class<T> entityClass, String sort) {
        return selectListWithConditionAndSort(entityClass, "", sort);
    }

    /**
     * 查询多条数据，并转为相应类型的实体列表（带有查询条件、排序方式与查询参数）
     */
    public <T> List<T> selectListWithConditionAndSort(Class<T> entityClass, String condition, String sort,
                                                      Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        return dbhelper.queryEntityList(entityClass, sql, params);
    }

    /**
     * 查询数据条数
     */
    public long selectCount(Class<?> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSqlForCount(entityClass, condition);
        return dbhelper.queryCount(sql, params);
    }

    /**
     * 查询实体主键值
     */
    public <T> T selectPK(Class<?> entityClass, Object entityObject) {
        return selectPK(entityClass, entityObject, FrameworkConstant.PK_NAME);
    }

    /**
     * 查询实体主键值, 可指定主键名称
     */
    public <T> T selectPK(Class<?> entityClass, Object entityObject, String PK_name) {
        if (entityObject == null) {
            throw new IllegalArgumentException("Entity is null!");
        }
        Map<String, Object> map = ObjectUtil.getFieldMap(entityObject);
        // 去掉id主键
        map.remove(PK_name);
        if (MapUtil.isNotEmpty(map)) {
            int count = map.size();
            Object[] params = new Object[count];
            int index = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params[index] = entry.getValue();
                index += 1;
            }
            String sql = SqlHelper.generateSelectSql(entityClass, map);
            sql = sql.replace("*", PK_name);
            return dbhelper.queryColumn(sql, params);
        }
        throw new IllegalArgumentException("Entity has no property except primary key!");
    }

    /**
     * 根据条件查询主键值
     */
    public <T> T selectPKWithCondition(Class<?> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateSelectPKSql(entityClass, condition);
        return dbhelper.queryColumn(sql, params);
    }

    /**
     * 查询多条数据，并转为列表（分页方式）
     */
    public <T> List<T> selectListForPager(int pageNumber, int pageSize, Class<T> entityClass, String condition,
                                          String sort, Object... params) {
        String sql = SqlHelper.generateSelectSqlForPager(pageNumber, pageSize, entityClass, condition, sort, dbtype);
        return dbhelper.queryEntityList(entityClass, sql, params);
    }

    /**
     * 查询多条数据，并转为映射
     */
    public <T> Map<Long, T> selectMap(Class<T> entityClass) {
        return selectMapWithPK(entityClass, FrameworkConstant.PK_NAME, "", "");
    }

    /**
     * 查询多条数据，并转为映射（带有查询条件与查询参数）
     */
    public <T> Map<Long, T> selectMapWithCondition(Class<T> entityClass, String condition, Object... params) {
        return selectMapWithPK(entityClass, FrameworkConstant.PK_NAME, condition, "", params);
    }

    /**
     * 查询多条数据，并转为映射（带有排序方式与查询参数）
     *
     * @since 2.3.3
     */
    public <T> Map<Long, T> selectMapWithSort(Class<T> entityClass, String sort) {
        return selectMapWithPK(entityClass, FrameworkConstant.PK_NAME, "", sort);
    }

    /**
     * 查询多条数据，并转为映射（带有查询条件、排序方式与查询参数）
     */
    public <T> Map<Long, T> selectMapWithConditionAndSort(Class<T> entityClass, String condition, String sort,
                                                          Object... params) {
        return selectMapWithPK(entityClass, FrameworkConstant.PK_NAME, condition, sort, params);
    }

    /**
     * 查询多条数据，并转为映射（带有主键名）
     */
    @SuppressWarnings("unchecked")
    public <PK, T> Map<PK, T> selectMapWithPK(Class<T> entityClass, String pkName, String condition, String sort,
                                              Object... params) {
        Map<PK, T> map = new LinkedHashMap<PK, T>();
        List<T> list = selectListWithConditionAndSort(entityClass, condition, sort, params);
        for (T obj : list) {
            PK pk = (PK) ObjectUtil.getFieldValue(obj, pkName);
            map.put(pk, obj);
        }
        return map;
    }

    /**
     * 根据列名查询单条数据，并转为相应类型的实体
     */
    public <T> T selectColumn(Class<?> entityClass, String columnName, String condition, Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, "");
        sql = sql.replace("*", columnName);
        return dbhelper.queryColumn(sql, params);
    }

    /**
     * 根据列名查询多条数据，并转为相应类型的实体列表
     */
    public <T> List<T> selectColumnList(Class<?> entityClass, String columnName, String condition, String sort,
                                        Object... params) {
        String sql = SqlHelper.generateSelectSql(entityClass, condition, sort);
        sql = sql.replace("*", columnName);
        return dbhelper.queryColumnList(sql, params);
    }

    /**
     * 插入一个实体返回主键值
     */
    public Object insertReturnPK(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entity.getClass();
        Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entity);
        return insertReturnPK(entityClass, fieldMap);
    }

    /**
     * 插入后返回主键值
     */
    public Object insertReturnPK(Class<?> entityClass, Map<String, Object> fieldMap) {
        if (MapUtil.isEmpty(fieldMap)) {
            return -1;
        }
        String sql = SqlHelper.generateInsertSql(entityClass, fieldMap.keySet());
        Object[] params = fieldMap.values().toArray();
        return dbhelper.insertReturnPK(sql, params);
    }

    /**
     * 插入一条数据
     */
    public boolean insert(Class<?> entityClass, Map<String, Object> fieldMap) {
        if (MapUtil.isEmpty(fieldMap)) {
            return true;
        }
        String sql = SqlHelper.generateInsertSql(entityClass, fieldMap.keySet());
        Object[] params = fieldMap.values().toArray();
        int rows = dbhelper.update(sql, params);
        return rows > 0;
    }

    /**
     * 插入一个实体
     */
    public boolean insert(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entity.getClass();
        Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entity);
        return insert(entityClass, fieldMap);
    }

    /**
     * 插入多个实体
     */
    public boolean insertList(List<?> entitys) {
        if (entitys == null || entitys.isEmpty()) {
            return true;
        }
        Class<?> entityClass = entitys.get(0).getClass();
        Map<String, Object> stdFieldMap = ObjectUtil.getFieldMap(entitys.get(0));
        if (MapUtil.isEmpty(stdFieldMap)) {
            return true;
        }
        String sql = SqlHelper.generateInsertSql(entityClass, stdFieldMap.keySet());
        final int row = entitys.size();
        final int col = stdFieldMap.size();
        Object[][] param = new Object[row][col];
        for (int i = 0; i < row; i++) {
            Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entitys.get(i));
            Object[] params = fieldMap.values().toArray();
            System.arraycopy(params, 0, param[i], 0, col);
        }
        return (dbhelper.batchUpdate(sql, param).length > 0);
    }

    /**
     * 插入多个实体
     */
    public int[] insertListWithResultList(List<?> entitys) {
        int reslut[];
        if (entitys == null || entitys.isEmpty()) {
            reslut = new int[1];
            reslut[0] = -1;
            return reslut;
        }
        Class<?> entityClass = entitys.get(0).getClass();
        Map<String, Object> stdFieldMap = ObjectUtil.getFieldMap(entitys.get(0));
        if (MapUtil.isEmpty(stdFieldMap)) {
            reslut = new int[1];
            reslut[0] = -1;
            return reslut;
        }
        String sql = SqlHelper.generateInsertSql(entityClass, stdFieldMap.keySet());
        final int row = entitys.size();
        final int col = stdFieldMap.size();
        Object[][] param = new Object[row][col];
        for (int i = 0; i < row; i++) {
            Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entitys.get(i));
            Object[] params = fieldMap.values().toArray();
            System.arraycopy(params, 0, param[i], 0, col);
        }
        return dbhelper.batchUpdate(sql, param);
    }

    /**
     * 更新相关数据
     */
    public boolean update(Class<?> entityClass, Map<String, Object> fieldMap, String condition,
                          Object... params) {
        if (MapUtil.isEmpty(fieldMap)) {
            return true;
        }
        String sql = SqlHelper.generateUpdateSql(entityClass, fieldMap, condition);
        int rows = dbhelper.update(sql, ArrayUtil.concat(fieldMap.values().toArray(), params));
        return rows > 0;
    }

    /**
     * 更新一个实体
     */
    public boolean update(Object entity) {
        return update(entity, FrameworkConstant.PK_NAME);
    }

    /**
     * 更新一个实体（带有主键名）,主键名称由调用程序输入进来
     */
    public boolean update(Object entityObject, String pkName) {
        if (entityObject == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entityObject.getClass();
        Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entityObject);
        String condition = pkName + " = ?";
        Object[] params = {ObjectUtil.getFieldValue(entityObject, pkName)};
        return update(entityClass, fieldMap, condition, params);
    }

    /**
     * 2016.03.15 CSQ
     * 更新一个实体（带有参数）
     */
    public boolean update(Object entityObject, String condition, Object... params) {
        if (entityObject == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entityObject.getClass();
        Map<String, Object> fieldMap = ObjectUtil.getFieldMap(entityObject);
        return update(entityClass, fieldMap, condition, params);
    }

    /**
     * 删除相关数据
     */
    public boolean delete(Class<?> entityClass, String condition, Object... params) {
        String sql = SqlHelper.generateDeleteSql(entityClass, condition);
        int rows = dbhelper.update(sql, params);
        return rows > 0;
    }

    /**
     * 删除相关数据, 无参情况
     */
    public boolean delete(Class<?> entityClass, String condition) {
        String sql = SqlHelper.generateDeleteSql(entityClass, condition);
        int rows = dbhelper.update(sql);
        return rows > 0;
    }

    /**
     * 按条件删除多条数据，采用批量处理的方式
     */
    public boolean[] delete(Class<?> entityClass, String condition, List<String> paramList) {
        String sql = SqlHelper.generateDeleteSql(entityClass, condition);
        final int row = paramList.size();
        Object[][] param = new Object[row][1];
        for (int i = 0; i < row; i++) {
            Object[] params = {paramList.get(i)};
            System.arraycopy(params, 0, param[i], 0, 1);
        }
        int[] batchUpdate = dbhelper.batchUpdate(sql, param);
        boolean[] result = new boolean[batchUpdate.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = batchUpdate[i] > 0;
        }
        return result;
    }

    /**
     * 删除一个实体
     */
    public boolean delete(Object entityObject) {
        return delete(entityObject, FrameworkConstant.PK_NAME);
    }

    /**
     * 删除一个实体（可指定主键名）
     */
    public boolean delete(Object entityObject, String pkName) {
        if (entityObject == null) {
            throw new IllegalArgumentException();
        }
        Class<?> entityClass = entityObject.getClass();
        String condition = pkName + " = ?";
        Object[] params = {ObjectUtil.getFieldValue(entityObject, pkName)};
        return delete(entityClass, condition, params);
    }

    /**
     * 删除一个实体（针对无指定主键的情况）
     *
     * @param entityObject
     * @param entityClass
     * @return
     */
    public boolean delete(Object entityObject, Class<?> entityClass) {
        if (entityObject == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> map = ObjectUtil.getFieldMap(entityObject);
        // 去掉id主键
        map.remove(FrameworkConstant.PK_NAME);
        if (MapUtil.isNotEmpty(map)) {
            int count = map.size();
            Object[] params = new Object[count];
            int index = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params[index] = entry.getValue();
                index += 1;
            }
            String sql = SqlHelper.generateDeleteSql(entityClass, map);
            int rows = dbhelper.update(sql, params);
            return rows > 0;
        }
        return false;
    }

    /**
     * csq 2016-07-08
     * 删除表内全部数据
     *
     * @param entityClass
     * @return
     */
    public boolean delete(Class<?> entityClass) {
        return delete(entityClass, "1=?", "1");
    }

}
