package hello.hellospring.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {

    //성공 했을 때
    SUCCESS(true, 200, "SUCCESS!"),

    SUCCESS_NO_POST(true, 201, "게시글이 하나도 없습니다"),

    //300 ~ POST ERROR
    //게시글 예외 처리 만들 때, 여기 만들면 됨!
    //300~GET요청
    NO_RELATED_POST(false,300,"관련된 게시글이 없습니다."),
    NO_RELATED_COMMENT(false,300,"해당 게시글의 댓글이 아닙니다."),
    NO_RELATED_COMMENTREPLY(false,300,"해당 댓글의 대댓글이 아닙니다."),
    NO_COMMENT(false,301,"댓글이 없습니다."),
    //330~POST요청
    NO_TITLE(false, 330, "제목을 입력해주세요."),
    NO_CATEGORY(false, 331, "카테고리를 선택해주세요."),
    NO_TAG(false,332,"태그를 선택해주세요."),
    NO_CONTEXT(false, 333, "내용을 입력해주세요."),

    NOT_EXIST_USER(false, 340, "존재하지 않는 회원입니다."),

    //360~PUT요청
    NOT_WRITER(false,360,"작성자가 아닙니다."),
    NO_MODIFIED_CONTEXT(false,361,"수정된 부분이 없습니다."),

    //380~공용
    NOT_EXIST_POST(false, 380, "존재하지 않는 게시글입니다."),
    NOT_EXIST_COMMENT(false, 380, "존재하지 않는 댓글입니다."),
    NOT_EXIST_COMMENTREPLY(false, 380, "존재하지 않는 대댓글입니다."),

    NO_LIKE_NUMBER(false, 390, "좋아요가 없습니다."),
    //400 ~ USER ERROR
    //로그인 예외 처리 만들 때, 여기 만들면 됨!

    NOT_FOUND_USER(false, 401, "찾을 수 없는 유저입니다."),


    // 채팅 예외
    NO_CHATROOM(false, 404, "채팅방이 존재하지 않습니다."),
    ALREADY_MATCH(false, 400, "이미 매칭이 완료되었습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;
}