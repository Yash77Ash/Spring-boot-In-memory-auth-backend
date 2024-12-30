package com.spring.security.inmemoryauthentication.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 100)
    private String lastName;

    @Column(nullable = false)
    @Size(max = 100)
    private String login;

    public @Size(max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 100) String password) {
        this.password = password;
    }

    public @Size(max = 100) String getLogin() {
        return login;
    }

    public void setLogin(@Size(max = 100) String login) {
        this.login = login;
    }

    public @Size(max = 100) String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(max = 100) String lastName) {
        this.lastName = lastName;
    }

    public @Size(max = 100) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Size(max = 100) String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @Size(max = 100)
    private String password;


}
