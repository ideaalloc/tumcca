package com.tumcca.api.db;

import com.google.common.base.Optional;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public interface AdminSessionsDAO extends AutoCloseable {
    @SqlQuery("SELECT USERNAME FROM QCM_API_ADMIN_SESSIONS WHERE SESSION_ID = :session_id")
    public String findBySessionId(@Bind("session_id") Optional<String> sessionId);

    @SqlQuery("SELECT STATUS FROM QCM_API_ADMIN_SESSIONS WHERE SESSION_ID = :session_id")
    public Boolean findStatusBySessionId(@Bind("session_id") Optional<String> sessionId);

    @SqlQuery("SELECT SESSION_ID FROM QCM_API_ADMIN_SESSIONS WHERE USERNAME = :username")
    public String findSessionIdByUsername(@Bind("username") Optional<String> username);

    @SqlUpdate("INSERT INTO QCM_API_ADMIN_SESSIONS (SESSION_ID, USERNAME, STATUS, TIMEOUT) VALUES (:session_id, :username, :status, :timeout)")
    public void insert(@Bind("session_id") Optional<String> sessionId, @Bind("username") Optional<String> username, @Bind("status") Optional<Boolean> status, @Bind("timeout") Optional<Long> timeout);

    @SqlUpdate("UPDATE QCM_API_ADMIN_SESSIONS SET STATUS = :status, SIGN_OUT_TIME = CURRENT_TIMESTAMP WHERE USERNAME = :username")
    public void update(@Bind("username") Optional<String> username, @Bind("status") Optional<Boolean> status);

    @SqlUpdate("DELETE FROM QCM_API_ADMIN_SESSIONS WHERE SESSION_ID = :session_id")
    public void delete(@Bind("session_id") Optional<String> sessionId);

    @Override
    void close();
}
