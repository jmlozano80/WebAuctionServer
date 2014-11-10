WebAuctionServer
================

The web application is bicycles auction site that allows   the user to auction a bike or bid for the auctioned bicycles.
The application was developed using different technologies such as Java Server Pages, Servlets , database ( Derby database), Enterprise Java Beans (session and entity beans).
The application consist in a Web Dynamic Project (MiniEbayClient) and a EJB project (EJBMiniEbayServer). Once the  MiniEbayClient project is deployed the  EJBMiniEbayServer is also deployed because it was instructed in the deployment assembly configuration.

EJBMiniEbayServer
This project is formed by  Entities and Session Beans, it is connected to the Derby database by a pooling connection.

MiniEbayClient
This project is formed by JSPs, Servlets , CSS, JavaScript (countdown timer). The servlets are in charge to communicate with the session beans of the EJBMiniEbayServer project.
