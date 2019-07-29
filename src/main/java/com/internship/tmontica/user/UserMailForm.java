package com.internship.tmontica.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;

@Setter
@Getter
class UserMailForm {

    private SimpleMailMessage msg;
    private static final String ADMIN_ADDRESS = "tmontica701@gmail.com";
    private User user;
    private String authenticationKey;
    private static final String ACTIVE_API_LINK = "http://tmontica-idev.tmon.co.kr/signin?active=true&";
    private static final String ID_PARAM = "id=";
    private static final String TOKEN_PARAM = "&token=";
    private MailType mailType;

     UserMailForm(MailType mailType, User user) {
        msg = new SimpleMailMessage();
        msg.setFrom(ADMIN_ADDRESS);
        msg.setTo(user.getEmail());
        setUser(user);
        setMailType(mailType);
    }

     public void makeMail(){
         switch (mailType){

             case FIND_ID:
                 makeFindIdEmail();
                 break;
             case FIND_PW:
                 makeFindPwEmail();
                 break;
             case SIGN_UP:
                 makeActiveEmail();
                 break;
             default:
         }
     }

    private void makeFindIdEmail(){

        msg.setSubject("[TMONG CAFFE]"+user.getName()+"님 아이디 찾기 메일입니다.");
        msg.setText("인증키는 ["+authenticationKey+"]입니다.\n 본인과 관련없는 메일이라면 무시하시면 됩니다.");
    }

    private void makeFindPwEmail(){

        msg.setSubject("[TMONG CAFFE]"+user.getName()+"님 비밀번호 찾기 메일입니다.");
        msg.setText("찾으시는 비밀번호는 "+user.getPassword()+"입니다.\n 본인과 관련없는 메일이라면 무시하시면 됩니다.");
    }

    private void makeActiveEmail(){

        msg.setSubject("[TMONG CAFFE]"+user.getName()+"님 회원가입 인증 메일입니다.");
        msg.setText("다음의 링크를 눌러 계정인증을 진행해주세요.\n[ "+
                ACTIVE_API_LINK+ID_PARAM+user.getId()+
                TOKEN_PARAM+authenticationKey+
                " ]\n 본인과 관련없는 메일이라면 무시하시면 됩니다.");
    }

}
