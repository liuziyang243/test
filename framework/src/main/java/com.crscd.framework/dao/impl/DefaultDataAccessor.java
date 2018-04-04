package com.crscd.framework.dao.impl;

import com.crscd.framework.dao.DataAccessor;
import com.crscd.framework.dao.extdbutils.ExtBeanProcessor;
import com.crscd.framework.dao.extdbutils.MappingMatcher;
import com.crscd.framework.orm.EntityHelper;
import com.crscd.framework.util.base.CastUtil;
import com.crscd.framework.util.collection.ArrayUtil;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 默认数据访问器
 * <br/>
 * 基于 Apache Commons DbUtils 实现
 *
 * @author lzy
 * @since 2.0
 */
public class DefaultDataAccessor implements DataAccessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDataAccessor.class);
    private final QueryRunner queryRunner;
    private final DataSource dataSource;

    public DefaultDataAccessor(DataSource dataSource) {
        this.dataSource = dataSource;
        // 应为在声明queryRunner的时候已经使用dataSource作为入参，
        // 因此在后续的使用中不用手动关闭连接，queryRunner会在处理后自行关闭连接。
        queryRunner = new QueryRunner(dataSource);
    }

    private static void printSQL(String sql) {
        logger.debug("[framework] SQL - {}", sql);
    }

    @Override
    public <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T result;
        // dbutils不支持enum直接传入作为参数，需要做一下转型
        if (null != params) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Enum) {
                    params[i] = params[i].toString();
                }
            }
        }
        try {
            Map<String, String> columnMap = EntityHelper.getColumnMap(entityClass);
            result = queryRunner.query(sql, new BeanHandler<T>(entityClass, new BasicRowProcessor(new ExtBeanProcessor(new MappingMatcher(columnMap)))), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return result;
    }

    @Override
    public <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> result;
        // dbutils不支持enum直接传入作为参数，需要做一下转型
        if (null != params) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof Enum) {
                    params[i] = params[i].toString();
                }
            }
        }
        try {
            Map<String, String> columnMap = EntityHelper.getColumnMap(entityClass);
            result = queryRunner.query(sql, new BeanListHandler<T>(entityClass, new BasicRowProcessor(new ExtBeanProcessor(new MappingMatcher(columnMap)))), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return result;
    }

    @Override
    public <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params) {
        Map<K, V> entityMap;
        try {
            entityMap = queryRunner.query(sql, new BeanMapHandler<K, V>(entityClass), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return entityMap;
    }

    @Override
    public Object[] queryArray(String sql, Object... params) {
        Object[] array;
        try {
            array = queryRunner.query(sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return array;
    }

    @Override
    public List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> arrayList;
        try {
            arrayList = queryRunner.query(sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return arrayList;
    }

    @Override
    public Map<String, Object> queryMap(String sql, Object... params) {
        Map<String, Object> map;
        try {
            map = queryRunner.query(sql, new MapHandler(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return map;
    }

    @Override
    public List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> fieldMapList;
        try {
            fieldMapList = queryRunner.query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return fieldMapList;
    }

    @Override
    public <T> T queryColumn(String sql, Object... params) {
        T obj;
        try {
            obj = queryRunner.query(sql, new ScalarHandler<T>(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return obj;
    }

    @Override
    public <T> List<T> queryColumnList(String sql, Object... params) {
        List<T> list;
        try {
            list = queryRunner.query(sql, new ColumnListHandler<T>(), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return list;
    }

    @Override
    public <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params) {
        Map<T, Map<String, Object>> map;
        try {
            map = queryRunner.query(sql, new KeyedHandler<T>(column), params);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return map;
    }

    @Override
    public long queryCount(String sql, Object... params) {
        long result;
        try {
            Object resultTemp = queryRunner.query(sql, new ScalarHandler<>(), params);
            result = CastUtil.castLong(resultTemp);
        } catch (SQLException e) {
            logger.error("查询出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return result;
    }

    @Override
    public int update(String sql, Object... params) {
        int result;
        try {
            result = queryRunner.update(sql, params);
        } catch (SQLException e) {
            logger.error("更新出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return result;
    }

    @Override
    public <T> T insertReturnEntity(Class<T> entryClass, String sql, Object... params) {
        T result;
        try {
            Map<String, String> columnMap = EntityHelper.getColumnMap(entryClass);
            result = queryRunner.insert(sql, new BeanHandler<T>(entryClass, new BasicRowProcessor(new ExtBeanProcessor(new MappingMatcher(columnMap)))), params);
        } catch (SQLException e) {
            logger.error("插入出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return result;
    }

    @Override
    public int[] batchUpdate(String sql, Object[][] params) {
        int reslut[];
        try {
            reslut = queryRunner.batch(sql, params);
        } catch (SQLException e) {
            logger.error("更新出错！", e);
            reslut = new int[1];
            reslut[0] = -1;
        }
        return reslut;
    }

    @Override
    public int[] batchUpdateWithConn(String sql, Connection conn, Object[][] params) {
        int reslut[];
        try {
            reslut = queryRunner.batch(conn, sql, params);
        } catch (SQLException e) {
            logger.error("更新出错！", e);
            reslut = new int[1];
            reslut[0] = -1;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reslut;
    }

    @Override
    public int updateWithConn(String sql, Connection conn, Object... params) {
        int result;
        try {
            result = queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error("更新出错！", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        printSQL(sql);
        return result;
    }

    @Override
    public Serializable insertReturnPK(String sql, Object... params) {
        Serializable key = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            if (ArrayUtil.isNotEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            int rows = pstmt.executeUpdate();
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    key = (Serializable) rs.getObject(1);
                }
            }
        } catch (SQLException e) {
            logger.error("插入出错！", e);
            throw new RuntimeException(e);
        }
        printSQL(sql);
        return key;
    }

    @Override
    public boolean execProc(String proc) {
        Connection conn = null;
        CallableStatement statement = null;
        int result = 0;
        String sql = "{call " + proc + "()}";
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareCall(sql);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("修改数据库出错！", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result != 0;
    }

}
