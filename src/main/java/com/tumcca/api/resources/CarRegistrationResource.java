package com.tumcca.api.resources;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.tumcca.api.db.CarDistDao;
import com.tumcca.api.db.CarInfoDAO;
import com.tumcca.api.db.ShopUsersDAO;
import com.tumcca.api.model.*;
import com.tumcca.api.model.admin.Review;
import com.tumcca.api.model.admin.ReviewMessage;
import com.tumcca.api.util.ImageUtil;
import com.tumcca.api.util.NumberUtil;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.LongParam;
import org.atmosphere.wasync.Event;
import org.atmosphere.wasync.Function;
import org.atmosphere.wasync.RequestBuilder;
import org.atmosphere.wasync.Socket;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.tumcca.api.util.NumberUtil.isNumber;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-12
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class CarRegistrationResource {
    static final Logger LOGGER = LoggerFactory.getLogger(CarRegistrationResource.class);

    final String uploadPath;

    final DBI dbi;

    final AtmosphereClient reviewClient;

    final RequestBuilder reviewRequest;

    public CarRegistrationResource(String uploadPath, DBI dbi, AtmosphereClient reviewClient, RequestBuilder reviewRequest) {
        this.uploadPath = uploadPath;
        this.dbi = dbi;
        this.reviewClient = reviewClient;
        this.reviewRequest = reviewRequest;
    }

    @GET
    @Path("/car-brands")
    public Response getBrands(@Auth String principal) {
        LOGGER.info("Get car brands");
        try (final CarDistDao carDistDao = dbi.open(CarDistDao.class)) {
            return Response.ok(carDistDao.findDists()).build();
        }
    }

    @GET
    @Path("/car-models/{brandId}")
    public Response getModels(@Auth String principal, @PathParam("brandId") LongParam brandId) {
        LOGGER.info("Get car models by {}", brandId);
        try (final CarDistDao carDistDao = dbi.open(CarDistDao.class)) {
            return Response.ok(carDistDao.findModelsByBrandId(Optional.of(brandId.get()))).build();
        }
    }

    @GET
    @Path("/car-versions/{modelId}")
    public Response getVersions(@Auth String principal, @PathParam("modelId") LongParam modelId) {
        LOGGER.info("Get car versions by {}", modelId);
        try (final CarDistDao carDistDao = dbi.open(CarDistDao.class)) {
            return Response.ok(carDistDao.findVersionsByModelId(Optional.of(modelId.get()))).build();
        }
    }

    @POST
    @Path("/car-basic-info")
    public Response addCarBasicInfo(@Auth String principal, @Valid CarBasicInfo carBasicInfo) {
        LOGGER.info("Principal {} is going to add car basic info {}....", principal, carBasicInfo);
        Long userId;
        if (isNumber(principal)) {
            userId = Long.parseLong(principal);
        } else {
            userId = 0L;
        }
        Long regId;
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            try {
                carInfoDAO.begin();
                regId = carInfoDAO.insertBasic(Optional.of(carBasicInfo.getCarId()), Optional.of(carBasicInfo.getOwnerName()), Optional.of(carBasicInfo.getOwnerPhone()), Optional.of(carBasicInfo.getVersionId()), Optional.of(carBasicInfo.getMileage()), Optional.of(carBasicInfo.getTransferTimes()), Optional.of(carBasicInfo.getRegDate()), Optional.of(true), Optional.of(userId));
                carInfoDAO.insertReview(Optional.of(regId), Optional.of(ReviewProcess.UNDER_REVIEW.getProcess()), Optional.of(ReviewProcess.UNDER_REVIEW.getProcess()), Optional.of(0));
                carInfoDAO.commit();
            } catch (Exception e) {
                carInfoDAO.rollback();
                String errorInfo = "Car info insertion error";
                LOGGER.info(errorInfo, e);
                return Response.status(500).entity(errorInfo).build();
            }
        }

        return Response.ok(ImmutableMap.of("regId", regId)).build();
    }

    @PUT
    @Path("/car-basic-info/{regId}")
    public Response updateCarBasicInfo(@Auth String principal, @PathParam("regId") LongParam regId, @Valid CarBasicInfo carBasicInfo) {
        LOGGER.info("Principal {} is going to update car basic info by car reg id {}....", principal, regId);
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            if (carInfoDAO.findBasic(Optional.of(regId.get())) == null) {
                LOGGER.info("Reg id {} not found", regId);
                return Response.status(404).entity("Reg id not found, cannot process update").build();
            }
            try {
                carInfoDAO.updateBasic(Optional.of(regId.get()), Optional.of(carBasicInfo.getCarId()), Optional.of(carBasicInfo.getOwnerName()), Optional.of(carBasicInfo.getOwnerPhone()), Optional.of(carBasicInfo.getVersionId()), Optional.of(carBasicInfo.getMileage()), Optional.of(carBasicInfo.getTransferTimes()), Optional.of(carBasicInfo.getRegDate()), Optional.of(true), Optional.of(new Date()));
            } catch (Exception e) {
                String errorInfo = "Car info update error";
                LOGGER.info(errorInfo, e);
                return Response.status(500).entity(errorInfo).build();
            }
        }

        return Response.ok(ImmutableMap.of("regId", regId.get())).build();
    }

    @GET
    @Path("/car-basic-info/{regId}")
    public Response getCarBasicInfo(@Auth String principal, @PathParam("regId") LongParam regId) {
        LOGGER.info("Principal {} is going to get car basic info by car reg id {}....", principal, regId);
        CarBasicInfo carBasicInfo;
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            carBasicInfo = carInfoDAO.findBasic(Optional.of(regId.get()));
        } catch (Exception e) {
            String errorInfo = "Car info find error";
            LOGGER.info(errorInfo, e);
            return Response.status(500).entity(errorInfo).build();
        }
        if (carBasicInfo == null) {
            return Response.status(404).entity("Car info not found").build();
        }
        return Response.ok(carBasicInfo).build();
    }

    @POST
    @Path("/detection-basic-info")
    public Response addDetectionBasicInfo(@Auth String principal, @Valid DetectionBasicInfo detectionBasicInfo) {
        LOGGER.info("Principal {} is going to add car detection info {}....", principal, detectionBasicInfo);
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            if (carInfoDAO.findBasic(Optional.of(detectionBasicInfo.getRegId())) == null) {
                LOGGER.info("There is no regId {}", detectionBasicInfo.getRegId());
                return Response.status(404).entity("There is no reg id registered in the car basic info, please register basic info first").build();
            }
            Long userId;
            if (isNumber(principal)) {
                userId = Long.parseLong(principal);
            } else {
                userId = 0L;
            }
            try {
                carInfoDAO.insertDetection(Optional.of(detectionBasicInfo.getRegId()), Optional.of(detectionBasicInfo.getAccidentEliminationDetection()), Optional.of(detectionBasicInfo.getAppearanceDetection()), Optional.of(detectionBasicInfo.getInteriorDetection()), Optional.of(detectionBasicInfo.getElectricalEquipmentDetection()), Optional.of(detectionBasicInfo.getEngineDetection()), Optional.of(detectionBasicInfo.getRoadTest()), Optional.of(detectionBasicInfo.getExpectedPrice()), Optional.of(detectionBasicInfo.getMinPrice()), Optional.of(detectionBasicInfo.getMaxPrice()), Optional.of(true), Optional.of(userId));
            } catch (Exception e) {
                String errorInfo = "Car detection insertion error";
                LOGGER.info(errorInfo, e);
                return Response.status(500).entity(errorInfo).build();
            }
        }

        return Response.ok(ImmutableMap.of("regId", detectionBasicInfo.getRegId())).build();
    }

    @PUT
    @Path("/detection-basic-info/{regId}")
    public Response updateDetectionBasicInfo(@Auth String principal, @PathParam("regId") LongParam regId, @Valid DetectionBasicInfo detectionBasicInfo) {
        LOGGER.info("Principal {} is going to update car detection info {}....", principal, regId);
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            if (carInfoDAO.findDetection(Optional.of(regId.get())) == null) {
                LOGGER.info("Reg id {} not found", regId);
                return Response.status(404).entity("Reg id not found, cannot process update").build();
            }
            try {
                carInfoDAO.updateDetection(Optional.of(regId.get()), Optional.of(detectionBasicInfo.getAccidentEliminationDetection()), Optional.of(detectionBasicInfo.getAppearanceDetection()), Optional.of(detectionBasicInfo.getInteriorDetection()), Optional.of(detectionBasicInfo.getElectricalEquipmentDetection()), Optional.of(detectionBasicInfo.getEngineDetection()), Optional.of(detectionBasicInfo.getRoadTest()), Optional.of(detectionBasicInfo.getExpectedPrice()), Optional.of(detectionBasicInfo.getMinPrice()), Optional.of(detectionBasicInfo.getMaxPrice()), Optional.of(true), Optional.of(new Date()));
            } catch (Exception e) {
                String errorInfo = "Car detection update error";
                LOGGER.info(errorInfo, e);
                return Response.status(500).entity(errorInfo).build();
            }
        }
        return Response.ok(ImmutableMap.of("regId", regId.get())).build();
    }

    @GET
    @Path("/detection-basic-info/{regId}")
    public Response getDetectionBasicInfo(@Auth String principal, @PathParam("regId") LongParam regId) {
        LOGGER.info("Principal {} is going to get car detection info {}....", principal, regId);
        DetectionBasicInfo detectionBasicInfo;
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            detectionBasicInfo = carInfoDAO.findDetection(Optional.of(regId.get()));
        } catch (Exception e) {
            String errorInfo = "Car detection not found error";
            LOGGER.info(errorInfo, e);
            return Response.status(404).entity(errorInfo).build();
        }
        if (detectionBasicInfo == null) {
            return Response.status(404).entity("Car detection not found").build();
        }
        return Response.ok(detectionBasicInfo).build();
    }

    @POST
    @Path("/upload-photo/{regId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPhoto(@Auth String principal, @PathParam("regId") LongParam regId,
                                @FormDataParam("carPhoto") InputStream uploadedInputStream,
                                @FormDataParam("carPhoto") FormDataContentDisposition fileDetail) {
        LOGGER.info("Principal {} is going to upload car photo....", principal);

        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            CarBasicInfo carBasicInfo;
            try {
                carBasicInfo = carInfoDAO.findBasic(Optional.of(regId.get()));
            } catch (Exception e) {
                String errorInfo = "Car info find error";
                LOGGER.info(errorInfo, e);
                return Response.status(500).entity(errorInfo).build();
            }
            if (carBasicInfo == null) {
                return Response.status(404).entity("Car info not found").build();
            }

            final String uniqueName = UUID.randomUUID().toString();
            Long photoId;

            Long userId;
            if (isNumber(principal)) {
                userId = Long.parseLong(principal);
            } else {
                userId = 0L;
            }

            try {
                final java.nio.file.Path filePath = Paths.get(uploadPath, uniqueName);
                Files.copy(uploadedInputStream, filePath);
                final String newUniqueName = UUID.randomUUID().toString();
                ImageIO.write(ImageIO.read(filePath.toFile()), "jpeg", Paths.get(uploadPath, newUniqueName).toFile());
                filePath.toFile().delete();
                final String fileName = fileDetail.getFileName();
                final String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".jpg";
                photoId = carInfoDAO.insertPhotoInfo(Optional.of(regId.get()), Optional.of(newFileName), Optional.of(newUniqueName), Optional.of(true), Optional.of(userId));
            } catch (IOException e) {
                String errorMessage = "Upload file error";
                LOGGER.info(errorMessage, e);
                return Response.status(500).entity(errorMessage).build();
            }

            return Response.ok(new PhotoSimple(regId.get(), photoId)).build();
        }

    }

    @GET
    @Path("/get-photo-ids/{regId}")
    public Response getPhotoIds(@Auth String principal, @PathParam("regId") LongParam regId) {
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            List<Long> photoIds = carInfoDAO.findPhotoIds(Optional.of(regId.get()));
            if (photoIds == null || photoIds.isEmpty()) {
                return Response.status(404).entity("Not found").build();
            }
            return Response.ok(ImmutableMap.of("photoIds", photoIds)).build();
        }
    }

    @DELETE
    @Path("/delete-photo/{photoId}")
    public Response deletePhoto(@Auth String principal, @PathParam("photoId") LongParam photoId) {
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            PhotoInfo photoInfo = carInfoDAO.findPhotoInfo(Optional.of(photoId.get()));
            File photo = Paths.get(uploadPath, photoInfo.getStorageName()).toFile();
            if (!photo.exists()) {
                throw new WebApplicationException(404);
            }
            photo.delete();
            carInfoDAO.deletePhotoInfo(Optional.of(photoId.get()));
        }
        return Response.ok(1).build();
    }

    @GET
    @Path("/get-photo/{photoId}")
    @Produces("image/jpeg")
    public Response getPhoto(@PathParam("photoId") LongParam photoId) {
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            PhotoInfo photoInfo = carInfoDAO.findPhotoInfo(Optional.of(photoId.get()));
            if (photoInfo == null) {
                return Response.status(404).entity("Id Not found").build();
            }

            File photo = Paths.get(uploadPath, photoInfo.getStorageName()).toFile();

            if (!photo.exists()) {
                throw new WebApplicationException(404);
            }

            return Response.ok(photo)
                    .header("Content-Disposition",
                            "attachment; filename=" + photoInfo.getName()).build();
        }
    }

    @GET
    @Path("/get-photo-thumb/{photoId}")
    @Produces("image/jpeg")
    public Response getPhotoThumbnail(@PathParam("photoId") LongParam photoId) {
        try (CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            PhotoInfo photoInfo = carInfoDAO.findPhotoInfo(Optional.of(photoId.get()));
            if (photoInfo == null) {
                return Response.status(404).entity("Id Not found").build();
            }

            File photo = Paths.get(uploadPath, photoInfo.getStorageName()).toFile();

            if (!photo.exists()) {
                throw new WebApplicationException(404);
            }

            BufferedImage bufferedImage;

            try {
                bufferedImage = ImageIO.read(photo);
            } catch (IOException e) {
                LOGGER.info("Read image error", e);
                return Response.status(404).entity("Read image error").build();
            }

            final BufferedImage thumbnail = ImageUtil.createThumbnail(bufferedImage);
            final java.nio.file.Path thumbnailPath = Paths.get(uploadPath, photoInfo.getStorageName() + "-thumbnail");
            final File thumbnailFile = thumbnailPath.toFile();
            if (!thumbnailFile.exists()) {
                try {
                    thumbnailFile.createNewFile();
                } catch (IOException e) {
                    LOGGER.info("Create new thumbnail error");
                    return Response.status(404).entity("Create new thumbnail error").build();
                }
            }
            try {
                ImageIO.write(thumbnail, "jpeg", thumbnailFile);
            } catch (IOException e) {
                LOGGER.info("Write thumbnail error", e);
                return Response.status(404).entity("Create thumbnail error").build();
            }

            final String fileName = photoInfo.getName();
            final String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "-thumbnail.jpg";

            return Response.ok(thumbnailFile)
                    .header("Content-Disposition",
                            "attachment; filename=" + newFileName).build();
        }
    }


    @POST
    @Path("/inform-review/{regId}")
    public Response reviewCarInfo(@Auth String principal, @PathParam("regId") LongParam regId) throws IOException {
        Review review;
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            review = carInfoDAO.findReview(Optional.of(regId.get()));
            if (review == null) {
                LOGGER.info("No such regId {}", regId);
                return Response.status(404).entity("No such regId").build();
            }
        }

        String username;
        if (NumberUtil.isNumber(principal)) {
            try (final ShopUsersDAO shopUsersDAO = dbi.open(ShopUsersDAO.class)) {
                username = shopUsersDAO.findUsernameByUserId(Optional.of(Long.parseLong(principal)));
            }
        } else {
            username = principal;
        }
        final ReviewMessage reviewMessage = new ReviewMessage(username, regId.get(), review.getProcess());

        Socket socket = null;
        try {
            socket = reviewClient.create();
            socket.on("message", new Function<ReviewMessage>() {
                @Override
                public void on(ReviewMessage reviewMessage) {
                    LOGGER.info("Got message: {}", reviewMessage);
                }
            }).on(Event.CLOSE.name(), t ->
                            LOGGER.info("Connection closed")
            ).open(reviewRequest.build());

            socket.fire(reviewMessage);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
        return Response.ok(ImmutableMap.of("regId", regId.get())).build();
    }

    @GET
    @Path("/review-result/{regId}")
    public Response getReviewResult(@Auth String principal, @PathParam("regId") LongParam regId) {
        Review review;
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            review = carInfoDAO.findReview(Optional.of(regId.get()));
            if (review == null) {
                LOGGER.info("No such regId {}", regId);
                return Response.status(404).entity("No such regId").build();
            }
        }
        return Response.ok(review).build();
    }

}
