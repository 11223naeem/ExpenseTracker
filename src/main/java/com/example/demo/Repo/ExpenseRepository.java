package com.example.demo.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Expense;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {

	    Optional<Expense> findByIdAndUsername(Long id, String username);

		List<Expense> findByUsername(String username);

}
