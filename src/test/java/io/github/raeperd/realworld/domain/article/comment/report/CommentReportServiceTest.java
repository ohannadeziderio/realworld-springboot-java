package io.github.raeperd.realworld.domain.article.comment.report;

import io.github.raeperd.realworld.domain.article.ArticleService;
import io.github.raeperd.realworld.domain.article.comment.CommentService;
import io.github.raeperd.realworld.domain.user.UserFindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CommentReportServiceTest {

    private CommentReportService commentReportService;

    @Mock
    private UserFindService userFindService;

    @Mock
    private CommentService commentService;

    @Mock
    private ArticleService articleService;

    @Mock
    private CommentReportRepository commentReportRepository;

    @BeforeEach
    private void initializeService() {
        commentReportService = new CommentReportService(userFindService, commentService, articleService, commentReportRepository);
    }

    @Test
    void when_commentService_return_empty_expect_NoSuchElementException() {
        assertThatThrownBy(() ->
                commentReportService.reportAComment(20, "slug", 30, null)
        ).isInstanceOf(NoSuchElementException.class);
    }
}
