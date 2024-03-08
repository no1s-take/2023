package com.example.banquet.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail extends AbstractEntity {

    @NotEmpty(message = "件名は必須項目です")
    private String subject;

    @NotEmpty(message = "本文は必須項目です")
    private String text;
}
