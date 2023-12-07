package hello.hellospring.domain;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Subject {
    private int sid;
    private String name;

    // 생성자, getter, setter, toString 등 필요한 메소드 추가
//
//    public int getSid() {
//        return sid;
//    }
//
//    public void setSid(int sid) {
//        this.sid = sid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    @Override
    public String toString() {
        return "Subject{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                '}';
    }
}
