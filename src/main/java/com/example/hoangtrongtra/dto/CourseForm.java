package com.example.hoangtrongtra.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CourseForm {

    @NotBlank(message = "Tên học phần không được để trống")
    @Size(max = 150, message = "Tên học phần tối đa 150 ký tự")
    private String name;

    @Size(max = 500, message = "URL ảnh tối đa 500 ký tự")
    private String image;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Số tín chỉ phải lớn hơn 0")
    private Integer credits;

    @NotBlank(message = "Giảng viên không được để trống")
    @Size(max = 120, message = "Tên giảng viên tối đa 120 ký tự")
    private String lecturer;

    @NotNull(message = "Vui lòng chọn danh mục")
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
