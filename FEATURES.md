# Overview

The app contains multiple features. These features are divided based on the entities and their relationship.

## User (Customer)

It's worth to note that the user entity represents customer and admin entities.

The customer can:

- Register/login via phone number, password, and name. The user will then receive an access and refresh tokens (JWTs) for upcoming requests.
- Add/update/delete/read addresses and has to set a single address as active to make a successful order.
- Browse the list of restaurants, select items needed and specify the quantity as well as the item specs (i.e toppings).
- Search for restaurants via their names.
- Filter restaurants based on each restaurant's cuisines.
- View the receipt of an order.
- Apply promotion code to get a discount.
- Rate the restaurant(s) and the courier who delivered the order from 1 to 5 as well as providing a feedback for both.

## User (Admin)

The admin can:

- Register/login via phone number, password, and name. The admin will then receive an access and refresh tokens (JWTs) for upcoming requests (admin related authority).
- Add/update/delete restaurants and insert the name and the address of each one.
- Add/update/delete a menu of a restaurant, sections to that menu, items to the sections as well as specs for each item.
- Add/update/delete regions which is later used in users, restaurants, and calculating delivery fees.
- Add/update/delete delivery fees between two regions (the customer exists in one of them and the restaurant in the other).
- Add/update/delete cuisines and update each restaurant's list of cuisines.
- Add/update/delete promotions to the system and defining the start and end dates. Furthermore, the admin can specify the usage count and specify the type of a promotion (explained later).
- Assign a specific courier to a specific order.

## Courier

The courier can:

- Register/login via phone number, password, and name. The courier will then receive an access and refresh tokens (JWTs) for upcoming requests (courier related authority).
- View the order(s), its items, and the specs.
- View the customer and the restaurant infos.
- Update the current status of an order ("Pending", "Preparing", "Dispatched", "Successful").
- The average rating, the number of successful orders, and earnings are periodically update via scheduled jobs since they're not crucial to be updated instantly (every hour).

## Restaurant

A restaurant got info including:

- Name
- Address(longitude, latitude, region)
- Set of menus related to the restaurant and their sections, items, and items specs.

## Spec

Spec defines the specs of an item and has two types:

- **Radio**: In which you can only select one spec and the price of it is added to the item
- **Checkbox**: In which you can select multiple specs of an item and the prices of each spec is add to the item.

## Delivery Fees

A delivery fee defines the cost of delivery between two regions.

## Order

An order represents, obviously, an order placed by a customer. It contains some info like:

- Restaurants.
- Delivery fees of each restaurant.
- Promotion code and its discount type (discussed later).
- The courier that handles the order (determined after an order is placed and an admin assigns the order to a courier).
- The rating of the courier and each restaurant as well as the feedbacks (which is possible to add only after the order is successfully delivered).
- The status of the order so the customer knows what's happening firsthand.
- The average rating and the number of successful orders are periodically update via scheduled jobs since they're not crucial to be updated instantly.

## Order Restaurants/Items/Specs/Delivery Fees

The order, restaurant, item, and specs can't be used to hold the order data so a separate tables are created for storing the order's info and the info of the restaurants in the order. This is intended as the changes that may happen to restaurants and their delivery fees. You may later decide to raise the price of the services but you want to keep the history of orders intact.

## Promotion

- A promotion can be associated to the whole order, delivery fees, or restaurants in the order.
- This means that there're 3 different promotion types:
  - **FIXED**: Which would be applied on the order total.
  - **DELIVERY**: Which is applied to the delivery fees. Note that you may associate a promotion to a restaurant so that the promotion would only be applied to a specific restaurant leaving other restaurants with no discounted delivery fees.
  - **PERCENTAGE**: Defines the discount percentage and can also be (or not) associated to a restaurant.
- A promotion has a max count of usages and start/end dates.
- A promotion has some info like code, discount type (explained above), discount value, description, headline for push notifications, and a flag to define whether the promotion is active or not.
- Note that each promotion usage is stored to know which user used the promotion and when did he/she used it.
- Be aware that no two promotions (with same codes) can be active at the same time. So code can be duplicated (i.e PRIMOS25 can be inserted to the system) but should not be active while the duplicate is also active (using a SQL trigger for that check).
