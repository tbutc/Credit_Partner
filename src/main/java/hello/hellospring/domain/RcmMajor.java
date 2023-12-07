package hello.hellospring.domain;

import lombok.Builder;

import javax.persistence.*;



@Entity
@Builder
@Table(name = "recommended_major")
public class RcmMajor { //extends BaseTimeEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가하도록 지정
    @Column(name = "pid") // 컬럼명 지정
    private Long pid;



    @Column(name = "mid1") // 컬럼명 지정
    private int mid1;

    @Column(name = "mid2") // 컬럼명 지정
    private int mid2;

    @Column(name = "mid3") // 컬럼명 지정
    private int mid3;


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getMid1() {
        return mid1;
    }

    public void setMid1(int mid1) {
        this.mid1 = mid1;
    }

    public int getMid2() {
        return mid2;
    }

    public void setMid2(Integer mid2) {
        this.mid2 = mid2;
    }

    public int getMid3() {
        return mid3;
    }

    public void setMid3(Integer mid3) {
        this.mid3 = mid3;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Column(name = "sid") // 컬럼명 지정
    private String sid;

    public RcmMajor(){
        this.pid = 0L;
        this.mid1 = 0;
        this.mid2 = 0;
        this.mid3 = 0;
        this.sid = null;
    }

    public RcmMajor(Long pid, int mid1, int mid2, int mid3, String sid){
        this.pid = pid;
        this.mid1 = mid1;
        this.mid2 = mid2;
        this.mid3 = mid3;
        this.sid = sid;
    }

}