package com.crscd.framework.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.orm.EntityHelper;
import com.crscd.framework.util.base.PropsUtil;
import com.crscd.framework.util.collection.CollectionUtil;
import com.crscd.framework.util.collection.MapUtil;
import com.crscd.framework.util.text.StringUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * 封装 SQL 语句相关操作
 *
 * @author lzy
 * @since 1.0
 */
public class SqlHelper {

    /**
     * SQL 属性文件对象
     */
    private static final Properties SQL_PROPS = PropsUtil.loadProps(FrameworkConstant.SQL_PROPS);

    private static final String MYSQL = "mysql";
    private static final String ORACLE = "oracle";
    private static final String MSSQL = "mssql";

    /**
     * 从 SQL 属性文件中获取相应的 SQL 语句
     */
    public static String getSql(String key) {
        String sql;
        if (SQL_PROPS.containsKey(key)) {
            sql = SQL_PROPS.getProperty(key);
        } else {
            throw new RuntimeException("无法在 " + FrameworkConstant.SQL_PROPS + " 文件中获取属性：" + key);
        }
        return sql;
    }

    /**
     * 生成 select 语句
     */
    public static String generateSelectSql(Class<?> entityClass, String condition, String sort) {
        StringBuilder sql = new StringBuilder("select * from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        sql.append(generateOrder(sort));
        return sql.toString();
    }

    /**
     * 生成 select 语句
     */
    public static String generateSelectSql(Class<?> entityClass, Map<String, Object> map) {
        StringBuilder sql = new StringBuilder("select * from ").append(getTable(entityClass));
        sql.append(generateWhere(generateMultiCondition(map)));
        return sql.toString();
    }


    public static String generateSelectPKSql(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("select ")
                .append(FrameworkConstant.PK_NAME)
                .append(" from ")
                .append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 insert 语句
     */
    public static String generateInsertSql(Class<?> entityClass, Collection<String> fieldNames) {
        StringBuilder sql = new StringBuilder("insert into ").append(getTable(entityClass));
        if (CollectionUtil.isNotEmpty(fieldNames)) {
            int i = 0;
            StringBuilder columns = new StringBuilder(" ");
            StringBuilder values = new StringBuilder(" values ");
            for (String fieldName : fieldNames) {
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0) {
                    columns.append("(").append(columnName);
                    values.append("(?");
                } else {
                    columns.append(", ").append(columnName);
                    values.append(", ?");
                }
                if (i == fieldNames.size() - 1) {
                    columns.append(")");
                    values.append(")");
                }
                i++;
            }
            sql.append(columns).append(values);
        }
        return sql.toString();
    }

    /**
     * 生成 delete 语句
     */
    public static String generateDeleteSql(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("delete from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 delete 语句
     */
    public static String generateDeleteSql(Class<?> entityClass, Map<String, Object> map) {
        StringBuilder sql = new StringBuilder("delete from ").append(getTable(entityClass));
        sql.append(generateWhere(generateMultiCondition(map)));
        return sql.toString();
    }

    /**
     * 生成不带主键的条件语句
     */
    public static String generateMultiCondition(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int count = map.size();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (index == (count - 1)) {
                sb.append(entry.getKey()).append(" = ?");
            } else {
                sb.append(entry.getKey()).append(" = ? AND ");
            }
            index += 1;
        }
        return sb.toString();
    }

    /**
     * 生成 update 语句
     */
    public static String generateUpdateSql(Class<?> entityClass, Map<String, Object> fieldMap, String condition) {
        StringBuilder sql = new StringBuilder("update ").append(getTable(entityClass));
        if (MapUtil.isNotEmpty(fieldMap)) {
            sql.append(" set ");
            int i = 0;
            for (Map.Entry<String, Object> fieldEntry : fieldMap.entrySet()) {
                String fieldName = fieldEntry.getKey();
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0) {
                    sql.append(columnName).append(" = ?");
                } else {
                    sql.append(", ").append(columnName).append(" = ?");
                }
                i++;
            }
        }
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 select count(*) 语句
     */
    public static String generateSelectSqlForCount(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("select count(*) from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        return sql.toString();
    }

    /**
     * 生成 select 分页语句（数据库类型为：mysql、oracle、mssql）
     */
    public static String generateSelectSqlForPager(int pageNumber, int pageSize, Class<?> entityClass, String condition,
                                                   String sort, String dbType) {
        StringBuilder sql = new StringBuilder();
        String table = getTable(entityClass);
        String where = generateWhere(condition);
        String order = generateOrder(sort);
        if (MYSQL.equalsIgnoreCase(dbType)) {
            int pageStart = (pageNumber - 1) * pageSize;
            appendSqlForMySql(sql, table, where, order, pageStart, pageSize);
        } else if (ORACLE.equalsIgnoreCase(dbType)) {
            int pageStart = (pageNumber - 1) * pageSize + 1;
            int pageEnd = pageStart + pageSize;
            appendSqlForOracle(sql, table, where, order, pageStart, pageEnd);
        } else if (MSSQL.equalsIgnoreCase(dbType)) {
            int pageStart = (pageNumber - 1) * pageSize;
            appendSqlForMsSql(sql, table, where, order, pageStart, pageSize);
        }
        return sql.toString();
    }

    private static String getTable(Class<?> entityClass) {
        // 为了兼容linux下mysql,将表名改为小写
        return EntityHelper.getTableName(entityClass).toLowerCase();
    }

    private static String generateWhere(String condition) {
        String where = "";
        if (StringUtil.isNotEmpty(condition)) {
            where += " where " + condition;
        }
        return where;
    }

    private static String generateOrder(String sort) {
        String order = "";
        if (StringUtil.isNotEmpty(sort)) {
            order += " order by " + sort;
        }
        return order;
    }

    /**
     * select * from 表名 where 条件 order by 排序 limit 开始位置, 结束位置
     */
    private static void appendSqlForMySql(StringBuilder sql, String table, String where, String order, int pageStart,
                                          int pageEnd) {
        sql.append("select * from ").append(table);
        sql.append(where);
        sql.append(order);
        sql.append(" limit ").append(pageStart).append(", ").append(pageEnd);
    }

    /**
     * select a.* from ( select rownum rn, t.* from 表名 t where 条件 order by
     * 排序 ) a where a.rn >= 开始位置 and a.rn < 结束位置
     */
    private static void appendSqlForOracle(StringBuilder sql, String table, String where, String order, int pageStart,
                                           int pageEnd) {
        sql.append("select a.* from (select rownum rn, t.* from ").append(table).append(" t");
        sql.append(where);
        sql.append(order);
        sql.append(") a where a.rn >= ").append(pageStart).append(" and a.rn < ").append(pageEnd);
    }

    /**
     * select top 结束位置 * from 表名 where 条件 and id not in ( select top 开始位置 id
     * from 表名 where 条件 order by 排序 ) order by 排序
     */
    private static void appendSqlForMsSql(StringBuilder sql, String table, String where, String order, int pageStart,
                                          int pageEnd) {
        sql.append("select top ").append(pageEnd).append(" * from ").append(table);
        if (StringUtil.isNotEmpty(where)) {
            sql.append(where).append(" and ");
        } else {
            sql.append(" where ");
        }
        sql.append("id not in (select top ").append(pageStart).append(" id from ").append(table);
        sql.append(where);
        sql.append(order);
        sql.append(") ").append(order);
    }
}
