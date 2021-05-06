# Stocks_management
A project that helps to manage stocks of an electronics and appliances store.

# Functionalities:
- general:
    - read/write data from/in .csv files;
    - generate random products;
    - add/remove product in/from stock;
    - add a category;
    - add a distributor;
    - add transactions;
    - show categories/distributors/stock;
    - show description for a product(name, specifications, stock, ...);
    - apply promotion for products(e.g.: for laptops, a mouse is offered for free);
    - show promotional products;
    - filter products by price(<, =, >, between minBound and maxBound).
    - show information about a distributor;
    - apply a price increase/decrease of p% for a category/product;
    - find products by name;
    - find categories/distributors by name or id;
   

- for categories:
    - show products from a category, sorted by a given criteria: price, name, distributor;


- for transactions:
    - add products in transaction;
    - remove products from transaction;
    - close transactions;
    - show transactions;
    - show total income;

# Object Types:
- PRODUCT(abstract class);
- LAPTOP, MOBILE_PHONE, FRIDGE, GAS_COOKER, AUDIO_SYSTEM, AUDIO_SPEAKER, TV, MOUSE, HEADPHONES, SMARTWATCH, POWER_BANK;
- CATEGORY;
- DISTRIBUTOR;
- TRANSACTION;
- VALIDATOR.
