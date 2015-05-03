package com.tumcca.api.auth;

import com.google.common.base.Optional;
import com.tumcca.api.db.ShopUserSessionsDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QicaimaAuthenticator implements Authenticator<String, String> {
    static final Logger LOGGER = LoggerFactory.getLogger(QicaimaAuthenticator.class);

    final DBI dbi;

    public QicaimaAuthenticator(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public Optional<String> authenticate(String credentials) throws AuthenticationException {
        LOGGER.info("Authenticate now....");
        LOGGER.info("Credentials {}", credentials);

        try (final ShopUserSessionsDAO shopUserSessionsDAO = dbi.open(ShopUserSessionsDAO.class)) {
            Boolean status = shopUserSessionsDAO.findStatusBySessionId(Optional.of(credentials));

            if (status != null && status) {
                LOGGER.info("Authorized successfully");
                Long userId = shopUserSessionsDAO.findBySessionId(Optional.of(credentials));
                return Optional.of(String.valueOf(userId));
            }
        }

        LOGGER.info("Not signed in yet, failed to authenticate");
        return Optional.absent();
    }
}
