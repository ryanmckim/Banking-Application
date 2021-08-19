# Banking System

## Ryan Kim

My banking application allows its users to simulate a baking account, by being allowed to add and remove customers, and
being able to deposit, withdraw, and keep track of previous sessions by being able to save and load sessions.  

This application is designed for people who are interested in starting a bank, as the user can add multiple customers
with different names, phone numbers, and change the balances for each customer to simulate a real bank.

I wanted to create a project that could be used in our daily lives, as well as something that can be educational towards 
the users.

This application is able to:
- Store name and balance of a customer's account
- Deposit and withdraw money from a customer's account

#### User's Stories:
- As a user, I want to be able to create a new banking account.
- As a user, I want to be able to create multiple customers to my bank (Add multiple X's to a Y).
- As a user, I want to be able to add and remove customers in my bank system.
- As a user, I want to be able to deposit and withdraw money to accounts.
- As a user, I want to be able to have the option to save the bank session
  when I press the quit option.
- As a user, I want to be able to load the previously saved bank session.

#### Phase 4: Task 2

I chose the first options to modify both of my model classes to make it robust
and for at least one method to throw a checked exception.

- In my Bank class, the method addNewCustomer() throws a checked exception.
- In my Customer class, the constructor as well as the methods depositMoney() and withdrawMoney()
throws checked exceptions.

#### Phase 4: Task 3

I think my UML diagram is neat because it does no arrows overlap anywhere, but some arrows are pointing down 
while some point up,because of the way the fields relate between classes.

If I had more time to refactor my project, I would:

- Create more helper methods because some methods in both Graphics classes have close to the max number of allowed 
  lines because it does multiple things in that single method.  I think this will overall help with the readability to 
  anyone who reads the code.
  
- Change the implementation of either the BankingApplication class and/or the Graphics classes, as there are a lot of 
  redundant code due to the graphical stage requiring inputs and outputs similar to that of the user interface without 
  any graphical implementations."# Banking-Application" 
