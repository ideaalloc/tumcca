package com.tumcca.api.db;

import com.google.common.base.Optional;
import com.tumcca.api.mapper.admin.AdminMapper;
import com.tumcca.api.model.admin.Admin;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-18
 */
public interface AdminsDAO extends AutoCloseable {
    @SqlQuery("SELECT USERNAME, STATUS FROM QCM_API_ADMINS WHERE USERNAME = :username")
    @Mapper(AdminMapper.class)
    public Admin findByUsername(@Bind("username") Optional<String> username);

    @SqlQuery("SELECT PASSWORD FROM QCM_API_ADMINS WHERE USERNAME = :username")
    public String findPasswordByUsername(@Bind("username") Optional<String> username);

    @SqlUpdate("INSERT INTO QCM_API_ADMINS (USERNAME, PASSWORD, STATUS) VALUES (:username, :password, :status)")
    public void insert(@Bind("username") Optional<String> username, @Bind("password") Optional<String> password, @Bind("status") Optional<Boolean> status);

    @Override
    void close();
}
