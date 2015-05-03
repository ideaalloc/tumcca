package com.tumcca.api.mapper;

import com.tumcca.api.model.DistModel;
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
 * @since 2015-03-17
 */
public class DistModelMapper implements ResultSetMapper<DistModel> {
    @Override
    public DistModel map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new DistModel(resultSet.getLong("ID"), resultSet.getString("NAME"));
    }
}
