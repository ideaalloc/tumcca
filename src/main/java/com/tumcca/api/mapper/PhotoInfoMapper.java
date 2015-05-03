package com.tumcca.api.mapper;

import com.tumcca.api.model.PhotoInfo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-21
 */
public class PhotoInfoMapper implements ResultSetMapper<PhotoInfo> {
    @Override
    public PhotoInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new PhotoInfo(resultSet.getLong("ID"), resultSet.getLong("REG_ID"), resultSet.getString("NAME"), resultSet.getString("STORAGE_NAME"), resultSet.getBoolean("STATUS"));
    }
}
