package hello.hellospring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Credit {

    public Credit(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //자동 증가하도록 지정
    @Column(name = "PID") // 컬럼명 지정
    private long pid;

    private int sid; //학생
    private int cid; //과목(화작, 수학1)
    private int credit;
    private int semester;

}
