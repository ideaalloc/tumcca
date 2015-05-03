package com.tumcca.api.mapper;

import com.tumcca.api.model.CarBasicInfo;
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
public class CarBasicInfoMapper implements ResultSetMapper<CarBasicInfo> {
    @Override
    public CarBasicInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new CarBasicInfo(resultSet.getLong("REG_ID"), resultSet.getString("CAR_ID"), resultSet.getString("OWNER_NAME"), resultSet.getString("OWNER_PHONE"), resultSet.getLong("VERSION_ID"), resultSet.getDouble("MILEAGE"), resultSet.getInt("TRANSFER_TIMES"), resultSet.getDate("REG_DATE"));
    }
}
