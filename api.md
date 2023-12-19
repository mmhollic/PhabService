**All entity classes exist in the source code**

**Bank API**

<https://phabservice-129311a14694.herokuapp.com/account/createaccount>

Type: POST<br>
Description: creates a bank account, given the user’s name<br>
Request body:-<br>
{
    "name": "New account holder's name"
}<br>
On success:-<br>
Response status 200<br>
Response body if– an object of class accountpackage.Account – eg:-<br>
{
    "id" : 1,
    "name" : "Account holder's name",
    "accountNumber" : 1472,
    "balance" : 1200
}<br>
On fail:- Response 500

<https://phabservice-129311a14694.herokuapp.com/account/deposit>

Type: POST<br>
Description: deposits an amount in an account, given the accountNumber<br>
Request body – an object of class accountpackage.Deposit eg:-<br>
{
    "accountNumber" : 1472,
    "amount" : 1200
}<br>

On success: Response status 200<br>
On fail:- Response status 500<br>

<https://phabservice-129311a14694.herokuapp.com/account/withdraw>

Type: POST<br>
Description: withdraws an amount from an account, given the accountNumber<br>
Request body – an object of class accountpackage.Withdrawal eg:-<br>
{
    "accountNumber" : 1472,
    "amount" : 1200
}<br>

On success: Response status 200<br>
On fail:- Response status 500<br>

<https://phabservice-129311a14694.herokuapp.com/account/transfer>

Type: POST<br>
Description: transfers an amount from one account to another, given the accountNumbers<br>
Request body – an object of class accountpackage.Transfer eg:-<br>

{
    "fromAccount" : 1472,
    "toAccount" : 1473,
    "amount" : 1200
}<br>

On success: Response status 200<br>
On fail:- Response status 500<br>

<https://phabservice-129311a14694.herokuapp.com/account/balance>

Type: GET<br>
Description: get details of all bank accounts in the system<br>
Response body – an object of class List\< accountpackage.Account\><br>
On success: Response status 200<br>
On fail:- Response status 500<br>

<https://phabservice-129311a14694.herokuapp.com/account/balance/1122>

Type: GET<br>
Description: get details of bank accounts 1122<br>
Response body – an object of class List\< accountpackage.Account\><br>
On success: Response status 200<br>
On fail:- Response status 404<br>

<https://phabservice-129311a14694.herokuapp.com/account/createtables>

Type: GET<br>
Description: CAUTION: creates all necessary database tables for accounts and wholesalers if they don’t exist. You only need to call this if you are setting up a new installation<br>
On success: Response status 200<br>
On fail:- Response status 500<br>

**WHOLESALER API**

**Note: The wholesaler’s bank accountNumber is 1471**

**Account name: MedsRUs**

<https://phabservice-129311a14694.herokuapp.com/wholesaler/products>

Type: GET
Description: Gets a list of all products that exist in the system
On success: Response status 200.
Response body is an object of class List\<wholesalerpackage.Product\>
On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/wholesaler/order>

Type: POST
Description: Places an order in the system, transferring funds and placing items on delivery
Request body is an object of class wholesalerpackage.Order, but only the following fields are necessary:-

{<br>
&emsp;"customerId": 1472, &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;// This is customer’s bank accountNumber<br>
&emsp;"orderDetails": <br>
&emsp;&emsp;[ &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;// This is a List of OrderDetail objects<br>
&emsp;&emsp;&emsp;{<br>
&emsp;&emsp;&emsp;&emsp;"productId": 3, &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;// Product id from the Products list<br>
&emsp;&emsp;&emsp;&emsp;"quantity": 1<br>
&emsp;&emsp;&emsp;},<br>
&emsp;&emsp;&emsp;{<br>
&emsp;&emsp;&emsp;&emsp;"productId": 4,<br>
&emsp;&emsp;&emsp;&emsp;"quantity": 7<br>
&emsp;&emsp;&emsp;}<br>
&emsp;&emsp;&emsp;Etc …<br>
&emsp;&emsp;]<br>
}<br>

On success: Response status 200.
Response body is a fully detailed object of class wholesalerpackage.Order with all fields filled in, giving you the orderId eg:-<br>
{<br>
&emsp;"orderId": 3,<br>
&emsp;"customerId": 1472,<br>
&emsp;"orderTime": 1702916805703, &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;// This is a TimeStamp object value<br>
&emsp;"status": "PENDING",<br>
&emsp;"totalAmount": 17.30, &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;// How much was charged to customer’s account<br>
&emsp;"orderDetails": [<br>
&emsp;&emsp;&emsp;{<br>
&emsp;&emsp;&emsp;"orderDetailId": 1, &emsp;&emsp;&emsp;// For internal tracking<br>
&emsp;&emsp;&emsp;"orderId": 3, &emsp;&emsp;&emsp;// Same as the orderId above<br>
&emsp;&emsp;&emsp;"productId": 3,<br>
&emsp;&emsp;&emsp;"quantity": 1<br>
&emsp;&emsp;},<br>
&emsp;&emsp;{<br>
&emsp;&emsp;&emsp;"orderDetailId": 2,<br>
&emsp;&emsp;&emsp;"orderId": 3,<br>
&emsp;&emsp;&emsp;"productId": 4,<br>
&emsp;&emsp;&emsp;"quantity": 7<br>
&emsp;&emsp;}<br>
&emsp;]<br>
}<br>

On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/wholesaler/orders/3>

Type: GET<br>
Description: Gets the details of order 3<br>
On success: Response status 200.<br>
Response body is a fully detailed object of class wholesalerpackage.Order with all fields filled in, giving you the orderId as above
Note: calls to this api method do not check whether delivery has been made - order status is **not** updated by calling this api call<br>
On fail: Response status 404 or 500.

<https://phabservice-129311a14694.herokuapp.com/wholesaler/delivery/3>

Type: GET<br>
Description: Gets the details of order 3, updating the delivery status if necessary<br>
On success: Response status 200.<br>
Response body is text that either says 'PENDING' or 'DELIVERED'<br>
This api call checks to see if delivery has been made and updates the order status accordingly. Delivery normally takes 10 minutes from order being placed.<br>
On fail: Response status 404 or 500.

If you are building for gretty rather than heroku, please change the switch in DAO.java 
