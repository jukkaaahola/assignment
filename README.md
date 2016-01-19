# Assignment

This assignment is a standalone Java application that provides REST API in JSON syntax with following details:

  - Provides a list of venues for a given location name, such as "Oulu,Finland"
  - Provides a list of venues for a given WGS84 formatted GPS coordinates, such as "65.08,25.45".
  - Provides an array of photo URLs for a given venueId.
  - Allows user to store a venue as a favorite to a persistent storage.
  - Allows user to update the keywords of a favorite venue into persistent storage.
  - Allows user to delete a favorite venue from persistent storage for a given venueId.
  - Allows user to view all favorite venues.

All request and response payloads are in JSON format.
### Prerequisites
The application has the following requirements:
* Java Development Kit version 8 or newer.
* Git
* Gradle

### Installation
```sh
$ git clone https://github.com/jukkaaahola/assignment.git
$ gradle run
```
The default server runs on localhost and port 8080. The main method supports changing the domain name and port numbers for your needs, if the alternative domain name and port number are provided as command line arguments.

### Technical aspects
The architecture supports micro service paradigm. The Venue search methods can be deployed to multiple separate virtual machines for scalability. The favorite venue services are not scalable due to the integrity of the file based persistence. The methods are strongly synchronized in order to assure that the file transactions are completed. Also the local file system based persistence may not be an ideal solution for micro services for many reasons.

The application utilizes Jersey libraries for REST server and client functionality. ResourceBundles are used for configuration management. The user is responsible to populate the required clientId and clientSecret parameters to file foursquare.properties for better functionality. XStream-library is used for XML-based Java serialization and deserialization in file based persistence. JSON.org-libraries are used for JSON data structure mappings to Java-objects.

### Testing
Unit tests are implemented for the FourSquareClient component and favorites persistence. The REST services have been individually tested by using a Chrome browser with Advanced Rest Client-extension. The tests cover all the public methods in the corresponding classes.

### Security
For additional security, the REST-method to query all stored favorite venues, the server logs the client browser's User-Agent credentials. Ideally the remote address would have been better, but the stand-alone container has limited ways to inject HTTPRequest context elements to the Jersey-services.

This text you see here is *actually* written in Markdown! To get a feel for Markdown's syntax, type some text into the left window and watch the results in the right.

### Version
1.0.0

### Contact
Jukka Ahola

jukka.a.ahola@gmail.com
