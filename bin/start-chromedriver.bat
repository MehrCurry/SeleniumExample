#!/bin/sh

set SELENIUM_HOME="C:\java\selenium-2.25.0"

java -jar %SELENIUM_HOME%/selenium-server-standalone-2.25.0.jar -Dwebdriver.chrome.driver=driver\chromedriver.exe -role webdriver -hub http://localhost:4444/grid/register -port 5558
