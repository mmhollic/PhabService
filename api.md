**All entity classes exist in the source code**

**Bank API**

<https://phabservice-129311a14694.herokuapp.com/account/createaccount>

Type: POST

Description: creates a bank account, given the user’s name

Request body:-

{
    "name": "New account holder's name"
}

On success:-
Response status 200
Response body if– an object of class accountpackage.Account – eg:-

{
    "id" : 1,
    "name" : "Account holder's name",
    "accountNumber" : 1472,
    "balance" : 1200
}
On fail:- Response 500

<https://phabservice-129311a14694.herokuapp.com/account/deposit>

Type: POST
Description: deposits an amount in an account, given the accountNumber
Request body – an object of class accountpackage.Deposit eg:-

{
    "accountNumber" : 1472,
    "amount" : 1200
}

On success: Response status 200
On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/account/withdraw>

Type: POST
Description: withdraws an amount from an account, given the accountNumber
Request body – an object of class accountpackage.Withdrawal eg:-

{
    "accountNumber" : 1472,
    "amount" : 1200
}

On success: Response status 200
On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/account/transfer>

Type: POST
Description: transfers an amount from one account to another, given the accountNumbers
Request body – an object of class accountpackage.Transfer eg:-

{
    "fromAccount" : 1472,
    "toAccount" : 1473,
    "amount" : 1200
}

On success: Response status 200
On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/account/balance>

Type: GET
Description: get details of all bank accounts in the system
Response body – an object of class List\< accountpackage.Account\>
On success: Response status 200
On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/account/balance/1122>

Type: GET
Description: get details of bank accounts 1122
Response body – an object of class List\< accountpackage.Account\>
On success: Response status 200
On fail:- Response status 404

<https://phabservice-129311a14694.herokuapp.com/account/createtables>

Type: GET
Description: CAUTION: creates all necessary database tables for accounts and wholesalers if they don’t exist. You only need to call this if you are setting up a new installation
On success: Response status 200
On fail:- Response status 500

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
{
    "customerId": 1472, // This is customer’s bank accountNumber
    "orderDetails": [ // This is a List of OrderDetail objects
    {
        "productId": 3, // Product id from the Products list
        "quantity": 1
    },
    {
        "productId": 4,
        "quantity": 7
    }
    Etc …
    ]
}

On success: Response status 200.
Response body is a fully detailed object of class wholesalerpackage.Order with all fields filled in, giving you the orderId eg:-
{
"orderId": 3,
"customerId": 1472,
"orderTime": 1702916805703, // This is a TimeStamp object value
"status": "PENDING",

"totalAmount": 17.30, // How much was charged to customer’s account

"orderDetails": [

{

"orderDetailId": 1, // For internal tracking

"orderId": 3, // Same as the orderId above

"productId": 3,

"quantity": 1

},

{

"orderDetailId": 2,

"orderId": 3,

"productId": 4,

"quantity": 7

}

]

}

On fail:- Response status 500

<https://phabservice-129311a14694.herokuapp.com/wholesaler/orders/3>

Type: GET

Description: Gets the details of order 3

On success: Response status 200.

Response body is a fully detailed object of class wholesalerpackage.Order with all fields filled in, giving you the orderId as above

Note: calls to this api method do not check whether delivery has been made - order status is **not** updated by calling this api call

On fail: Response status 404 or 500.

<https://phabservice-129311a14694.herokuapp.com/wholesaler/delivery/3>

Type: GET

Description: Gets the details of order 3, updating the delivery status if necessary

On success: Response status 200.

Response body is a fully detailed object of class wholesalerpackage.Order with all fields filled in, giving you the orderId as above

This api call checks to see if delivery has been made and updates the order status accordingly. Delivery normally takes 10 minutes from order being placed.

On fail: Response status 404 or 500.
