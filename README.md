# Cal_App
Simple calendar app

Simple calendar application

This is a server side application.
I run it through an application server (tomcat)

drop the war file [Cal_App-0.0.1-SNAPSHOT.war] in the tomcat webapps folder,
trim the war file name to: Cal_App.war and start the server (run #catalina_home\bin\startup.bat)

rest api:

add new entry:
http://localhost:8080/Cal_App/calAPI/calservice/new;from=<from date time formet: "yyyy-MM-dd'T'HH:mm">;to=<from date time formet: "yyyy-MM-dd'T'HH:mm">;subject=<text>;comments=<text>

Get entry by ID:
http://localhost:8080/Cal_App/calAPI/calservice/<entry Id>

Edit entry:
http://localhost:8080/Cal_App/calAPI/calservice/editparam;entryId=<entry id>;from=<from date time formet: "yyyy-MM-dd'T'HH:mm">;to=<from date time formet: "yyyy-MM-dd'T'HH:mm">;subject=<text>;comments=<text>

delete entry:
http://localhost:8080/Cal_App/calAPI/calservice/delete/<id>

get entries between dates:
http://localhost:8080/Cal_App/calAPI/calservice/list;from=<from date time formet: "yyyy-MM-dd'T'HH:mm">;to=<from date time formet: "yyyy-MM-dd'T'HH:mm">

search test:
http://localhost:8080/Cal_App/calAPI/calservice/search;searchString=<search string>

The app handles empty parameters.

3rd parties:
are configured in the maven's POM file.

Enjoy.

Danny Aram
