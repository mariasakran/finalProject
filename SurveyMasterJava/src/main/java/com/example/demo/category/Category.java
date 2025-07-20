package com.example.demo.category;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="type",unique = true)
    private String type;
    @Column
    private boolean isAccepted;

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category(String type) {
        this.type = type;
        this.isAccepted=false;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(type, category.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }
}
