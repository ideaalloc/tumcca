package com.tumcca.api.mapper;

import com.tumcca.api.model.DetectionBasicInfo;
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
public class DetectionBasicInfoMapper implements ResultSetMapper<DetectionBasicInfo> {
    @Override
    public DetectionBasicInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new DetectionBasicInfo(resultSet.getLong("REG_ID"), resultSet.getString("ACC_ELI_DETECT"), resultSet.getString("APPEARANCE_DETECT"), resultSet.getString("INTERIOR_DETECT"), resultSet.getString("ELEC_EQP_DETECT"), resultSet.getString("ENGINE_DETECT"), resultSet.getString("ROAD_TEST"), resultSet.getDouble("EXP_PRICE"), resultSet.getDouble("MIN_PRICE"), resultSet.getDouble("MAX_PRICE"));
    }
}
