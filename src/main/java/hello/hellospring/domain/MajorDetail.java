package hello.hellospring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class MajorDetail {

    public  MajorDetail(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //자동 증가하도록 지정
    @Column(name = "PID") // 컬럼명 지정
    private long pid;

    private String major_info;
    private String kind_of_student;
    private String class_basic;
    private String class_course;


}