package com.study.section05.access.subsection02.property;

import javax.persistence.*;

@Entity(name = "member_section05_subsection02")
@Table(name = "tbl_member_section05_subsection02")
@Access(AccessType.PROPERTY)
public class Member {

//    @Id //필드가 우선권을 갖는다. 필드레벨에 id가 설정되어 있는 경우 기본 값은 AccessType.FIELD가 된다.
    @Column(name = "member_no")
    private int memberNo;
    @Column(name = "member_name")
    private String memberName;
    @Column
    private String nickName;
    @Column
    private String address;

    public Member() {
    }

    public Member(int memberNo, String memberName, String nickName, String address) {
        this.memberNo = memberNo;
        this.memberName = memberName;
        this.nickName = nickName;
        this.address = address;
    }

    @Id
    public int getMemberNo() {
        System.out.println("get No");
        return memberNo;
    }

    public String getMemberName() {
        System.out.println("get Name");
        return memberName;
    }

    public String getNickName() {
        System.out.println("get Nickname");
        return nickName;
    }

    public String getAddress() {
        System.out.println("get address");
        return address;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberName='" + memberName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
