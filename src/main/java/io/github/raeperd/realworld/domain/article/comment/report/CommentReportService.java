package io.github.raeperd.realworld.domain.article.comment.report;

import io.github.raeperd.realworld.application.article.comment.report.CommentReportDTO;
import io.github.raeperd.realworld.domain.article.Article;
import io.github.raeperd.realworld.domain.article.ArticleService;
import io.github.raeperd.realworld.domain.article.comment.Comment;
import io.github.raeperd.realworld.domain.article.comment.CommentService;
import io.github.raeperd.realworld.domain.user.User;
import io.github.raeperd.realworld.domain.user.UserFindService;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class CommentReportService {

    private final UserFindService userFindService;
    private final CommentService commentService;
    private final ArticleService articleService;

    private final CommentReportRepository commentReportRepository;

    public CommentReportService(UserFindService userFindService, CommentService commentService, ArticleService articleService, CommentReportRepository commentReportRepository) {
        this.userFindService = userFindService;
        this.commentService = commentService;
        this.articleService = articleService;
        this.commentReportRepository = commentReportRepository;
    }


    @Transactional
    public CommentReport reportAComment(long userId, String slug, long commentId, CommentReportDTO commentReportDTO) throws Exception {
        Article article = articleService.getArticleBySlug(slug).orElseThrow(NoSuchElementException::new);
        Comment comment = commentService.findById(commentId).orElseThrow(NoSuchElementException::new);
        User user = userFindService.findById(userId).orElseThrow(NoSuchElementException::new);

        if(isTheAuthorEqualTheUser(user, article.getAuthor())) {
            throw new Exception();
        }

        CommentReport commentReport = new CommentReport(comment, commentReportDTO.getDescription(), user);

        return commentReportRepository.save(commentReport);
    }

    @Transactional
    public Map<Comment, List<CommentReport>> getAllCommentsReportsByAuthor(long userId) {
        List<Article> articles = articleService.findAllByAuthorId(userId);
        Map<Comment, List<CommentReport>> commentsAndItsReports = new HashMap<>();

        if(!articles.isEmpty()) {
            articles.forEach(article -> {
                if(!article.getComments().isEmpty()) {
                    article.getComments().forEach(comment -> {
                        commentsAndItsReports.put(comment, commentReportRepository.findAllByComment_Id(comment.getId()));
                    });
                }
            });
        }

        return commentsAndItsReports;
    }

    @Transactional
    public void deleteCommentReportById(long userId, long commentReportId) {
        CommentReport commentReport = commentReportRepository.findById(commentReportId).orElseThrow(NoSuchElementException::new);
        User user = userFindService.findById(userId).orElseThrow(NoSuchElementException::new);

        if(isTheAuthorEqualTheUser(user, commentReport.getComment().getAuthor())) {
            commentReportRepository.deleteById(commentReportId);
        }
    }

    private boolean isTheAuthorEqualTheUser(User user, User author) {
        return (user.equals(author) ? true : false);
    }
}
