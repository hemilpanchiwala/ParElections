# ParElections
                                         Parliamentary Elections App

This app mainly helps the users for getting different information about the Elections. 

In this app, firstly I have added the SignUp Section from where user can SignUp through his unique Aadhar Id and has to provide username and other relevant information with his/her photograph. On submitting the information he/she will be told that he/she will be able to login once verified by electoral officer. 

Now I have added Electoral Officer button in app from which he/she can login to electoral officer’s main page by submitting the unique ID given to him from the government office (here its “963418363284”). And he/she can signUp easily by just clicking signUp by adding their email and password. Once logged in, he/she would be able to see the signUp requests  with their information on main page. Here he can click on Approve button to update the database and then user can login to the app while if he finds information suspicious then he can click remove button to remove the user’s information from the database. On this page, officer can find buttons for setting election dates, election results, any updates and even declare Results. 

Now comes the login page where user can login with his aadhar as password and username given by him/her at signUp while electoral officer can sign in by providing his/her password. On signing in, the Updates page will show the updates provided by  the officer and a logout options for logging out of the app. While on results page, user can see the Election Dates, can find his/her polling booth stations and whenever results are declared, he/she can see it on clicking Results option. If the election dates are declared by officer then on signing in User will be first notified that election dates have been declared and polling booth can be seen in Result & Polling Information page. On signing in, user will be welcomed to the app by a notification in phones. And on the day of elections, the notification will be sent by the firebase to all the users for going to vote with a message on importance of voting. 


Main Page

<img src="https://i.paste.pics/a3208f0d6a92aaa7dd4f2c757833efe0.png" width="220" height="464" alt="Screenshot">

Electoral Officer's Main Page

<img src="https://i.paste.pics/b1e02eea2f643dea7c2302e31a101b25.png" width="220" height="464" alt="Screenshot">

Results & Polling Information Page

<img src="https://i.paste.pics/af35cd2790e82e35beb98ac1708bff03.png" width="220" height="464" alt="Screenshot">

Updates Page

<img src="https://i.paste.pics/4I6RO.png" width="220" height="464" alt="Screenshot">

TO-DO List:

    • Have added a thread in my Updates fragment for setting notification automatically on day of elections but app is crashing when I logout so will fix that problem and so now I am using firebase cloud messaging to send message to all users.

