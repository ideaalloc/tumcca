package com.tumcca.api.mapper.admin;

import com.tumcca.api.model.admin.CarVersion;
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
 * @since 2015-03-22
 */
public class CarVersionMapper implements ResultSetMapper<CarVersion> {
    @Override
    public CarVersion map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new CarVersion(resultSet.getString("BRAND"), resultSet.getString("MODEL"), resultSet.getString("VERSION"));
    }
}
