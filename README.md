
##appium-1.6-XCUITest-demo

I created a sample iOS Appium test with Java. 
The newly announced 1.6 Appium did not have any sample code so updated the old non-functional sample code from Saucelabs and made it work with
the latest Appium 1.6. Verified the test execution with Xcode 8, iOS SDK 10.0 and iOS simulator iphone plus 7 on SauceLabs. 

##To compile and run all tests, run:

    mvn test

##To run a single test, run:

    mvn -Dtest=com.saucelabs.appium.SimpleTest test

##How to set up the SauceLab authentication before running the test.

Add the following to your .bash_profile.

    export SAUCE_USERNAME=<your User Name>
    export SAUCE_ACCESS_KEY=<Your key>

##Here is the output from Saucelabs

<img src="assets/saucelab-test-result.png" width="800">


