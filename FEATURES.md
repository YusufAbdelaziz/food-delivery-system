# Overview

The app contains multiple features, organized based on the entities and their relationships.

## User (Customer)

It’s important to note that the user entity encompasses both customer and admin roles.

The customer is able to:

- **Register/login** using their phone number, password, and name. Upon successful registration, they will receive access and refresh tokens (JWTs) for future requests.

- **Add, update, delete, and read addresses**, ensuring that one address is set as active to successfully place an order.

- **Browse the restaurant list**, select desired items, and specify quantities along with item specifications (e.g., toppings).

- **Search for restaurants** by their names.

- **Filter restaurants** according to their cuisine types.

- **View the receipt** for an order.

- **Apply a promotion code** for discounts.

- **Rate the restaurant(s)** and the courier who delivered the order on a scale from 1 to 5, along with providing feedback for both.

## User (Admin)

The admin has the ability to:

- **Register/login** using their phone number, password, and name. The admin will then receive access and refresh tokens (JWTs) for subsequent requests (admin-related authority).

- **Add, update, delete restaurants**, including the name and address of each restaurant.

- **Add, update, delete a restaurant's menu**, including sections, items, and specifications for each item.

- **Add, update, delete regions**, which are later utilized for users, restaurants, and calculating delivery fees.

- **Add, update, delete delivery fees** between two regions (with the customer in one region and the restaurant in another).

- **Add, update, delete cuisines** and modify each restaurant's list of cuisines.

- **Add, update, delete promotions** in the system, defining start and end dates. Additionally, the admin can specify the usage count and type of each promotion (explained later).

- **Assign a specific courier** to a particular order.

## Courier

The courier is able to:

- **Register/login** using their phone number, password, and name. The courier will then receive access and refresh tokens (JWTs) for future requests (courier-related authority).

- **View the order(s)**, including items and their specifications.

- **View customer and restaurant information**.

- **Update the current status** of an order ("Pending", "Preparing", "Dispatched", "Successful").

- The **average rating**, number of successful orders, and earnings are updated periodically through scheduled jobs, as they do not need instant updates (every hour).

## Restaurant

A restaurant contains information including:

- **Name**

- **Address** (longitude, latitude, region)

- A **set of menus** related to the restaurant, including sections, items, and item specifications.

## Spec

A spec defines the characteristics of an item and comes in two types:

- **Radio**: Only one spec can be selected, and its price is added to the item.

- **Checkbox**: Multiple specs can be selected, and the prices of each spec are added to the item.

## Delivery Fees

A delivery fee specifies the cost of delivery between two regions.

## Order

An order represents a customer's placed order, containing information such as:

- **Restaurants** involved.

- **Delivery fees** for each restaurant.

- **Promotion code** and its discount type (discussed later).

- The **courier** assigned to handle the order (determined after the order is placed and assigned by an admin).

- The **rating** of the courier and each restaurant, along with feedback (which can only be added after the order is successfully delivered).

- The **status** of the order, allowing the customer to stay informed about its progress.

- The **average rating** and the number of successful orders are updated periodically through scheduled jobs, as they do not require instant updates.

## Order Restaurants/Items/Specs/Delivery Fees

The order, restaurant, item, and specs cannot be utilized to store order data, so separate tables are created to hold the order's information and the details of the restaurants involved. This approach is intended to accommodate potential changes to restaurants and their delivery fees. For instance, if service prices are increased, the historical order data should remain intact.

## Promotion

- A promotion can be linked to the entire order, delivery fees, or specific restaurants within the order.

- This results in **three distinct promotion types**:

  - **FIXED**: Applied to the total order amount.

  - **DELIVERY**: Applied to delivery fees. Note that a promotion can be associated with a specific restaurant, allowing discounts only for that restaurant while others remain unaffected.

  - **PERCENTAGE**: Defines a discount percentage and may or may not be associated with a restaurant.

- A promotion has a maximum usage count and specified start/end dates.

- Promotion details include code, discount type (as explained above), discount value, description, push notification headline, and an active status flag.

- Each promotion usage is recorded to track which user utilized the promotion and when.

- It is important to note that no two promotions (with the same codes) can be active simultaneously. Therefore, while a code can be duplicated (e.g., PRIMOS25 can be added to the system), it should not be active while its duplicate is also active (this can be enforced using a SQL trigger).
