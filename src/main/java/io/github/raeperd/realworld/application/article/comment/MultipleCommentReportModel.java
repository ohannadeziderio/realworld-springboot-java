package io.github.raeperd.realworld.application.article.comment;

import io.github.raeperd.realworld.domain.article.comment.Comment;
import io.github.raeperd.realworld.domain.article.comment.CommentReport;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class MultipleCommentReportModel {

    List<CommentReportModel.CommentReportModelNested> comments;

    public MultipleCommentReportModel(List<CommentReportModel.CommentReportModelNested> commentsReportsCollected) {
    }

    static MultipleCommentReportModel fromCommentsReports(Set<CommentReport> commentsReports) {
        final var commentsReportsCollected = commentsReports.stream().map(CommentReportModel.CommentReportModelNested::fromCommentReport)
                .collect(toList());
        return new MultipleCommentReportModel(commentsReportsCollected);
    }

}
