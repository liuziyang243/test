package com.crscd.framework.dao;

import com.crscd.framework.FrameworkConstant;
import com.crscd.framework.util.reflect.ClassUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Create by: lzy
 * Date: 2017/5/4
 * Time: 16:39
 *
 * @author lzy
 */
public class DatabaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    /**
     * 定义一个局部线程变量（使每个线程都拥有自己的连接）
     */
    private static final ThreadLocal<Connection> CONNECTION_CONTAINER = new ThreadLocal<>();
    //数据访问器
    private DataAccessor dataAccessor;
    private DataSource dataSource;

    public DatabaseHelper(DataSource dataSource, DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
        this.dataSource = dataSource;
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() {
        Connection conn;
        try {
            //从 DataSource 中获取 Connection
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("获取数据库连接出错！", e);
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * 开启事务的模式下获取数据库连接
     */
    private Connection getConnectionwithAffair() {
        Connection conn;
        try {
            // 先从 ThreadLocal 中获取 Connection
            conn = CONNECTION_CONTAINER.get();
            if (conn == null) {
                // 若不存在，则从 DataSource 中获取 Connection
                conn = dataSource.getConnection();
                // 将 Connection 放入 ThreadLocal 中
                if (conn != null) {
                    CONNECTION_CONTAINER.set(conn);
                }
            }
        } catch (SQLException e) {
            logger.error("获取数据库连接出错！", e);
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public void beginTransaction() {
        Connection conn = getConnectionwithAffair();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("开启事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_CONTAINER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public void commitTransaction() {
        Connection conn = getConnectionwithAffair();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                logger.error("提交事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_CONTAINER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public void rollbackTransaction() {
        Connection conn = getConnectionwithAffair();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                logger.error("回滚事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_CONTAINER.remove();
            }
        }
    }

    /**
     * 初始化 SQL 脚本
     */
    public void initSQL(String sqlPath) {
        try {
            File sqlFile = new File(ClassUtil.getClassPath() + sqlPath);
            List<String> sqlList = FileUtils.readLines(sqlFile, FrameworkConstant.UTF_8);
            for (String sql : sqlList) {
                update(sql);
            }
        } catch (Exception e) {
            logger.error("初始化 SQL 脚本出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 SQL 语句查询 Entity
     */
    public <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntity(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity并返回bean
     */
    public <T> T queryEntityWithReturn(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.insertReturnEntity(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 列表
     */
    public <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntityList(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 映射（Field Name => Field Value）
     */
    public <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntityMap(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段（单条记录）
     */
    public Object[] queryArray(String sql, Object... params) {
        return dataAccessor.queryArray(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段列表（多条记录）
     */
    public List<Object[]> queryArrayList(String sql, Object... params) {
        return dataAccessor.queryArrayList(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段（单条记录）
     */
    public Map<String, Object> queryMap(String sql, Object... params) {
        return dataAccessor.queryMap(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段列表（多条记录）
     */
    public List<Map<String, Object>> queryMapList(String sql, Object... params) {
        return dataAccessor.queryMapList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段（单条记录）
     */
    public <T> T queryColumn(String sql, Object... params) {
        return dataAccessor.queryColumn(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段列表（多条记录）
     */
    public <T> List<T> queryColumnList(String sql, Object... params) {
        return dataAccessor.queryColumnList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段映射（多条记录）
     */
    public <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params) {
        return dataAccessor.queryColumnMap(column, sql, params);
    }

    /**
     * 根据 SQL 语句查询记录条数
     */
    public long queryCount(String sql, Object... params) {
        return dataAccessor.queryCount(sql, params);
    }

    /**
     * 执行更新语句（包括：update、insert、deletion）
     */
    public int update(String sql, Object... params) {
        return dataAccessor.update(sql, params);
    }

    /**
     * 执行带有事务的更新语句(包括: update、insert、deletion)
     */
    public boolean transactionUpdate(Map<String, Object[]> sqlList) {
        // 开始事务
        beginTransaction();
        QueryRunner runner = new QueryRunner();
        try {
            int flag = 0;
            Connection conn = getConnectionwithAffair();
            for (Map.Entry<String, Object[]> entry : sqlList.entrySet()) {
                flag = runner.update(conn, entry.getKey(), entry.getValue());
                if (flag <= 0) {
                    break;
                }
            }
            // 提交事务
            if (flag > 0) {
                commitTransaction();
            } else {
                rollbackTransaction();
                return false;
            }
        } catch (SQLException e) {
            // 回滚事务
            rollbackTransaction();
            logger.error("执行批量更新transactionUpdate失败！", e);
            return false;
        }
        return true;
    }

    /**
     * 执行批量更新语句(包括: update、insert、deletion)
     */
    public int[] batchUpdate(String sql, Object[][] param) {
        return dataAccessor.batchUpdate(sql, param);
    }

    /**
     * 使用特定连接执行批量更新语句(包括: update、insert、deletion)
     */
    public int[] batchUpdateWithConn(String sql, Connection conn, Object[][] param) {
        return dataAccessor.batchUpdateWithConn(sql, conn, param);
    }

    /**
     * 使用特定连接执行更新语句（包括：update、insert、deletion）
     */
    public int updateWithConn(String sql, Connection conn, Object... params) {
        return dataAccessor.updateWithConn(sql, conn, params);
    }

    /**
     * 执行插入语句，返回插入后的主键
     */
    public Serializable insertReturnPK(String sql, Object... params) {
        return dataAccessor.insertReturnPK(sql, params);
    }

    /**
     * 执行无参过程语句
     */
    public boolean execProc(String proc) {
        return dataAccessor.execProc(proc);
    }
}
