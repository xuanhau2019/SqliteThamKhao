package com.example.sachsqlite;

import java.util.Objects;

public class Author {
    private int matacgia;
    private String tentacgia;

    public int getMatacgia() {
        return matacgia;
    }

    public void setMatacgia(int matacgia) {
        this.matacgia = matacgia;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public Author(int matacgia, String tentacgia) {
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
    }

    public Author() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return matacgia == author.matacgia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matacgia);
    }

    @Override
    public String toString() {
        return "Author{" +
                "matacgia=" + matacgia +
                ", tentacgia='" + tentacgia + '\'' +
                '}';
    }
}
