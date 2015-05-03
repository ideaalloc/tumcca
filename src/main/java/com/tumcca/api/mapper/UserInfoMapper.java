package com.tumcca.api.mapper;

import com.tumcca.api.model.UserInfo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-14
 */
public class UserInfoMapper implements ResultSetMapper<UserInfo> {
    @Override
    public UserInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new UserInfo(resultSet.getString("CREDENTIALS"), resultSet.getInt("CREDIT"), resultSet.getLong("SHOP_ID"), resultSet.getString("SHOP_NAME"));
    }
}
