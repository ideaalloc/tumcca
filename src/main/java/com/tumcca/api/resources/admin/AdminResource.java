package com.tumcca.api.resources.admin;

import com.google.common.base.Optional;
import com.tumcca.api.db.CarInfoDAO;
import com.tumcca.api.db.ShopUsersDAO;
import com.tumcca.api.admin.*;
import com.tumcca.api.model.CarBasicInfo;
import com.tumcca.api.model.DetectionBasicInfo;
import com.tumcca.api.model.admin.CarVersion;
import com.tumcca.api.model.admin.Review;
import com.tumcca.api.model.admin.ReviewReports;
import io.dropwizard.jersey.params.LongParam;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-17
 */
@Path("/admin")
@Produces(MediaType.TEXT_HTML)
public class AdminResource {
    static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    final DBI dbi;

    public AdminResource(DBI dbi) {
        this.dbi = dbi;
    }

    @GET
    public IndexView index() {
        return new IndexView();
    }

    @GET
    @Path("/home")
    public HomeView home() {
        return new HomeView();
    }

    @GET
    @Path("/model")
    public ModelView model() {
        return new ModelView();
    }

    @GET
    @Path("/user")
    public UserView user() {
        return new UserView();
    }

    @GET
    @Path("/user-add")
    public UserAddView userAdd() {
        return new UserAddView();
    }

    @GET
    @Path("/user-view/{userId}")
    public UserViewView userView(@PathParam("userId") LongParam userId) {
        LOGGER.info("View user by {}", userId);
        try (final ShopUsersDAO shopUsersDAO = dbi.open(ShopUsersDAO.class)) {
            return new UserViewView(shopUsersDAO.findUserDetail(Optional.of(userId.get())));
        }
    }

    @GET
    @Path("/review-reports/{regId}")
    public ReviewReportsView reviewReports(@PathParam("regId") LongParam regId) {
        LOGGER.info("Get review reports by {}", regId);
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            CarBasicInfo carBasicInfo = carInfoDAO.findBasic(Optional.of(regId.get()));
            CarVersion carVersion = carInfoDAO.findCarVersion(Optional.of(carBasicInfo.getVersionId()));
            DetectionBasicInfo detection = carInfoDAO.findDetection(Optional.of(regId.get()));
            if (detection == null) {
                detection = new DetectionBasicInfo();
            }
            List<Long> photoIds = carInfoDAO.findPhotoIds(Optional.of(regId.get()));
            Review review = carInfoDAO.findReview(Optional.of(regId.get()));

            ReviewReports reviewReports = new ReviewReports(regId.get(), carBasicInfo.getCarId(), carBasicInfo.getOwnerName(), carBasicInfo.getOwnerPhone(), carVersion.getBrand(), carVersion.getModel(), carVersion.getVersion(), carBasicInfo.getMileage(), carBasicInfo.getTransferTimes(), carBasicInfo.getRegDate(), detection.getAccidentEliminationDetection(), detection.getAppearanceDetection(), detection.getInteriorDetection(), detection.getElectricalEquipmentDetection(), detection.getEngineDetection(), detection.getRoadTest(), detection.getExpectedPrice(), detection.getMinPrice(), detection.getMaxPrice(), photoIds, review.getProcess(), review.getMessage(), review.getIntegral(), review.getAuditor());
            return new ReviewReportsView(reviewReports);
        }
    }

    @GET
    @Path("/review")
    public ReviewView review() {
        return new ReviewView();
    }
}
