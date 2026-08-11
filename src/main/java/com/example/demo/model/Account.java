package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity

public class Account {
	


	@Id
    private String name;
    private String email;
    private String pass;
    private String Cpass;
    
    public Account() {
		super();
	}
    
   

	public Account(String name, String email, String pass, String cpass) {
		super();
		this.name = name;
		this.email = email;
		this.pass = pass;
		Cpass = cpass;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCpass() {
		return Cpass;
	}

	public void setCpass(String cpass) {
		Cpass = cpass;
	}
    
    
    
    
	



	
    

    

  
   
}

