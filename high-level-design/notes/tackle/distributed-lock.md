### When do you need a distributed lock ?
#### Ticket booking site where there is only one ticket and thousand of user are racing buying it  ?
Assume for now there is now there is payment needed whoever clicks first get ticket.
If everyone click on buy button, even though you might be using transactional database which can help you ensure only one person is able to secure the ticket. 
But we need to still ensure that how do we stop database thundering with thousand of request. So distributed lock is needed to protect the DB.