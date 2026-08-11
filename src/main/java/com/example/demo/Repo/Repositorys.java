package com.example.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;



@Repository
public interface Repositorys extends JpaRepository<Account, String>  {

	

	Account findByname(String name);

	Account findBypass(String pass);

	

	

	

	

}