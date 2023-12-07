package hello.hellospring.service;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import hello.hellospring.domain.MajorDetail;
import org.springframework.context.annotation.Configuration;
import hello.hellospring.repository.majorsRepository;

import java.util.List;

@Configuration
public class MajorsService {
    private final majorsRepository majorsRepository;

    public MajorsService(hello.hellospring.repository.majorsRepository majorsRepository){
        this.majorsRepository = majorsRepository;
    }

    public MajorDetail showAll(String major_name){
        return majorsRepository.findMajor(major_name);
    }
    public String findMajorName(int majorNum){return majorsRepository.findMajorName(majorNum); }

}