package com.example.springessentials.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Date;

@Document(collection = "contact")
@Data
@NoArgsConstructor
public class Contact {

    @Id
    private String id;

    @NotEmpty
    private String name;

    @Email
    private String email;

    @CPF
    @Length(max = 11, min = 11)
    private String cpf;

    @Past
    private Date birthDay;

    public Contact(@NotEmpty String name, @Email String email, @CPF @Length(max = 11, min = 11) String cpf, @Past Date birthDay) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDay = birthDay;
    }
}
