package hello.hellospring.dto;

public class MajorDetailDTO{
    public MajorDetailDTO(){}

    public MajorDetailDTO(String major_info, String kind_of_student, String class_basic, String class_course) {
        this.major_info=major_info;
        this.kind_of_student = kind_of_student;
        this.class_basic = class_basic;
        this.class_course = class_course;
    }

    public String getMajor_info() {
        return major_info;
    }

    public void setMajor_info(String major_info) {
        this.major_info = major_info;
    }

    public String getKind_of_student() {
        return kind_of_student;
    }

    public void setKind_of_student(String kind_of_student) {
        this.kind_of_student = kind_of_student;
    }

    public String getClass_basic() {
        return class_basic;
    }

    public void setClass_basic(String class_basic) {
        this.class_basic = class_basic;
    }

    public String getClass_course() {
        return class_course;
    }

    public void setClass_course(String class_course) {
        this.class_course = class_course;
    }

    private String major_info;
    private String kind_of_student;
    private String class_basic;
    private String class_course;




}
