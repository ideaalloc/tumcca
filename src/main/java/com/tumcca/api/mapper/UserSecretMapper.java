package com.tumcca.api.mapper;

import com.tumcca.api.model.UserSecret;
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
 * @since 2015-03-20
 */
public class UserSecretMapper implements ResultSetMapper<UserSecret> {
    @Override
    public UserSecret map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new UserSecret(resultSet.getLong("USER_ID"), resultSet.getString("PASSWORD"));
    }
}
