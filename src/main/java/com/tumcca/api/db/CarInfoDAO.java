package com.tumcca.api.db;

import com.google.common.base.Optional;
import com.tumcca.api.mapper.CarBasicInfoMapper;
import com.tumcca.api.mapper.DetectionBasicInfoMapper;
import com.tumcca.api.mapper.PhotoInfoMapper;
import com.tumcca.api.mapper.admin.CarVersionMapper;
import com.tumcca.api.mapper.admin.ReviewMapper;
import com.tumcca.api.model.CarBasicInfo;
import com.tumcca.api.model.DetectionBasicInfo;
import com.tumcca.api.model.PhotoInfo;
import com.tumcca.api.model.admin.CarVersion;
import com.tumcca.api.model.admin.Review;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-20
 */
public interface CarInfoDAO extends Transactional<CarInfoDAO>, AutoCloseable {
    @SqlUpdate("INSERT INTO QCM_CAR_BASIC_INFO (CAR_ID, OWNER_NAME, OWNER_PHONE, VERSION_ID, MILEAGE, TRANSFER_TIMES, REG_DATE, STATUS, CREATOR) VALUES (:car_id, :owner_name, :owner_phone, :version_id, :mileage, :transfer_times, :reg_date, :status, :creator)")
    @GetGeneratedKeys
    public Long insertBasic(@Bind("car_id") Optional<String> carId, @Bind("owner_name") Optional<String> ownerName, @Bind("owner_phone") Optional<String> ownerPhone, @Bind("version_id") Optional<Long> versionId, @Bind("mileage") Optional<Double> mileage, @Bind("transfer_times") Optional<Integer> transferTimes, @Bind("reg_date") Optional<Date> regDate, @Bind("status") Optional<Boolean> status, @Bind("creator") Optional<Long> creator);

    @SqlUpdate("UPDATE QCM_CAR_BASIC_INFO SET CAR_ID = :car_id, OWNER_NAME = :owner_name, OWNER_PHONE = :owner_phone, VERSION_ID = :version_id, MILEAGE = :mileage, TRANSFER_TIMES = :transfer_times, REG_DATE = :reg_date, STATUS = :status, UPDATE_TIME = :update_time WHERE REG_ID = :reg_id")
    public void updateBasic(@Bind("reg_id") Optional<Long> regId, @Bind("car_id") Optional<String> carId, @Bind("owner_name") Optional<String> ownerName, @Bind("owner_phone") Optional<String> ownerPhone, @Bind("version_id") Optional<Long> versionId, @Bind("mileage") Optional<Double> mileage, @Bind("transfer_times") Optional<Integer> transferTimes, @Bind("reg_date") Optional<Date> regDate, @Bind("status") Optional<Boolean> status, @Bind("update_time") Optional<Date> updateTime);

    @SqlQuery("SELECT REG_ID, CAR_ID, OWNER_NAME, OWNER_PHONE, VERSION_ID, MILEAGE, TRANSFER_TIMES, REG_DATE FROM QCM_CAR_BASIC_INFO WHERE REG_ID = :reg_id")
    @Mapper(CarBasicInfoMapper.class)
    public CarBasicInfo findBasic(@Bind("reg_id") Optional<Long> regId);

    @SqlUpdate("INSERT INTO QCM_CAR_DETECTION_INFO (REG_ID, ACC_ELI_DETECT, APPEARANCE_DETECT, INTERIOR_DETECT, ELEC_EQP_DETECT, ENGINE_DETECT, ROAD_TEST, EXP_PRICE, MIN_PRICE, MAX_PRICE, STATUS, CREATOR) VALUES (:reg_id, :acc_eli_detect, :appearance_detect, :interior_detect, :elec_eqp_detect, :engine_detect, :road_test, :exp_price, :min_price, :max_price, :status, :creator)")
    public void insertDetection(@Bind("reg_id") Optional<Long> regId, @Bind("acc_eli_detect") Optional<String> accEliDetect, @Bind("appearance_detect") Optional<String> appearanceDetect, @Bind("interior_detect") Optional<String> interiorDetect, @Bind("elec_eqp_detect") Optional<String> elecEqpDetect, @Bind("engine_detect") Optional<String> engineDetect, @Bind("road_test") Optional<String> roadTest, @Bind("exp_price") Optional<Double> expPrice, @Bind("min_price") Optional<Double> minPrice, @Bind("max_price") Optional<Double> maxPrice, @Bind("status") Optional<Boolean> status, @Bind("creator") Optional<Long> creator);

