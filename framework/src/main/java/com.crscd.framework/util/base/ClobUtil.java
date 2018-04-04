package com.crscd.framework.util.base;

import com.crscd.framework.util.reflect.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialClob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * Created by zs
 * on 2017/7/19.
 */
public class ClobUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    //string转clob
    public static Clob str2Clob(String str) {
        Clob clob = null;
        try {
            clob = new SerialClob(str.toCharArray());
        } catch (SQLException e) {
            logger.error("Convert string " + str + " to clob wrong!", e);
        }
        return clob;
    }

    //clob转string
    public static String clob2Str(Clob clob) {
        StringBuilder stringBuff = new StringBuilder();
        try (Reader instream = clob.getCharacterStream();
             BufferedReader in = new BufferedReader(instream)) {
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuff.append(line);
            }
            if (instream != null) {
                instream.close();
            }
        } catch (SQLException | IOException e) {
            logger.error("Convert clob " + clob.toString() + " to string wrong!", e);
        }
        return stringBuff.toString();
    }
}
