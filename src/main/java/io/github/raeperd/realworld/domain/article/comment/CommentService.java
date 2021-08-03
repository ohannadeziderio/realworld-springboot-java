package io.github.raeperd.realworld.domain.article.comment;

import io.github.raeperd.realworld.domain.article.ArticleFindService;
import io.github.raeperd.realworld.domain.article.comment.report.CommentRepository;
import io.github.raeperd.realworld.domain.user.User;
import io.github.raeperd.realworld.domain.user.UserFindService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.util.Optionals.mapIfAllPresent;

@Service
public class CommentService implements CommentFindService {

    private final UserFindService userFindService;
    private final ArticleFindService articleFindService;
    private final CommentRepository commentRepository;

    CommentService(UserFindService userFindService, ArticleFindService articleFindService, CommentRepository commentRepository) {
        this.userFindService = userFindService;
        this.articleFindService = articleFindService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment createComment(long userId, String slug, String body) {
        return mapIfAllPresent(userFindService.findById(userId), articleFindService.getArticleBySlug(slug),
                (user, article) -> user.writeCommentToArticle(article, body))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Set<Comment> getComments(long userId, String slug) {
        return mapIfAllPresent(userFindService.findById(userId), articleFindService.getArticleBySlug(slug),
                User::viewArticleComments)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deleteCommentById(long userId, String slug, long commentId) {
        final var articleContainsComments = articleFindService.getArticleBySlug(slug)
                .orElseThrow(NoSuchElementException::new);
        userFindService.findById(userId)
                .ifPresentOrElse(user -> user.deleteArticleComment(articleContainsComments, commentId),
                        () -> {throw new NoSuchElementException();});
    }

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllByAuthor_Id(long authorId) {
        return commentRepository.findAllByAuthor_Id(authorId);
    }
}
