#!/bin/sh

java -jar selenium-server-standalone-2.25.0.jar -Dwebdriver.chrome.driver=driver/chromedriver -role webdriver -hub http://localhost:4444/grid/register -port 5558 # -browser "platform=WINDOWS,browserName=chrome,version=14.0,firefox_binary=C:\Users\%USERNAME%\AppData\Local\Google\Chrome\Application\chrome.exe"
