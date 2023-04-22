package com.example.sp5chap03.assembler;


import com.example.sp5chap03.spring.ChangePasswordService;
import com.example.sp5chap03.spring.MemberDao;
import com.example.sp5chap03.spring.MemberRegisterService;

public class Assembler {

    private MemberDao memberDao;
    private MemberRegisterService regSvc;
    private ChangePasswordService pwdSvc;

    public Assembler() {
        memberDao = new MemberDao();
        regSvc = new MemberRegisterService(memberDao);  // 생성자 주입

        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao); // Setter 주입
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    public MemberRegisterService getMemberRegisterService() {
        return regSvc;
    }

    public ChangePasswordService getChangePasswordService() {
        return pwdSvc;
    }

}