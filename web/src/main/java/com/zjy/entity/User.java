package com.zjy.entity;

import org.hibernate.validator.constraints.Length;

public class User {
	@Length(min=5, max=20,message="长度大于56")
	private String name;
	private int age;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
