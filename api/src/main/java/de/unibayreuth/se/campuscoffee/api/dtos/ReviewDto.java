package de.unibayreuth.se.campuscoffee.api.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class ReviewDto {

    private Long id;
    private LocalDateTime createdAt;
    private Long posId;
    private Long authorId;

    @NotBlank(message = "Das Review darf nicht leer sein.")
    private String review;

    private Boolean approved;

    public ReviewDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPosId() {
        return posId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
