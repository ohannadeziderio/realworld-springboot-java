package io.github.raeperd.realworld.domain.article.comment.report;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CommentReportRepository extends Repository<CommentReport, Long> {

    CommentReport save(CommentReport commentReport);

    void deleteById(long commentReportId);

    Optional<CommentReport> findById(long commentReportId);

    List<CommentReport> findAllByUser_Id(long userId);

    List<CommentReport> findAllByComment_Id(long commentId);

}
