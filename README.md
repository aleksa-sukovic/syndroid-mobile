# SynDroid Mobile

SynDroid is application that allows you, on the one hand, to control your computer from your phone, and on the other, to get updates from your phone directly on host computer.

 - It was written as an assignment project for `Advanced Programming Techniques` course

 - If you havent already, please read my README file for my [Syndroid Desktop](https://github.com/aleksa-sukovic/syndroid-desktop) project since it is related to this project.

**I'd like to note that both server and desktop applications implement the same protocol, meaning they can both send and receive same types of requests. This was achived with a little more effort here, since project is written in Java insted of JavaScript**

`scroll down to see images`

## Technologies
- Java

## Application Structure

There were few interesting software design principles and techniques I've used in order to make this project the best it could be at this particular moment with my current knowlege.

### Communication protocol

By far, the most interesting thing about this project is its communication system. All communication between server and client is done through socket connection.

I've built a "miniature" protocol that describes communication between server and client. If you look at example messages you'll' think that they look a lot like regular HTTP call,and you'd be right since HTTP protocol was my inspiration.
   
    /mouse/move?x=15&y=15
        tells the server to move mouse 15 pixes in both x and y axis
    /mouse/left-click
        tells the server to do mouse left click

When we want to send this kind of message (request) to server, we can do so using the OutgoingRequest class
```javascript
// example of constructing OutgoingRequest instance
OutgoingRequest = new OutgoingRequest.Builder()
            .setRoutePath("/mouse/move")
            .addParam("x", 15)
            .addParam("y", 15)
            .build();
```

You can see that I've used builder pattern to allow easy creation of OutgoingRequest.

### Laravel inspiration

**The main difference here, from Syndroid Desktop implementation, is the need for Java Reflection API in order to initialize appropriate classes and handlers for incoming requests.**

Since I work with Laravel on daily basis, I'm very much familiar with its architecture and design principles. This insipred me to try to separate my application in such a way that it it would be open for extension and closed for modifications.

Each of the objects controllable by client are defined in a separate file `ServiceProviderContainer.java`. This file contains array of service providers (Laravel inspiration kicks in) which define objects that will be available to entire application.

```javascript
// Example definition of ServiceProvider
 public class UtilsServiceProvider implements ServiceProvider 
 {
    @Override
    public List<Route> getRoutes()
    {
        return Arrays.asList(
            new Route("/device/info", "com.aleksa.syndroid.objects.utils.controllers.UtilsController", "deviceInfo"),
            new Route("/device/ping", "com.aleksa.syndroid.objects.utils.controllers.UtilsController", "ping")
        );
    }
 }
 
 // ServiceProviderContainer.java
 public class ServiceProviderContainer 
 {
    private List<Class<?>> serviceProviders;
    
    {
        serviceProviders = Arrays.asList(
            UtilsServiceProvider.class,
            ...
        );
    }

    public List<Class<?>> getProviders() 
    {
        return serviceProviders;
    }
 }
 
```

You can see that when defining custom ServiceProvider you implement interface `ServiceProvider` which defines one method `getRoutes`. You implement that method to return available routes for your object. Each route contains a path, controller and approprate handler method to be invoked when that route is hit.

**You can see that I don't instantiate service providers in ServiceProviderContainer, instead I only reference the class. Instantiation is done through Reflection API.**

You can also validate incoming request by extending BaseValidator class

```javascript
// example of validator definition
public class UtilsValidator extends BaseValidator 
{
    {
        this.rules = new LinkedList<>(
            new Rule("device_id", "deviceInfo", "[1-9]+", true);
        );
    }
}
```

As you can see UtilsValidator class extends BaseValidator and you are required to initialize linked list of available rules. Each rule contains parameter name, handler method for which this rule applies, regular expression defining parameter and boolean filed indicating if parameter is requried.

```javascript
// Example of controller definition
public class UtilsController extends BaseController 
{
    {
        validator = new UtilsValidator();
    }

    public String deviceInfo(Context context, IncomingRequest request) 
    {
        // implementation    
    }
}
```

Finally, we define object controller which gets called once appropriate route has been hit. BaseController automatically validates incoming request, so we are sure that we won't hit controllers handler method with inappropriate parameters.
**BaseController uses reflection to dynamically call appropriate handler for incoming request.**

## Images

![Syndroid Mobile Welcome](https://api.pcloud.com/getpubthumb?code=XZmDrK7ZQRPCorcJCx0mT4GMGH3sKXrTnUP7&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Server Dashboard](https://api.pcloud.com/getpubthumb?code=XZ1DrK7ZhDiJJnekyrz3Q2a4Bx9RWpJP8WrV&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Favourites edit](https://api.pcloud.com/getpubthumb?code=XZqDrK7ZjDe9oTOo7VQL6nSMdVw25SxYlHx7&linkpassword=undefined&size=250x650&crop=0&type=auto)
![Syndroid Mobile Mouse Controll](https://api.pcloud.com/getpubthumb?code=XZV1rK7ZHR5YSbp7ukY4UbhFslDzyk7zqyby&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Favourites edit](https://api.pcloud.com/getpubthumb?code=XZ91rK7ZKavRSu9meUH7XuVpUOK8jJQ5jT3k&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Mouse Controll](https://api.pcloud.com/getpubthumb?code=XZt1rK7ZED1pS7cRJf4pTlk2GwkxfbdcU0zy&linkpassword=undefined&size=250x650&crop=0&type=auto)
![Syndroid Mobile Favourites edit](https://api.pcloud.com/getpubthumb?code=XZv1rK7ZnHsnSNbk1dHqgyr1LwpO2R9wmwWX&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Mouse Controll](https://api.pcloud.com/getpubthumb?code=XZyerK7ZMNgMXewmszJSOElxv4NU3RtpWMWk&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Favourites edit](https://api.pcloud.com/getpubthumb?code=XZjerK7ZmDSjrs5JUU5WSUnSsmbNq5lsw7Gk&linkpassword=undefined&size=250x650&crop=0&type=auto) 
![Syndroid Mobile Mouse Controll](https://api.pcloud.com/getpubthumb?code=XZMerK7ZYf2BYAw5qrLpQdSiJyryMJj35EY7&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Mouse Controll](https://api.pcloud.com/getpubthumb?code=XZm9rK7ZFfiImaipkwjHDRBxzvKqB5N9by3V&linkpassword=undefined&size=250x650&crop=0&type=auto) ![Syndroid Mobile Mouse Controll](https://api.pcloud.com/getpubthumb?code=XZzCrK7ZektpvrJfT6VS9cu3o8mm7XnyoCl7&linkpassword=undefined&size=250x650&crop=0&type=auto)
