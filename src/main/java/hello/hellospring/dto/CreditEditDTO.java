package hello.hellospring.dto;

public class CreditEditDTO {

    public CreditEditDTO(){} //기본 생성자

    public CreditEditDTO(String id, String class_name, int credit,String subject){
        this.class_name=class_name;
        this.credit=credit;
        this.id=id;
        this.subject=subject;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String class_name;
    private int credit;

    private String id;
    private String subject;


}
