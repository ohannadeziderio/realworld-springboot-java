package io.github.raeperd.realworld.application.article.comment.report;

import io.github.raeperd.realworld.application.user.ProfileModel;
import io.github.raeperd.realworld.domain.article.comment.report.CommentReport;
import lombok.Value;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CommentReportModel {

    CommentReportModel.CommentReportModelNested commentReport;

    public CommentReportModel(CommentReportModelNested fromCommentReport) {
    }

    static CommentReportModel fromCommentReport(CommentReport commentReport) {
        return new CommentReportModel(CommentReportModel.CommentReportModelNested.fromCommentReport(commentReport));
    }

    @Value
    static class CommentReportModelNested {
        long id;
        String body;
        ZonedDateTime createdAt;
        ZonedDateTime updatedAt;
        ProfileModel.ProfileModelNested user;

        static CommentReportModel.CommentReportModelNested fromCommentReport(CommentReport commentReport) {
            return new CommentReportModel.CommentReportModelNested(commentReport.getId(),
                    commentReport.getDescription(),
                    commentReport.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    commentReport.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    ProfileModel.ProfileModelNested.fromProfile(commentReport.getUser().getProfile()));
        }
    }
}
