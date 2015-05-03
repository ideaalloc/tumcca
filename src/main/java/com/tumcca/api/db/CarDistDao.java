package com.tumcca.api.db;

import com.google.common.base.Optional;
import com.tumcca.api.mapper.DistModelMapper;
import com.tumcca.api.model.DistModel;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-17
 */
public interface CarDistDao extends AutoCloseable {
    @SqlQuery("SELECT ID, NAME FROM QCM_CAR_BRANDS")
    @Mapper(DistModelMapper.class)
    public List<DistModel> findDists();

    @SqlQuery("SELECT ID, NAME FROM QCM_CAR_MODELS WHERE BRAND_ID = :brand_id")
    @Mapper(DistModelMapper.class)
    public List<DistModel> findModelsByBrandId(@Bind("brand_id") Optional<Long> brandId);

    @SqlQuery("SELECT ID, NAME FROM QCM_CAR_VERSIONS WHERE MODEL_ID = :model_id")
    @Mapper(DistModelMapper.class)
    public List<DistModel> findVersionsByModelId(@Bind("model_id") Optional<Long> modelId);

    @Override
    void close();
}
