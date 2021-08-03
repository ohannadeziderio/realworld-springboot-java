package io.github.raeperd.realworld.domain.article.comment;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends Repository<Comment, Long> {

    Optional<Comment> findById(long id);

    List<Comment> findAllByAuthor_Id(long authorId);

}
