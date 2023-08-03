package com.study.section02.column;

import lombok.*;

import javax.persistence.*;
import javax.swing.text.Style;
import java.util.Date;

@Entity(name = "member_section02") //엔티티 객체로 만들기 위한 어노테이션 (name: 엔티티를 식별하기 위한 네임)
@Table(name = "tbl_member_section02") //데이터 베이스 엑세스 시 (name: 테이블 이름을 생성하기 위한 네임)
public class Member {

    @Id //PK
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id", length = 10)
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String memberNick;

    @Column(name = "phone", columnDefinition/*컬럼기본값 설정*/ = "varchar(200) default '010-0000-0000'")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address", nullable = false) //NOT NULL
    private String address;

    @Column(name = "enroll_date")
    @Temporal(TemporalType.TIMESTAMP) //날짜 시간 같이...
    private Date enrollDate;

    @Column(name = "member_role")
    private String memberRole;

    @Column(name = "status")
    private String status;

    public Member() {
    }

    public Member(int memberNo, String memberId, String memberPwd, String memberNick, String phone, String email, String address, Date enrollDate, String memberRole, String status) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberNick = memberNick;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", memberNick='" + memberNick + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", enrollDate=" + enrollDate +
                ", memberRole='" + memberRole + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
