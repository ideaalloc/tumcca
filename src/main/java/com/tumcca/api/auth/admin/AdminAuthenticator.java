package com.tumcca.api.auth.admin;

import com.google.common.base.Optional;
import com.tumcca.api.db.AdminSessionsDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public class AdminAuthenticator implements Authenticator<String, String> {
    static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthenticator.class);

    final DBI dbi;

    public AdminAuthenticator(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public Optional<String> authenticate(String credentials) throws AuthenticationException {
        LOGGER.info("Admin authenticate now....");
        LOGGER.info("Credentials {}", credentials);

        try (final AdminSessionsDAO adminSessionsDAO = dbi.open(AdminSessionsDAO.class)) {
            Boolean status = adminSessionsDAO.findStatusBySessionId(Optional.of(credentials));
            if (status != null && status) {
                LOGGER.info("Authorized successfully");
                String username = adminSessionsDAO.findBySessionId(Optional.of(credentials));
                return Optional.of(username);
            }
        }

        LOGGER.info("Not signed in yet, failed to authenticate");
        return Optional.absent();
    }
}
