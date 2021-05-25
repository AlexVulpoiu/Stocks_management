# Stocks_management
A project that helps to manage stocks of an electronics and appliances store.

# Functionalities:
- general:
    - read/write data from/in .csv files;
    - generate random products;
    - add/remove product in/from stock;
    - add a category;
    - add a distributor;
    - add a transaction;
    - show categories/distributors/stock/transactions;
    - show promotional products;
    - filter products by price(<, =, >, between minBound and maxBound).
    - apply a price increase/decrease of p% for a category/product;
    - find products by name;
    - find distributor by id;
    - find categories by name or id;
    - show total income;


- for categories:
  - show products from a category, sorted by a given criteria: price, name, distributor;
   
  
- for distributors:
    - show information about a distributor;
  

- for products:
  - show description for a product(name, specifications, stock, ...);
  - apply/delete promotion for a product(e.g.: for laptops, a mouse is offered for free);


- for transactions:
    - add products in transaction;
    - remove products from transaction;
    - close transactions;
    - show transactions.

# Object Types:
- Product(abstract class);
- AudioSpeaker, AudioSystem, Fridge, GasCooker, Headphones, Laptop, MobilePhone, Mouse, PowerBank, Smartwatch, TV;
- Category;
- Distributor;
- Transaction;
- Validator.
