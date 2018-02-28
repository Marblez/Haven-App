var firebase = require("firebase/app");
var auth = require("firebase/auth");
var data = require("firebase/database");
var arr = [];
var count = 0;
var keys = [];
var len;
var https = require('https');
var userID = "1365841946830211";
var request = require("request");
var accountSid = 'ACf6e2c57a49f3a5266e44e4e292c94b5a';
var authToken = '069cf667c68c5e3624ba7f6a1ee68564';
var phoneNumber = '+12173051574';
var twilio = require('twilio');
var client = new twilio(accountSid, authToken);
var twilioNumber = '+12176567597';
var specialentry = [];





// Initialize Firebase
var config = {
    apiKey: "AIzaSyAp76aPtfX9-EBPGnGCEP89ngewoA98tDY",
    authDomain: "health-haven.firebaseapp.com",
    databaseURL: "https://health-haven.firebaseio.com",
    projectId: "health-haven",
    storageBucket: "health-haven.appspot.com",
    messagingSenderId: "92510334527"
  };
  firebase.initializeApp(config);

  var database = firebase.database();

setInterval(function(){
	specialentry = [];
var leadsRef = database.ref("Users/1365841946830211/Journals");
console.log("\n");

console.log("Displaying Entries:");
leadsRef.on('value', function(snapshot) {
    snapshot.forEach(function(childSnapshot) {
      var childData = childSnapshot.val();
      arr[count] = childData;
      count++;
      console.log(childData);
    });
});

function checkSpecialEntries(){
	var leadsRef = database.ref("Users/1365841946830211/Special Journals");
	leadsRef.on('value', function(snapshot) {
	    snapshot.forEach(function(childSnapshot) {
	      var childData = childSnapshot.val();
	      if(childData.length!=0){
	      	notifyTherapist();
	      }
	    });
	});
}

var time = setTimeout(function(){
var leadsRef = database.ref("Users/1365841946830211/Special Journals");
	console.log("\n");
	console.log("Special Entries Check");
	leadsRef.on('value', function(snapshot) {
	    snapshot.forEach(function(childSnapshot) {
	      var childData = childSnapshot.val();
	      //console.log(childData);
	      specialentry[specialentry.length] = childData;
	      
	    });
	});

		var checkEntry = setTimeout(function(){
		if(specialentry.length!=0){
	      	notifyTherapist();
	      }
	      else{
	      	console.log("No New Special Entries");
	      }
		},1000);
		
}, 2000);

function notifyTherapist(){
	console.log("\n");
	sendMessage();
	var leadsRef = database.ref("Users/1365841946830211/Contacts");
	leadsRef.on('value', function(snapshot) {
	    snapshot.forEach(function(childSnapshot) {
	      var childData = childSnapshot.val();
	  	  phoneNumber = childData.Phone;
	  	  
	      //console.log(phoneNumber);
		    /*
		    client.messages.create({
		        body: message,
		        to: phoneNumber[i],  // Text this number
		        from: twilioNumber // From a valid Twilio number
		    })
		    .then((message) => console.log(message.sid));
			*/
			
			


	    });
	});

}
function sendMessage(){

var time = setTimeout(function(){
			console.log(specialentry);
			console.log("\n");
			console.log("Special Entries exist: Sending Message to Therapist");
			message = "Special Entries for client "+userID;
			for(i = 0; i<specialentry.length;i++){
				message = "Client may have signs of depression";
			}
 			client.messages.create({
		        body: message,
		        to: phoneNumber,  // Text this number
		        from: twilioNumber // From a valid Twilio number
		    })
		    .then((message) => console.log(message.sid));
		    clear();

}, 2000);
}

function clear(){
	var leadsRef = database.ref("Users/1365841946830211/Special Journals").remove()
}

function sentimentAnalysis(status){
	//Switch

	var message2 = "Client "+ userID+ " is feeling extremely bad";
		client.messages.create({
		        body: message2,
		        to: phoneNumber,  // Text this number
		        from: twilioNumber // From a valid Twilio number
		    })
		    .then((message) => console.log(message.sid));
}

/*
var userId = firebase.auth().currentUser.uid;


writeUserData("Test1","test2","Test3","test34");

function writeUserData(userId, name, email, imageUrl) {
  firebase.database().ref('users/' + userId).set({
    username: name,
    email: email,
    profile_picture : imageUrl
  });
}
*/
}, 5000);