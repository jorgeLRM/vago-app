package com.dosvales.vagoapp.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
    private String name;
	
	@NotNull
    @Column(unique = true)
    @Pattern(regexp = "^([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)$", message = "Formato de correo electrónico no válido, vuelva a comprobarlo")
    private String email;
	
	@NotNull
    @Size(min = 8, message = "La contraseña debe tener por lo menos {min} caracteres de longitud")
	private String password;
	
	private LocalDate birthDate;

	@NotNull
    private LocalDate hireDate;

    @NotNull
    private boolean ready;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo avatar;
    
    public User() {
    }

    public User(String name, String email, String password, LocalDate birthDate, LocalDate hireDate, boolean ready, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.ready = ready;
        this.role = role;
    }

    public User(String name, String email, String password, LocalDate birthDate, LocalDate hireDate, boolean ready, Role role, Photo avatar) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.ready = ready;
        this.role = role;
        this.avatar = avatar;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setAvatar(Photo avatar) {
        this.avatar = avatar;
    }
}
