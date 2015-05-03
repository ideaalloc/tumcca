package com.tumcca.api.mapper.admin;

import com.tumcca.api.model.admin.Admin;
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
 * @since 2015-03-18
 */
public class AdminMapper implements ResultSetMapper<Admin> {
    @Override
    public Admin map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Admin(resultSet.getString("USERNAME"), resultSet.getBoolean("STATUS"));
    }
}
