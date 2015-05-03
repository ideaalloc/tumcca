package com.tumcca.api.mapper.admin;

import com.tumcca.api.model.admin.Review;
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
public class ReviewMapper implements ResultSetMapper<Review> {
    @Override
    public Review map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Review(resultSet.getLong("REG_ID"), resultSet.getString("PROCESS"), resultSet.getString("MESSAGE"), resultSet.getInt("INTEGRAL"), resultSet.getString("AUDITOR"), resultSet.getDate("CREATE_TIME"));
    }
}
