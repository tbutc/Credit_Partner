package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository{
    Member save(Member member);
    Optional<Member> findById(String id);
    Optional<Member> findByName(String name);

    Member findById_not_null(String id);

    List<Member> findAll();

}
