package com.tumcca.api.resources.admin;

import com.google.common.base.Optional;
import com.tumcca.api.db.CarInfoDAO;
import com.tumcca.api.model.admin.DataTable;
import com.tumcca.api.model.admin.Review;
import com.tumcca.api.model.admin.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.util.IntegerMapper;

import javax.validation.Valid;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class DataResource {
    static final String[] USER_COLUMNS = new String[]{
            "USER_ID", "SHOP_ID", "USERNAME", "CREDIT"
    };

    static final String[] REVIEW_COLUMNS = new String[]{
            "REG_ID", "PROCESS", "INTEGRAL", "CREATE_TIME", "MESSAGE"
    };

    final DBI dbi;

    public DataResource(DBI dbi) {
        this.dbi = dbi;
    }

    @POST
    @Path("/add-review")
    public Response submitReview(@Valid Review review) {
        try (final CarInfoDAO carInfoDAO = dbi.open(CarInfoDAO.class)) {
            if (carInfoDAO.findBasic(Optional.of(review.getRegId())) == null) {
                return Response.status(404).entity("Reg Id invalid").build();
            }
            carInfoDAO.updateReview(Optional.of(review.getRegId()), Optional.of(review.getProcess()), Optional.of(review.getMessage()), Optional.of(review.getIntegral()), Optional.of(review.getAuditor()));
        }

        return Response.ok(1).build();
    }

    @POST
    @Path("/reviews")
    public Response getReviews(
            @FormParam("start") Optional<Integer> start,
            @FormParam("length") Optional<Integer> length,
            @FormParam("search[regex]") Optional<Boolean> searchRegex,
            @FormParam("search[value]") Optional<String> searchValue,
            @FormParam("draw") Optional<Integer> draw,
            @FormParam("order[0][column]") Optional<Integer> orderColumn,
            @FormParam("order[0][dir]") Optional<String> orderDir) {
        StringBuilder reviewQuery = new StringBuilder();
        reviewQuery.append("SELECT REG_ID, PROCESS, MESSAGE, INTEGRAL, AUDITOR, CREATE_TIME FROM QCM_CAR_INFO_REVIEW ");

        try (final Handle handle = dbi.open()) {
            Integer countAll = handle.createQuery(wrapCount(reviewQuery).toString())
                    .map(IntegerMapper.FIRST).first();

            Integer countFiltered;

            if (searchValue.isPresent()) {
                reviewQuery.append("WHERE PROCESS LIKE '%").append(searchValue.get()).append("%' ");
                countFiltered = handle.createQuery(wrapCount(reviewQuery).toString())
                        .map(IntegerMapper.FIRST).first();
            } else {
                countFiltered = countAll;
            }

            if (orderColumn.isPresent()) {
                reviewQuery.append("ORDER BY ").append(REVIEW_COLUMNS[orderColumn.get()]).append(" ").append(orderDir.get()).append(" ");
            }

            reviewQuery.append("LIMIT :index, :page_size");

            final List<Review> reviews = handle.createQuery(reviewQuery.toString())
                    .bind("index", start)
                    .bind("page_size", length)
                    .map((int i, ResultSet resultSet, StatementContext statementContext) ->
                            new Review(resultSet.getLong("REG_ID"), resultSet.getString("PROCESS"),
                                    resultSet.getString("MESSAGE"),
                                    resultSet.getInt("INTEGRAL"), resultSet.getString("AUDITOR"),
                                    resultSet.getDate("CREATE_TIME"))).list();
            return Response.ok(new DataTable<>(draw.get(), countAll, countFiltered, reviews)).build();
        }

    }

    @POST
    @Path("/users")
    public Response getUsers(
            @FormParam("start") Optional<Integer> start,
            @FormParam("length") Optional<Integer> length,
            @FormParam("search[regex]") Optional<Boolean> searchRegex,
            @FormParam("search[value]") Optional<String> searchValue,
            @FormParam("draw") Optional<Integer> draw,
            @FormParam("order[0][column]") Optional<Integer> orderColumn,
            @FormParam("order[0][dir]") Optional<String> orderDir) {
        StringBuilder userQuery = new StringBuilder();
        userQuery.append("SELECT USER_ID, SHOP_ID, USERNAME, CREDIT FROM QCM_SHOP_USERS ");

        try (final Handle handle = dbi.open()) {
            Integer countAll = handle.createQuery(wrapCount(userQuery).toString())
                    .map(IntegerMapper.FIRST).first();

            Integer countFiltered;

            if (searchValue.isPresent()) {
                userQuery.append("WHERE USERNAME LIKE '%").append(searchValue.get()).append("%' ");
                countFiltered = handle.createQuery(wrapCount(userQuery).toString())
                        .map(IntegerMapper.FIRST).first();
            } else {
                countFiltered = countAll;
            }

            if (orderColumn.isPresent()) {
                userQuery.append("ORDER BY ").append(USER_COLUMNS[orderColumn.get()]).append(" ").append(orderDir.get()).append(" ");
            }

            userQuery.append("LIMIT :index, :page_size");

            final List<User> users =
                    handle.createQuery(userQuery.toString())
                            .bind("index", start)
                            .bind("page_size", length)
                            .map((int i, ResultSet resultSet, StatementContext statementContext) ->
                                    new User(resultSet.getLong("USER_ID"), resultSet.getLong("SHOP_ID"),
                                            resultSet.getString("USERNAME"),
                                            resultSet.getInt("CREDIT"))).list();
            return Response.ok(new DataTable<>(draw.get(), countAll, countFiltered, users)).build();
        }

    }

    StringBuilder wrapCount(StringBuilder query) {
        StringBuilder countQuery = new StringBuilder();
        countQuery.append("SELECT COUNT(*) FROM (");
        countQuery.append(query);
        countQuery.append(") V");
        return countQuery;
    }
}
