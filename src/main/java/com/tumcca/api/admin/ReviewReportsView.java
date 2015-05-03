package com.tumcca.api.admin;

import com.google.common.base.Charsets;
import com.tumcca.api.model.admin.ReviewReports;
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
public class ReviewReportsView extends View {
    final ReviewReports reviewReports;

    public ReviewReportsView(ReviewReports reviewReports) {
        super("review-reports.ftl", Charsets.UTF_8);
        this.reviewReports = reviewReports;
    }

    public ReviewReports getReviewReports() {
        return reviewReports;
    }
}
