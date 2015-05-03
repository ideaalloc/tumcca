package com.tumcca.api.mapper.admin;

import com.tumcca.api.model.admin.UserDetail;
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
public class UserDetailMapper implements ResultSetMapper<UserDetail> {
    @Override
    public UserDetail map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new UserDetail(resultSet.getString("SHOP_NAME"), resultSet.getString("USERNAME"), resultSet.getInt("CREDIT"));
    }

}
