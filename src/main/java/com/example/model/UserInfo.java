package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="users")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", length = 25)
	private Integer id;

	@Column(name="username",length=50,nullable=false)
	@JsonProperty(value = "username")
	private String userName;
	
	@Column(name="password",length=800,nullable=false)
	private String password;
	
	@Column(name="role",length=50)	
	private String role;
	
	@Column(name="enabled")	
	private short enabled;
	
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public short getEnabled() {
		return enabled;
	}
	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}
}
