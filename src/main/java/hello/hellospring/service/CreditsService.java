package hello.hellospring.service;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import org.springframework.context.annotation.Configuration;
import hello.hellospring.repository.creditsRepository;

import java.util.List;


@Configuration
public class CreditsService {
    private final creditsRepository creditsRepository;

    public CreditsService(hello.hellospring.repository.creditsRepository creditsRepository) {
        this.creditsRepository = creditsRepository;
    }
    public List<Credit> showAll(int semester, String id){
        return creditsRepository.find(semester, id);
    }
    public List<Classes> showAll_class(int cid){
        return creditsRepository.find_class((cid));
    }
    public List<Subject> showAll_subject(int sid){
        return creditsRepository.find_subject((sid));
    }

    public int find_sid(String name) {return creditsRepository.find_sid_by_class(name);}

    public int find_cid(String name) {return creditsRepository.find_cid_by_name(name);}

    public void credit_edit(int semester, Credit credit_object, String id){
        creditsRepository.credits_edits(semester, credit_object, id);
    }

    public void credit_delete(int semester, String id){
        creditsRepository.credits_delete(semester, id);;
    }
}
