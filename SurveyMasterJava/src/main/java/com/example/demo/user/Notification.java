package com.example.demo.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="Notifications")
public class Notification {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @Column( nullable = false)
 private Long userId;
 @Column( nullable = false)
 @NotBlank(message = "content cannot be empty or null")
 private String title;
 @Column( nullable = false)
 @NotBlank(message = "content cannot be empty or null")
 private String content;
 @Column
 private boolean isAccepted;


    public Notification(Long userId, String title, String content, boolean isAccepted) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isAccepted = isAccepted;
    }

    public Notification() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
       this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", isAccepted=" + isAccepted +
                '}';
    }
}
