package io.github.raeperd.realworld.domain.article.comment;

import java.util.List;
import java.util.Optional;

public interface CommentFindService {

    Optional<Comment> findById(long id);

    List<Comment> findAllByAuthor_Id(long authorId);

}
