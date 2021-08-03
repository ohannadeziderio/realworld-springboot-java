package io.github.raeperd.realworld.domain.article.comment;

import io.github.raeperd.realworld.application.article.comment.CommentReportDTO;
import io.github.raeperd.realworld.domain.user.User;
import io.github.raeperd.realworld.domain.user.UserFindService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CommentReportService {

    private final UserFindService userFindService;
    private final CommentService commentService;
    private final CommentReportRepository commentReportRepository;

    public CommentReportService(UserFindService userFindService, CommentService commentService, CommentReportRepository commentReportRepository) {
        this.userFindService = userFindService;
        this.commentService = commentService;
        this.commentReportRepository = commentReportRepository;
    }


    @Transactional
        public CommentReport reportAComment(long userId, String slug, long commentId, CommentReportDTO commentReportDTO) throws ChangeSetPersister.NotFoundException {
            Comment comment = commentService.findById(commentId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            User user = userFindService.findById(userId).orElseThrow();

            boolean isTheAuthorTheSameUser = comment.getAuthor().getEmail().equals(user.getEmail());

            if(isTheAuthorTheSameUser) {
                throw null;
            }

            CommentReport commentReport = new CommentReport(comment, commentReportDTO.getDescription(), user);

            return commentReportRepository.save(commentReport);
        }

    @Transactional
    public Map<Comment, List<CommentReport>> getAllCommentsReportsByAuthor(long userId) {
        List<Comment> comments = commentService.findAllByAuthor_Id(userId);
        Map<Comment, List<CommentReport>> commentsAndItsReports = new HashMap<>();

        if(comments.size() > 0) {


            comments.forEach(comment -> {
                commentsAndItsReports.put(comment, commentReportRepository.findAllByComment_Id(comment.getId()));
            });
        }

        return commentsAndItsReports;
    }

    @Transactional
    public void deleteCommentReportById(long userId, long commentReportId) throws ChangeSetPersister.NotFoundException {
        CommentReport comment = commentReportRepository.findById(commentReportId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        User user = userFindService.findById(userId).orElseThrow();

        boolean isTheAuthorTheSameUser = comment.getUser().getEmail().equals(user.getEmail());

        if(isTheAuthorTheSameUser) {
            commentReportRepository.deleteById(commentReportId);
        }
    }
}
