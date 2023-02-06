package com.gerquestions.gerquestions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private String email;

    private String name;

    private String password;

    private List<Long> questionIds;
}
