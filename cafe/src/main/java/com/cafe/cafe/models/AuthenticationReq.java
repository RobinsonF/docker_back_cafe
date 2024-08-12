package com.cafe.cafe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

}
