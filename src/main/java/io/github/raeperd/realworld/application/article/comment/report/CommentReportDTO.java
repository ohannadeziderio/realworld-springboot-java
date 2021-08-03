package io.github.raeperd.realworld.application.article.comment.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("commentReport")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Getter
public class CommentReportDTO {

    @NotBlank
    private String description;

    @JsonCreator
    CommentReportDTO(String description) {
        this.description = description;
    }

}
