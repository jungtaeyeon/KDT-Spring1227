package com.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member231228 {
  private int mem_no; // private int mem_no=0; 차이점
  private String mem_id;
  private String mem_pw;
  private String mem_name;
  private String zipcode;
  private String address;
  private String mem_email;
  private String gubun;
  private String mem_picture;
  

@Builder
  public Member231228(int mem_no, String mem_id, String mem_pw, String mem_name, String zipcode, String address,
    String mem_email, String gubun, String mem_picture) {
    super();
    this.mem_no = mem_no;
    this.mem_id = mem_id;
    this.mem_pw = mem_pw;
    this.mem_name = mem_name;
    this.zipcode = zipcode;
    this.address = address;
    this.mem_email = mem_email;
    this.gubun = gubun;
    this.mem_picture = mem_picture;
  }

}
