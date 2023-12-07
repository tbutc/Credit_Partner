package hello.hellospring.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
@Entity
@Builder
@Table(name = "member")
public class Member implements UserDetails { //extends BaseTimeEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가하도록 지정
    @Column(name = "PID") // 컬럼명 지정
    private Long pid;

    @Column(name = "ID") // 컬럼명 지정
    private String id;

    @Column(name = "NAME") // 컬럼명 지정
    private String name;

    @Column(name = "PWD") // 컬럼명 지정
    private String pwd;

    @Enumerated(EnumType.STRING)
    private Authority role;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Member() {
    }

    // 매개변수가 있는 생성자 추가
    public Member(Long pid, String id, String name, String pwd, Authority role, List<String> roles) {
        this.pid = pid;
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.role = role;
        this.roles = roles;
    }


    public void encodePassword(PasswordEncoder passwordEncoder){
        this.pwd = passwordEncoder.encode(pwd);
    }
    public boolean checkPassword(PasswordEncoder passwordEncoder, String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.pwd);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