    @SqlUpdate("UPDATE QCM_CAR_DETECTION_INFO SET ACC_ELI_DETECT = :acc_eli_detect, APPEARANCE_DETECT = :appearance_detect, INTERIOR_DETECT = :interior_detect, ELEC_EQP_DETECT = :elec_eqp_detect, ENGINE_DETECT = :engine_detect, ROAD_TEST = :road_test, EXP_PRICE = :exp_price, MIN_PRICE = :min_price, MAX_PRICE = :max_price, STATUS = :status, UPDATE_TIME = :update_time WHERE REG_ID = :reg_id")
    public void updateDetection(@Bind("reg_id") Optional<Long> regId, @Bind("acc_eli_detect") Optional<String> accEliDetect, @Bind("appearance_detect") Optional<String> appearanceDetect, @Bind("interior_detect") Optional<String> interiorDetect, @Bind("elec_eqp_detect") Optional<String> elecEqpDetect, @Bind("engine_detect") Optional<String> engineDetect, @Bind("road_test") Optional<String> roadTest, @Bind("exp_price") Optional<Double> expPrice, @Bind("min_price") Optional<Double> minPrice, @Bind("max_price") Optional<Double> maxPrice, @Bind("status") Optional<Boolean> status, @Bind("update_time") Optional<Date> updateTime);

    @SqlQuery("SELECT REG_ID, ACC_ELI_DETECT, APPEARANCE_DETECT, INTERIOR_DETECT, ELEC_EQP_DETECT, ENGINE_DETECT, ROAD_TEST, EXP_PRICE, MIN_PRICE, MAX_PRICE FROM QCM_CAR_DETECTION_INFO WHERE REG_ID = :reg_id")
    @Mapper(DetectionBasicInfoMapper.class)
    public DetectionBasicInfo findDetection(@Bind("reg_id") Optional<Long> regId);

    @SqlUpdate("INSERT INTO QCM_CAR_PHOTOS (REG_ID, NAME, STORAGE_NAME, STATUS, CREATOR) VALUES (:reg_id, :name, :storage_name, :status, :creator)")
    @GetGeneratedKeys
    public Long insertPhotoInfo(@Bind("reg_id") Optional<Long> regId, @Bind("name") Optional<String> name, @Bind("storage_name") Optional<String> storageName, @Bind("status") Optional<Boolean> status, @Bind("creator") Optional<Long> creator);

    @SqlQuery("SELECT ID, REG_ID, NAME, STORAGE_NAME, STATUS FROM QCM_CAR_PHOTOS WHERE ID = :id")
    @Mapper(PhotoInfoMapper.class)
    public PhotoInfo findPhotoInfo(@Bind("id") Optional<Long> id);

    @SqlQuery("SELECT ID FROM QCM_CAR_PHOTOS WHERE REG_ID = :reg_id")
    public List<Long> findPhotoIds(@Bind("reg_id") Optional<Long> regId);

    @SqlUpdate("DELETE FROM QCM_CAR_PHOTOS WHERE ID = :id")
    public void deletePhotoInfo(@Bind("id") Optional<Long> id);

    @SqlUpdate("INSERT INTO QCM_CAR_INFO_REVIEW (REG_ID, PROCESS, MESSAGE, INTEGRAL) VALUES (:reg_id, :process, :message, :integral)")
    public void insertReview(@Bind("reg_id") Optional<Long> regId, @Bind("process") Optional<String> process, @Bind("message") Optional<String> message, @Bind("integral") Optional<Integer> integral);

    @SqlUpdate("UPDATE QCM_CAR_INFO_REVIEW SET PROCESS = :process, MESSAGE = :message, INTEGRAL = :integral, AUDITOR = :auditor, UPDATE_TIME = CURRENT_TIMESTAMP WHERE REG_ID = :reg_id")
    public void updateReview(@Bind("reg_id") Optional<Long> regId, @Bind("process") Optional<String> process, @Bind("message") Optional<String> message, @Bind("integral") Optional<Integer> integral, @Bind("auditor") Optional<String> auditor);

    @SqlQuery("SELECT REG_ID, PROCESS, MESSAGE, INTEGRAL, AUDITOR, CREATE_TIME FROM QCM_CAR_INFO_REVIEW WHERE REG_ID = :reg_id")
    @Mapper(ReviewMapper.class)
    public Review findReview(@Bind("reg_id") Optional<Long> regId);

    @SqlQuery("SELECT B.NAME BRAND, M.NAME MODEL, V.NAME VERSION FROM QCM_CAR_VERSIONS V JOIN QCM_CAR_MODELS M ON (M.ID = V.MODEL_ID) JOIN QCM_CAR_BRANDS B ON (B.ID = M.BRAND_ID) WHERE V.ID = :version_id")
    @Mapper(CarVersionMapper.class)
    public CarVersion findCarVersion(@Bind("version_id") Optional<Long> versionId);

    @Override
    void close();
}
