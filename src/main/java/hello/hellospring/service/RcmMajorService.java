package hello.hellospring.service;

import hello.hellospring.domain.RcmMajor;
import hello.hellospring.repository.RcmMajorRepository;
import org.springframework.stereotype.Service;

@Service
public class RcmMajorService {

    private final RcmMajorRepository rcmMajorRepository;

    public RcmMajorService(RcmMajorRepository rcmMajorRepository) {
        this.rcmMajorRepository = rcmMajorRepository;
    }

    public int findMajorId(String major){
        return rcmMajorRepository.findMajorId(major);
    }

    public void RcmMajorEdit(RcmMajor major){
        rcmMajorRepository.RcmMajorEdit(major);
    }

    public void RcmMajorEdit(int rcmMajorId1, int rcmMajorId2, int rcmMajorId3, String sid) {
        rcmMajorRepository.RcmMajorEdit(rcmMajorId1, rcmMajorId2, rcmMajorId3, sid);
    }

    public void deleteRcm(String id) {
        rcmMajorRepository.deleteRcm(id);
    }
}
