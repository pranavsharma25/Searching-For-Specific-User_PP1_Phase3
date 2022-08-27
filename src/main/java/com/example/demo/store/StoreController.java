package com.example.demo.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

    @Autowired
    UserEntityCrudRepository userEntityCrudRepository;


    @GetMapping(path = "/createUser", produces = "text/html")
    String showRegistrationForm() {
        String output = "<h2>User Registration</h2> <form action='' method='POST'>";
        output += "Username: <input name='username' type='text' /><br />";
        output += "Password: <input name='password' type='password' /><br />";
        output += "<input type='submit' />";
        output += "</form>";
        return output;
    }
    
    @PostMapping(path = "/createUser")
    String createUser(@ModelAttribute LoginEntity user) {
    	
        if (user == null || user.getUsername() == null) {
            throw new RuntimeException("Username required");
        }
        if (user == null || user.getPassword() == null) {
            throw new RuntimeException("Password required");
        }
        
        userEntityCrudRepository.save(user);
        
        return home();
    }
    
    @GetMapping(path = "/login", produces = "text/html")
    String showLoginForm() {
        String output = "<h2>User Login</h2> <form action='' method='POST'>";
        output += "Username: <input name='username' type='text' /><br />";
        output += "Password: <input name='password' type='password' /><br />";
        output += "<input type='submit' />";
        output += "</form>";
        return output;
    }
    
    @PostMapping(path = "/login")
    String validateUser(@ModelAttribute LoginEntity user) {
    	
    	String result = "Login Unsuccessful";
    	
    	Iterable<LoginEntity> users = userEntityCrudRepository.findAll();
    	
    	for(LoginEntity loginEntity : users) {
    		if(loginEntity.getUsername().equals(user.getUsername()) && loginEntity.getPassword().equals(user.getPassword())){
    			result = "Login Successful";
    		}
    	}
    	   
        return result;
        
    }
    
    @GetMapping(path = "/update", produces = "text/html")
    String showUpdateForm() {
        String output = "<h2>User Update</h2> <form action='' method='POST'>";
        output += "User ID: <input name='id' type='text' /><br />";
        output += "<input type='submit' />";
        output += "</form>";
        return output;
    }
    
    @PostMapping(path = "/update")
    String validateId(@ModelAttribute LoginEntity user) {
    	
    	String result = "Id does not exist";
    	
    	Iterable<LoginEntity> users = userEntityCrudRepository.findAll();
    	
    	for(LoginEntity loginEntity : users) {
    		if(loginEntity.getId().equals(user.getId())){
    			result = "Id exist";
    		}
    	}
    	   
        return result;
        
    }
    
    @GetMapping(path = "/updateUser", produces = "text/html")
    String showUpdateUserForm() {
        String output = "<h2>Enter Updated Info</h2> <form action='' method='POST'>";
        output += "User ID: <input name='id' type='text' /><br />";
        output += "Username: <input name='username' type='text' /><br />";
        output += "Password: <input name='password' type='password' /><br />";
        output += "<input type='submit' />";
        output += "</form>";
        return output;
    }
    
    @PostMapping(path = "/updateUser")
    String updatingUser(@ModelAttribute LoginEntity user) {
    	
    	//String result = "Id does not exist";
    	
    	Iterable<LoginEntity> users = userEntityCrudRepository.findAll();
    	
    	for(LoginEntity loginEntity : users) {
    		if(loginEntity.getId().equals(user.getId())){
    			loginEntity.setUsername(user.getUsername());
    			loginEntity.setPassword(user.getPassword());
    			userEntityCrudRepository.save(user);
    			
    		}
    	}
    	   
        return home();
        
    }
    

    @GetMapping(path = "/home")
    String home() {

        Iterable<LoginEntity> users = userEntityCrudRepository.findAll();

        String myUsers = "<h2>Our Users</h2>";
        for (LoginEntity p: users) {
            myUsers = myUsers + "<p>User: " + p.getUsername() + " | Password: " + p.getPassword() + "</p>";
        }
        return myUsers;
    }

}
