package io.github.raeperd.realworld.application.article.comment;

import io.github.raeperd.realworld.domain.article.comment.Comment;
import io.github.raeperd.realworld.domain.article.comment.CommentReport;
import io.github.raeperd.realworld.domain.article.comment.CommentReportService;
import io.github.raeperd.realworld.infrastructure.jwt.UserJWTPayload;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class CommentReportController {

    private final CommentReportService commentReportService ;

    public CommentReportController(CommentReportService commentReportService) {
        this.commentReportService = commentReportService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/articles/{slug}/comments/{id}/report")
    public CommentReportModel reportComment(@AuthenticationPrincipal UserJWTPayload jwtPayload, @PathVariable String slug,
                                       @PathVariable long id, @Valid @RequestBody CommentReportDTO commentReportDTO) throws ChangeSetPersister.NotFoundException {

        var commentReportAdded = commentReportService.reportAComment(jwtPayload.getUserId(), slug, id, commentReportDTO);

        return CommentReportModel.fromCommentReport(commentReportAdded);
    }

    @GetMapping("/comment-reports")
    public Map<Comment, List<CommentReport>> getAllCommentsReports(@AuthenticationPrincipal UserJWTPayload jwtPayload) {

        final var commentsAndItsReports = commentReportService.getAllCommentsReportsByAuthor(jwtPayload.getUserId());

        return commentsAndItsReports;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comment-reports/{id}")
    public void deleteCommentReport(@AuthenticationPrincipal UserJWTPayload jwtPayload, @PathVariable long id) throws ChangeSetPersister.NotFoundException {
        commentReportService.deleteCommentReportById(jwtPayload.getUserId(), id);
    }

}
