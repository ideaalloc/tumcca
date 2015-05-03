package com.tumcca.api.admin;

import com.google.common.base.Charsets;
import com.tumcca.api.model.admin.UserDetail;
import io.dropwizard.views.View;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-22
 */
public class UserViewView extends View {
    final UserDetail userDetail;

    public UserViewView(UserDetail userDetail) {
        super("user-view.ftl", Charsets.UTF_8);
        this.userDetail = userDetail;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }
}
