package io.github.raeperd.realworld.domain.article.comment;

import io.github.raeperd.realworld.domain.article.Article;
import io.github.raeperd.realworld.domain.user.Profile;
import io.github.raeperd.realworld.domain.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "comments_reports")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class CommentReport {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private Comment comment;

    @Column(name = "description", nullable = false)
    private String description;

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private User user;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    public CommentReport() {
    }

    public CommentReport(Comment comment, String description, User user) {
        this.comment = comment;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Comment getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentReport that = (CommentReport) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
