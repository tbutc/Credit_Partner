package hello.hellospring.domain;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Classes {
    private int cid;
    private String name;
    private int credit;
    private int sid;

    // 생성자, getter, setter, toString 등 필요한 메소드 추가

//    public int getCid() {
//        return cid;
//    }
//
//    public void setCid(int cid) {
//        this.cid = cid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getCredit() {
//        return credit;
//    }
//
//    public void setCredit(int credit) {
//        this.credit = credit;
//    }
//
//    public int getSid() {
//        return sid;
//    }
//
//    public void setSid(int sid) {
//        this.sid = sid;

//    }

    @Override
    public String toString() {
        return "Class{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                ", sid=" + sid +
                '}';
    }
}
