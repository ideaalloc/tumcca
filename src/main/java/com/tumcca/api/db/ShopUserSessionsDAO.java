package com.tumcca.api.db;

import com.google.common.base.Optional;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-14
 */
public interface ShopUserSessionsDAO extends AutoCloseable {
    @SqlQuery("SELECT US.USER_ID FROM QCM_SHOP_USER_SESSIONS US WHERE US.SESSION_ID = :session_id")
    public Long findBySessionId(@Bind("session_id") Optional<String> sessionId);

    @SqlQuery("SELECT US.STATUS FROM QCM_SHOP_USER_SESSIONS US WHERE US.SESSION_ID = :session_id")
    public Boolean findStatusBySessionId(@Bind("session_id") Optional<String> sessionId);

    @SqlUpdate("INSERT INTO QCM_SHOP_USER_SESSIONS (SESSION_ID, USER_ID, STATUS) values (:session_id, :user_id, :status)")
    public void insert(@Bind("session_id") Optional<String> sessionId, @Bind("user_id") Optional<Long> userId, @Bind("status") Optional<Boolean> status);

    @SqlUpdate("UPDATE QCM_SHOP_USER_SESSIONS SET STATUS = :status WHERE USER_ID = :user_id")
    public void update(@Bind("user_id") Optional<Long> userId, @Bind("status") Optional<Boolean> status);

    @SqlUpdate("DELETE FROM QCM_SHOP_USER_SESSIONS WHERE SESSION_ID = :session_id")
    public void delete(@Bind("session_id") Optional<String> sessionId);

    @Override
    void close();
}
