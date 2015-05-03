package com.tumcca.api.resources;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumcca.api.model.CarBasicInfo;
import com.tumcca.api.model.SignIn;
import com.tumcca.api.model.UserInfo;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.jackson.Jackson;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class CarRegistrationResourceTest {
    static final ObjectMapper JSON_MAPPER = Jackson.newObjectMapper();

    @Test
    public void testAddCarBasicInfo() throws Exception {
        JerseyClientConfiguration configuration = new JerseyClientConfiguration();
        configuration.setChunkedEncodingEnabled(false);
        postRequest(configuration);
    }

    private void postRequest(JerseyClientConfiguration configuration) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Client jersey = new JerseyClientBuilder(new MetricRegistry())
                .using(executor, JSON_MAPPER)
                .using(configuration)
                .build("jersey-test");
        jersey.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        jersey.property(ClientProperties.READ_TIMEOUT, 5000);
        Response response = jersey.target("http://127.0.0.1:9080" + "/api/sign-in")
                .request()
                .buildPost(Entity.entity(new SignIn("wuwenchuan", "888888"), APPLICATION_JSON))
                .invoke();

        assertThat(response.getHeaderString(HttpHeaders.CONTENT_TYPE)).isEqualTo(APPLICATION_JSON);
        UserInfo responseMessage = response.readEntity(UserInfo.class);

        final String credentials = responseMessage.getCredentials();

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        response = jersey.target("http://127.0.0.1:9080" + "/api/car-basic-info")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + credentials)
                .buildPost(Entity.entity(new CarBasicInfo(null, "20160324230159", "gwawhwa", "138225256252", 6197L, 0.0, 2, new Date(cal.getTimeInMillis())), APPLICATION_JSON))
                .invoke();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getHeaderString(HttpHeaders.CONTENT_TYPE)).isEqualTo(APPLICATION_JSON);
        final CarInfoResponse carInfoResponse = response.readEntity(CarInfoResponse.class);

        System.out.println(String.format("Response regId %d", carInfoResponse.regId));
        executor.shutdown();
        jersey.close();
    }

    static class CarInfoResponse {
        Long regId;

        public CarInfoResponse() {
        }

        public CarInfoResponse(@JsonProperty Long regId) {
            this.regId = regId;
        }

        @JsonProperty
        public Long getRegId() {
            return regId;
        }
    }

}