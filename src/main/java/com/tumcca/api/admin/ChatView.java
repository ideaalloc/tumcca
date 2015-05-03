package com.tumcca.api.admin;

import com.google.common.base.Charsets;
import io.dropwizard.views.View;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-24
 */
public class ChatView extends View {
    public ChatView() {
        super("chat.ftl", Charsets.UTF_8);
    }
}
