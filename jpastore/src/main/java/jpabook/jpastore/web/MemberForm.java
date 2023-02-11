package jpabook.jpastore.web;

import lombok.Getter;
import lombok.Setter;

/**
 * MemberForm
 */
@Getter
@Setter
public class MemberForm {
    private String name;
    private String city;
    private String street;
    private String zipcode;
}