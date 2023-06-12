# Book selling website using Spring Boot

## Register feature
Website has Register/Login feature. The user information will be stored: Name, Email, Password

## Admin feature
* There is a table display all books in database. The columns of table are: Title, author, category, publish date, number of pages, sold amount and a blank column at end
* When admin isn't login, admin only can be seen the list of book, after login, at the end of each row has 2 button: View and Delete, and a button Add Book above the table
* When click view, all the fields will display the information retreive from database but can't modify. When click Edit button, these field will be allowed for editing and the Edit button will change to Save button
* When click Add Book, all of field are blank and only display Add button

## User feature
* User after login can see list of books, the information will displayed are: Cover image, book title and author name
* User can click on each book to see the detail of each book, user can order by a specific amount
* User can rating and giving comment for each book
* User can see what books they have ordered, and cancel order

## Validate requirement
* All of required field must not be empty, the database can not contain two books with same title AND author, and two user can not have the same username
* The error must be display alert for user
* Before execute the important task like Delete or Create. An alert popup must be display for asking confirmation (Confirm or Cancel)