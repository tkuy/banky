# 💰 **Bank Account** 💰

### Presentation

This is a kata showing how I work. 

There are 4 features to implement. I've explained my choices, how I worked and prioritisation below the kata rules.

## Implementation details

### Feature 1: the bank account

We want to offer a bank account feature.

This account must have:

- A unique account number (free format)
- A balance
- A money deposit feature
- A money withdrawal feature

The following business rule must be implemented:

- A withdrawal cannot be made if it exceeds the amount of money in the account

__

### Feature 2: Overdraft

We want to offer an authorised overdraft facility on bank accounts.

The following business rule must be implemented:

- If an account has an authorised overdraft, then a withdrawal exceeding the account balance is permitted
  if the final balance does not exceed the authorised overdraft amount.

__

### Feature 3: savings accounts

We want to offer savings accounts.

A savings account is a bank account that:

- Has a deposit limit: money can only be deposited into this account up to the account limit (e.g. £22,950 on a Livret A savings account)
- Cannot have an overdraft facility

__

### Feature 4: Account statement

We would like to offer a monthly statement feature (covering a rolling month) of account transactions.

This statement should show:

- The type of account (savings account or current account)
- The account balance on the date the statement is issued
- A list of transactions that have taken place on the account, sorted by date in reverse chronological order

______

### How I proceeded

I did the project using an hexagonal architecture. I really focused on the features using TDD and KISS principles. I also used some design pattern that are adapted to DDD or unrelated.
For time management reason, I decided to sacrifice the controller. You can see the routes I would have done below. But I did an example of how I would have worked on.

**What I covered:**
- Domain (Entity, Aggregate root and service)
- Persistence
- A part of the controller: I’m not using Spring and because it’s a lot of redundancy, it’s only calling the routes, convert DTO to Domain entity then call the service, this is done once for create bank account feature as an example.

**What were the steps:**
- Domain:
    - Did all the features with TDD, KISS principles
    - BankAccount entity handling its own intelligence (Aggregate root - DDD typical Design pattern)
    - Completed 4 features in domain only (focus on business rules and not infrastructure)
- Hexagonal architecture and infrastructure :
    - Created services with a few features only
    - Created persistence layer using port adapter design pattern
    - Created controller with only one feature (creation)
- Refocus on the domain
    - I refocused on the service using TDD, implementing all the features and reaching quality expectations
    - I leave the controller with one feature (creation)


**Things I would have done differently:**
- For the hexagonal architecture initialisation, I would have started with the controller to get a more business/feature oriented approach and complete feature from end to end -> It would have been a good guideline to not over-engineer (see the point below)
- FinancialTransactionRepository - See the comments in the class. I was answering to a feature that was beyond the expressed needs, which is keeping track of the activity of an account. So I wouldn’t need to access to the transactions only in my business scope. Breaking the YAGNI principle.


**Limitations:**
- I wanted to use Spring boot but after I initialised it, I realised my IDE is not an upgraded version and I didn’t want to code in these conditions
  What I did because I didn’t use Spring :
- without the Spring IoC, I implemented an application context to create what would be equals to the beans
- I didn’t test directly the routes using http calls, I just called the methods
- I didn’t handle the unchecked exception and translated them into http codes. With spring, you can use an error handler to translate the errors into http request.

**What would be the next things I would have done that I intentionally ignored:**
- Business Errors
- Validations (mostly DTOs)
- Refactoring to use the same code to deposit and withdraw, this may lead to more complicated codes and handling behaviours differently. I don’t think that was a priority atm.
- Test the persistence layer but I think it’s not very relevant at the moment

**Questioning:**
- The dtos (used in the controller) might have to be in the domain as it has business intelligence. But it would have been also difficult to avoid annotation which would break the domain isolation (exempt of librairies/technologies). I think this is just a detail.

**Routes structure if I did all the features in the controller:**

**POST /accounts**

201: UUID


**POST /accounts/:id/operations/**

// See if operations fits the business language

// See if it should be a PATCH instead of POST

200

```
{
	date: Optional<LocalDateTime>,
	type: 'deposit' | 'withdrawal',
	amount: Integer
}
```

accounts not found => 404 not found

unsupported operation => 403 Forbidden


**GET /accounts/:id**

200

```
{
	UUID id,
	long allowedOverdraft, 
	long maxAmount, 
	long balance, 
	BankAccountType bankAccountType
}
```

Account not found => 404 Not found

**GET /accounts/operations**

200

```
[
	{
		UUID id, 
		LocalDateTime localDateTime, 
		Long amount, 
		Long balance, 
		TransactionTypeEnum transactionType
	},
	…
]
```

// For ATM tickets

**GET /accounts/statement**

200
String | Empty string | Errors





