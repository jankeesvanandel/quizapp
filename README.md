# Quizapp

1.  Configure the questions (where? how?)
2.  Launch the application/webserver
3.  Navigate to the main page
4.  Clients scan the QR code
5.  

# Running

mkdir -p ~/mongodb/quizapp
mongod --dbpath ~/mongodb/quizapp --smallfiles

mvn jetty:run
