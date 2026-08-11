package com.example.demo.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Repo.ExpenseRepository;
import com.example.demo.Repo.Repositorys;
import com.example.demo.model.Account;
import com.example.demo.model.Expense;

import jakarta.servlet.http.HttpSession;

@Controller
public class TrackerController {
	
	@Autowired
	Repositorys repo;
	
	@Autowired
    ExpenseRepository expenseRepo;

	
	@RequestMapping("/")
	public String home() {
		return"login";
	}
	
	@RequestMapping("/createAccount")
	public String CreateAccount() {
		return "createAccount";
		
	}
	
	@RequestMapping("/loginAccount")
	public String loginAccount() {
		return "login";
		
	}
	
	@RequestMapping("/CreateAccounts")
	public String account(@RequestParam String name,
            @RequestParam String Email,
            @RequestParam String pass,
            @RequestParam String Cpass,
            Model model) {

// Password length validation
if (pass.length() < 6 || pass.length() > 20) {
model.addAttribute("error", "Password must be between 6 and 20 characters");
return "createAccount";
}

// Password match validation
if (!pass.equals(Cpass)) {
model.addAttribute("error", "Passwords do not match");
return "createAccount";
}

Account acc = new Account(name, Email, pass, Cpass);
repo.save(acc);

return "login";
}
	
	@RequestMapping("/loginAccounts")
	public String LoginPages(@RequestParam String name,@RequestParam String pass, Model model,HttpSession session) {
		Account acc =repo.findByname(name);
		
		if (acc == null  ) {
            model.addAttribute("title", "Error");
            model.addAttribute("message", "Account Not Found");
            
            return "result";
        }
		if(!acc.getPass().equals(pass)) {
			 model.addAttribute("title", "Error");
	            model.addAttribute("message", "Account Not Found");
	            
	            return "result";
		}
    	else {
    		
    		session.setAttribute("user", name);
    		return "redirect:/dashboard"; 
	}
	}
	
	 @GetMapping("/dashboard")
	    public String dashboard(
	            HttpSession session,
	            Model model) {

	        String username =
	                (String) session.getAttribute("user");


	        // User not logged in
	        if (username == null) {
	            return "redirect:/loginAccount";
	        }

	        model.addAttribute("username",username);

	        return "dashboard";
	    }
	
	 
	  @GetMapping("/addExpense")
	    public String addExpensePage(
	            HttpSession session) {

	        String username =
	                (String) session.getAttribute("user");


	        if (username == null) {
	            return "redirect:/loginAccount";
	        }


	        return "addExpense";
	    }

	    // SAVE EXPENSE

	    @PostMapping("/saveExpense")
	    public String saveExpense(
	            @RequestParam String expenseName,
	            @RequestParam BigDecimal amount,
	            @RequestParam LocalDate date,
	            @RequestParam String description,
	            HttpSession session) {


	        String username =(String) session.getAttribute("user");


	        // User not logged in
	        if (username == null) {
	            return "redirect:/loginAccount";
	        }


	        // Create expense
	        Expense expense = new Expense();

	        expense.setExpenseName(expenseName);
	        expense.setAmount(amount);
	        expense.setDate(date);
	        expense.setDescription(description);

	        // Get username from session
	        expense.setUsername(username);


	        // Save to database
	        expenseRepo.save(expense);


	        // After saving, go to expense list
	        return "redirect:/expenseList";
	    }

	    // EXPENSE LIST
	    @GetMapping("/expenseList")
	    public String expenseList(
	            HttpSession session,
	            Model model) {

	        String username =
	                (String) session.getAttribute("user");


	        if (username == null) {
	            return "redirect:/loginAccount";
	        }


	        // Get ONLY current user's expenses
	        List<Expense> expenses =
	                expenseRepo.findByUsername(username);


	        model.addAttribute( "expenses", expenses);
	        model.addAttribute( "username", username );
	        return "expenseList";
	    }

	    // UPDATE EXPENSE PAGE
	    @GetMapping("/updateExpense")
	    public String updateExpensePage(
	            @RequestParam Long id,
	            HttpSession session,Model model) {


	        String username = (String) session.getAttribute("user");


	        if (username == null) {
	            return "redirect:/loginAccount";
	        }

	        Expense expense = expenseRepo .findByIdAndUsername(id, username) .orElse(null);


	        if (expense == null) {

	            model.addAttribute( "title", "Error");

	            model.addAttribute("message","Expense not found");
	            return "result";
	        }


	        model.addAttribute("expense",expense );


	        return "updateExpense";
	    }

	    // UPDATE EXPENSE IN DATABASE
	    
	    @PostMapping("/updateExpense")
	    public String updateExpense(
	            @RequestParam Long id,
	            @RequestParam String expenseName,
	            @RequestParam BigDecimal amount,
	            @RequestParam LocalDate date,
	            @RequestParam String description,
	            HttpSession session,
	            Model model) {


	        String username =(String) session.getAttribute("user");


	        if (username == null) {
	            return "redirect:/loginAccount";
	        }


	        // Find user's expense
	        Expense expense =expenseRepo.findByIdAndUsername(id, username).orElse(null);


	        if (expense == null) {

	            model.addAttribute("title","Error");

	            model.addAttribute( "message", "Expense not found");

	            return "result";
	        }


	        // Update values
	        expense.setExpenseName(expenseName);

	        expense.setAmount(amount);

	        expense.setDate(date);

	        expense.setDescription(description);


	        // Save updated expense
	        expenseRepo.save(expense);


	        return "redirect:/expenseList";
	    }
	    
	    
	    @GetMapping("/logout")
	    public String logout(HttpSession session) {

	        session.invalidate();

	        return "redirect:/loginAccount";
	    }

	

}
