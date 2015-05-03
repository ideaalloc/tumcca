package com.tumcca.api.db;

import com.google.common.base.Optional;
import com.tumcca.api.mapper.UserInfoMapper;
import com.tumcca.api.mapper.UserSecretMapper;
import com.tumcca.api.mapper.admin.UserDetailMapper;
import com.tumcca.api.mapper.admin.UserMapper;
import com.tumcca.api.model.UserInfo;
import com.tumcca.api.model.UserSecret;
import com.tumcca.api.model.admin.User;
import com.tumcca.api.model.admin.UserDetail;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-14
 */
public interface ShopUsersDAO extends AutoCloseable {
    @SqlQuery("SELECT (SELECT US.SESSION_ID FROM QCM_SHOP_USER_SESSIONS US WHERE US.USER_ID = U.USER_ID) CREDENTIALS, U.CREDIT, U.SHOP_ID, S.NAME SHOP_NAME from QCM_SHOP_USERS U JOIN QCM_SHOPS S ON (U.SHOP_ID = S.ID) WHERE U.USERNAME = :username")
    @Mapper(UserInfoMapper.class)
    public UserInfo findByUsername(@Bind("username") Optional<String> username);

    @SqlQuery("SELECT USER_ID, PASSWORD FROM QCM_SHOP_USERS WHERE USERNAME = :username")
    @Mapper(UserSecretMapper.class)
    public UserSecret findPasswordByUsername(@Bind("username") Optional<String> username);

    @SqlQuery("SELECT USERNAME FROM QCM_SHOP_USERS WHERE USER_ID = :user_id")
    public String findUsernameByUserId(@Bind("user_id") Optional<Long> userId);

    @SqlUpdate("INSERT INTO QCM_SHOP_USERS(SHOP_ID, USERNAME, PASSWORD, CREDIT) VALUES (:shop_id, :username, :password, :credit)")
    @GetGeneratedKeys
    public Long insert(@Bind("shop_id") Optional<Long> shopId, @Bind("username") Optional<String> username, @Bind("password") Optional<String> password, @Bind("credit") Optional<Integer> credit);

    @SqlQuery("SELECT USER_ID, SHOP_ID, USERNAME, CREDIT FROM QCM_SHOP_USERS LIMIT :index, :page_size")
    @Mapper(UserMapper.class)
    public List<User> listUsers(@Bind("index") Optional<Integer> index, @Bind("page_size") Optional<Integer> pageSize);

    @SqlQuery("SELECT (SELECT NAME FROM QCM_SHOPS S WHERE S.ID = U.SHOP_ID) SHOP_NAME, USERNAME, CREDIT FROM QCM_SHOP_USERS U WHERE USER_ID = :user_id")
    @Mapper(UserDetailMapper.class)
    public UserDetail findUserDetail(@Bind("user_id") Optional<Long> userId);

    @Override
    void close();
}
