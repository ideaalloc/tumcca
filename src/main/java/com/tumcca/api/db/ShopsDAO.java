package com.tumcca.api.db;

import com.tumcca.api.mapper.DistModelMapper;
import com.tumcca.api.model.DistModel;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-22
 */
public interface ShopsDAO extends AutoCloseable {
    @SqlQuery("SELECT ID, NAME FROM QCM_SHOPS")
    @Mapper(DistModelMapper.class)
    List<DistModel> findShops();

    @Override
    void close();
}
