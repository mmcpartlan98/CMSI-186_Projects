// To add new recipe:
// - update NUMBER_OF_RECIPES constant
// - add recipe to ratio switch statement
// - add drink name to display switch statement
//
// To add new pump/ingredient:
// - update CONNECTED_PUMPS constant
// - #define INGREDIENT PIN
// - add INGREDIENT to pump[] array
// - define 'ingredient' in ratio switch statement
// - add 'ingredient' and INGREDIENT to runTime[][] array
// - add 'digitalWrite(INGREDIENT, LOW) at the end of recipeBook

#include <Adafruit_CharacterOLED.h>
#include <string.h>

#define BAUD_RATE 9600

#define TRIPLE_SEC 1
#define LIME_JUICE 2
#define CRANBERRY 3
#define VODKA 4
#define RUM 5
#define TEQUILA 6
#define KEYPAD A1
#define CONNECTED_PUMPS 6
#define NUMBER_OF_RECIPES 2

// UNCOMMENT FOR 12VDC (ml/s)
//#define FLOW_RATE_TRIPLE_SEC 3.9592
//#define FLOW_RATE_LIME_JUICE 3.876
//#define FLOW_RATE_CRANBERRY 3.496
//#define FLOW_RATE_VODKA 3.8
//#define FLOW_RATE_RUM 3.76
//#define FLOW_RATE_TEQUILA 3.42

// UNCOMMENT FOR 24VDC
#define FLOW_RATE_TRIPLE_SEC 8.1
#define FLOW_RATE_LIME_JUICE 8.2
#define FLOW_RATE_CRANBERRY 7.5
#define FLOW_RATE_VODKA 7.8
#define FLOW_RATE_RUM 8.2
#define FLOW_RATE_TEQUILA 7.5

#define INTERVAL 100

#define btnONE 5
#define btnTWO 3
#define btnTHREE 6
#define btnNONE 0

#define redPin 3
#define greenPin 9
#define bluePin 10

double pump[CONNECTED_PUMPS] = {TRIPLE_SEC, LIME_JUICE, CRANBERRY, VODKA, RUM, TEQUILA};

int working_pump = 0;
int debug = 1;
int flasherDirection = 1;
int brightness[3] = {50, 50, 50};
unsigned long taskTimer = 0;
unsigned long lastAction = 0;
int currentSelection = 1;
double drinkSize = 45.7;
int programLevel = -1;
int drinkSelectDisplay = 0;
int dispensingDisplay = 0;
int sizeDisplay = 0;
int drinkDisplayCode = 1;
int collectiveBrightness = 50;
int shotFlag = 0;
int iterations = 0;
int inactionTimer = 0;
Adafruit_CharacterOLED lcd(OLED_V2, 7, 4, 2, 8, 14, 15, 16);
int bitDelay = 115;
int transmit = 18;
int dataArray[16] = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
int canPressAgain = 1;
int buttonTimer = 0;
unsigned int rgbColour[3] = {255, 0, 0};
int LEDDir = 1;
int decColour = 0;
int incColour = 1;
int decCounter = 0;
int passwordArray[4] = {btnONE, btnTWO, btnTWO, btnTHREE};
int inputPassword[4] = {0, 0, 0, 0};
int nextPassword = 0;

void recipeBook(int dispenseFlag) {
  if (debug == 0) lcd.print("dispenseFlag: ");
  if (debug == 0) lcd.println(dispenseFlag);
  double tequila = 0;
  double tripleSec = 0;
  double cranberry = 0;
  double limeJuice = 0;
  double vodka = 0;
  double rum = 0;
  //
  //  switch (currentSelection) { // Calibration switch
  //    case 1:
  //      tequila = 1;
  //      break;
  //    case 2:
  //      tripleSec = 1;
  //      break;
  //    case 3:
  //      cranberry = 1;
  //      break;
  //    case 4:
  //      limeJuice = 1;
  //      break;
  //    case 5:
  //      vodka = 1;
  //      break;
  //    case 6:
  //      rum = 1;
  //      break;
  //  }

  switch (currentSelection) {
    case 1: // Margarita
      tequila = 0.40;
      limeJuice = 0.27;
      tripleSec = 0.33;
      shotFlag = 1;
      break;
    case 2: // Susanne Margarita
      limeJuice = 0.4;
      tripleSec = 0.4;
      tequila = 0.2;
      shotFlag = 1;
      break;
//    case 2: // Cosmopolitan
//      limeJuice = 0.192;
//      cranberry = 0.35;
//      tripleSec = 0.13;
//      vodka = 0.328;
//      shotFlag = 1;
//      break;
    case 3: // Kamakazi
      vodka = 0.333;
      tripleSec = 0.333;
      limeJuice = 0.333;
      shotFlag = 1;
      break;
    case 4: // Rummy Cranberry
      rum = 0.25;
      cranberry = 0.75;
      break;
    case 5: // Hard Cranberry
      cranberry = 0.75;
      vodka = 0.25;
      break;
    case 6: // Vodka Shot
      vodka = 1;
      shotFlag = 1;
      break;
    case 7: // Tequila Shot
      tequila = 1;
      shotFlag = 1;
      break;
    case 8: // Rum Shot
      rum = 1;
      shotFlag = 1;
      break;
    default:
      break;
  }
  if (dispenseFlag == 1) {
    double runTime[2][CONNECTED_PUMPS] = {{
        ((drinkSize * tripleSec) / FLOW_RATE_TRIPLE_SEC) * 1000, // runTime[0]
        ((drinkSize * limeJuice) / FLOW_RATE_LIME_JUICE) * 1000,
        ((drinkSize * cranberry) / FLOW_RATE_CRANBERRY) * 1000,
        ((drinkSize * vodka) / FLOW_RATE_VODKA) * 1000,// runTime[1]
        ((drinkSize * rum) / FLOW_RATE_RUM) * 1000,
        ((drinkSize * tequila) / FLOW_RATE_TEQUILA) * 1000,
      },
      {TRIPLE_SEC, LIME_JUICE, CRANBERRY, VODKA, RUM, TEQUILA} // MUST correspond with ingredients in runtime[0]
    };

    int sortingFlag = 1;
    while (sortingFlag == 1) {
      sortingFlag = 0;
      for (int i = CONNECTED_PUMPS - 1; i > 0; i--) { // Sorts by milliseconds running
        if (runTime[0][i] > runTime[0][i - 1]) {
          double temp = runTime[0][i - 1];
          double tempPins = runTime[1][i - 1];
          runTime[0][i - 1] = runTime[0][i];
          runTime[1][i - 1] = runTime[1][i];
          runTime[0][i] = temp;
          runTime[1][i] = tempPins;
          sortingFlag = 1;
        }
      }
    }
    double runTimeDifferences[2][CONNECTED_PUMPS];
    double level = 1;
    for (int i = 0; i < CONNECTED_PUMPS; i++) {
      if (runTime[0][i] == 0) {
        runTimeDifferences[1][i] = 0;
        runTimeDifferences[0][i] = 0;
        // If runtime is the same as the one after it or the one after it == 0
      }
      else if ((runTime[0][i] - runTime[0][i + 1] == runTime[0][i]) || runTime[0][i + 1] == 0) {
        runTimeDifferences[0][i] = runTime[0][i];
        runTimeDifferences[1][i] = level;
        if (i == CONNECTED_PUMPS - 1) {
          runTimeDifferences[0][i + 1] = runTime[0][i];
          runTimeDifferences[1][i + 1] = level;
        }
      } else {
        runTimeDifferences[1][i] = level;
        if (runTime[0][i + 1] != 0) {
          runTimeDifferences[0][i] = runTime[0][i] - runTime[0][i + 1];
          if (i != CONNECTED_PUMPS - 1) {
            level++;
          }
        } else {
          runTimeDifferences[0][i] = runTime[0][i];
        }
      }
    }
    int workingLevel = 0;
    for (int i = 0; i < level; i++) {
      while (runTimeDifferences[1][workingLevel] == (i + 1)) {
        digitalWriteExp(runTime[1][workingLevel], HIGH);
        workingLevel++;
      }
      delay(runTimeDifferences[0][workingLevel - 1]);
    }
    digitalWriteExp(TRIPLE_SEC, LOW);
    digitalWriteExp(LIME_JUICE, LOW);
    digitalWriteExp(CRANBERRY, LOW);
    digitalWriteExp(VODKA, LOW);
    digitalWriteExp(RUM, LOW);
    digitalWriteExp(TEQUILA, LOW);
  }
}

void updateDrinkDisplay(int statusTag) {
  if (statusTag == 1 && drinkSelectDisplay != 1) {   //First drink menu
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Drink: ");
    drinkSelectDisplay = 1;
    dispensingDisplay = 0;
    sizeDisplay = 0;
  } else if (statusTag == 0 && dispensingDisplay != 1) {
    lcd.clear();
    lcd.setCursor(0, 0);
    drinkSelectDisplay = 0;
    //dispensingDisplay = 1;
    sizeDisplay = 0;
    lcd.print("Making: ");
    switch (currentSelection) {
      case 1:
        lcd.print("Margarita    ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Teq: 33%, TrSec: 33%");
        lcd.setCursor(0, 3);
        lcd.print("Lime: 33%           ");
        break;
      case 2:
        lcd.print("Suz Margarita");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Teq: 20%, TrSec: 40%");
        lcd.setCursor(0, 3);
        lcd.print("Lime: 40%           ");
        break;
//      case 2:
//        lcd.print("Cosmopolitan ");
//        lcd.setCursor(0, 1);
//        lcd.print("====================");
//        lcd.setCursor(0, 2);
//        lcd.print("Lime: 19%, Cran: 35%");
//        lcd.setCursor(0, 3);
//        lcd.print("TrSec: 13%, Vdk: 33%");
//        break;
      case 3:
        lcd.print("Kamakazi     ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Lime: 33%, TSec: 33%");
        lcd.setCursor(0, 3);
        lcd.print("Vdk: 33%            ");
        break;
      case 4:
        lcd.print("Rummy Cran   ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Rum: 25%, Cran: 75% ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 5:
        lcd.print("Cran Vodka   ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Vdk: 25%, Cran: 75% ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 6:
        lcd.print("Vodka Shot          ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Vdk: 100%           ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 7:
        lcd.print("Tequila Shot        ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Teq: 100%           ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 8:
        lcd.print("Rum Shot            ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Rum: 100%           ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      default:
        break;
    }
  } else if (statusTag == 2 && sizeDisplay != 1) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Size (in mL): ");
    lcd.setCursor(0, 1);
    lcd.print(drinkSize);
    drinkSelectDisplay = 0;
    dispensingDisplay = 0;
    sizeDisplay = 1;
  }
  if (statusTag == 1) {
    lcd.setCursor(7, 0);
    //        switch (currentSelection) {
    //          case 1:
    //            lcd.print("Pump 6          ");
    //            break;
    //          case 2:
    //            lcd.print("Pump 1          ");
    //            break;
    //          case 3:
    //            lcd.print("Pump 3          ");
    //            break;
    //          case 4:
    //            lcd.print("Pump 2          ");
    //            break;
    //          case 5:
    //            lcd.print("Pump 4          ");
    //            break;
    //          case 6:
    //            lcd.print("Pump 5          ");
    //            break;
    //    }
    switch (currentSelection) {
      case 1:
        lcd.print("Margarita    ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Tequila, Triple Sec ");
        lcd.setCursor(0, 3);
        lcd.print("Lime Juice          ");
        break;
      case 2:
        lcd.print("Suz Margarita");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Tequila, Triple Sec ");
        lcd.setCursor(0, 3);
        lcd.print("Lime Juice          ");
        break;
//      case 2:
//        lcd.print("Cosmopolitan ");
//        lcd.setCursor(0, 1);
//        lcd.print("====================");
//        lcd.setCursor(0, 2);
//        lcd.print("Lime, Cranberry     ");
//        lcd.setCursor(0, 3);
//        lcd.print("Triple Sec, Vodka   ");
//        break;
      case 3:
        lcd.print("Kamakazi     ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Vodka, Triple Sec   ");
        lcd.setCursor(0, 3);
        lcd.print("Lime Juice          ");
        break;
      case 4:
        lcd.print("Rummy Cran   ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Rum, Cranberry      ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 5:
        lcd.print("Cran Vodka   ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("Vodka, Cranberry    ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 6:
        lcd.print("Vodka Shot          ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("                    ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 7:
        lcd.print("Tequila Shot        ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("                    ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      case 8:
        lcd.print("Rum Shot            ");
        lcd.setCursor(0, 1);
        lcd.print("====================");
        lcd.setCursor(0, 2);
        lcd.print("                    ");
        lcd.setCursor(0, 3);
        lcd.print("                    ");
        break;
      default:
        break;
    }
  }
}

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

void setup() {
  // Initialize communication (serial, 9600 baud)
  lcd.begin(20, 4);
  lcd.setCursor(0, 0);
  wireBegin(18);

  //Begin serial connection
  Serial.begin(BAUD_RATE);

  while ((Serial.available() == 0) && (millis() < 5000));

  if (Serial.available() > 0)
  {
    Serial.read();
    lcd.print("Serial: ");
    lcd.print(BAUD_RATE);
    lcd.print("bd");
    debug = 0;
  }
  else
  {
    lcd.print("Serial: N/A");
  }
  if (debug == 0) Serial.println("Serial connection established!");

  delay(1000);

  while (working_pump < CONNECTED_PUMPS) {
    pinMode(pump[working_pump], OUTPUT);
    digitalWriteExp(pump[working_pump], LOW);
    if (debug == 0) {
      Serial.print("Relay ");
      Serial.print(working_pump + 1);
      Serial.println(" initialized");
    }
    working_pump++;
  }

  working_pump = 0;

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Startup util?");
  lcd.setCursor(0, 1);
  lcd.print("NO           YES");

  int exitCode = 0;
  while (exitCode != 1) {
    taskTimer = millis();
    if (taskTimer - lastAction > INTERVAL) {
      lastAction = taskTimer;
      int readValPrime = read_input();
      if (readValPrime == btnONE) {
        exitCode = 1;
        delay(250);
      } else if (readValPrime == btnTHREE) {
        lcd.clear();
        lcd.setCursor(0, 0);
        lcd.print("Pump ");
        lcd.setCursor(0, 1);
        lcd.print("<(ON) NEXT (ON)>");
        Serial.println("Entering btnTHREE loop.");
        int exitCode2 = 0;
        int readVal = 0;
        while (exitCode2 != 1) {
          taskTimer = millis();
          if (taskTimer - lastAction > INTERVAL) {
            lastAction = taskTimer;
            if (working_pump < CONNECTED_PUMPS) {
              lcd.setCursor(6, 0);
              lcd.print(working_pump + 1);
              readVal = read_input();
              if (readVal != btnNONE) {
                if ((readVal == btnONE) || (readVal == btnTHREE)) {
                  Serial.println("Turning on pump.");
                  digitalWriteExp(pump[working_pump], HIGH);
                  delay(125);
                } else if (readVal == btnTWO) {
                  working_pump++;
                  delay(250);
                }
              }
            } else {
              exitCode2 = 1;
              exitCode = 1;
              lcd.clear();
              lcd.setCursor(0, 0);
              lcd.print("Done priming...");
              delay(1000);
              lcd.clear();
            }
            digitalWriteExp(pump[working_pump], LOW);
          } else {
            //INSERT LIGHT CODE
            if (flasherDirection == 0) {
              if (collectiveBrightness > 1) {
                collectiveBrightness -= 1;
              } else {
                flasherDirection = 1;
              }

            } else if (flasherDirection == 1) {
              if (collectiveBrightness < 255) {
                collectiveBrightness += 1;
              } else {
                flasherDirection = 0;
              }
            }
            analogWrite(btnONE, collectiveBrightness);

            analogWrite(btnTHREE, collectiveBrightness);
            delay(10);
          }
        }
      }
    } else {
      //INSERT LIGHT CODE
      if (flasherDirection == 0) {
        if (collectiveBrightness > 1) {
          collectiveBrightness -= 1;
        } else {
          flasherDirection = 1;
        }

      } else if (flasherDirection == 1) {
        if (collectiveBrightness < 255) {
          collectiveBrightness += 1;
        } else {
          flasherDirection = 0;
        }
      }
      analogWrite(btnONE, collectiveBrightness);

      analogWrite(btnTHREE, collectiveBrightness);
      delay(5);
    }
  }
  if (debug == 0) Serial.println("Launching UI...");
  if (debug == 0) Serial.println("");
  if (debug == 0) Serial.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
  lcd.clear();
  dataArray[0] = 1;
  for (int i = 1; i <= 15; i++) {
    dataArray[i] = 0;
  }
  updatePins();
}

void loop() {
  int readVal = 0;
  int readVal1 = 0;
  int readVal2 = 0;
  int correctPassword = 1;
  int cleaningFlag = 1;
  taskTimer = millis();
  if (programLevel == -1) {
    readVal = read_input();
    lcd.setCursor(0, 0);
    lcd.print("Password: ");
    lcd.print(inputPassword[0]);
    lcd.print(" ");
    lcd.print(inputPassword[1]);
    lcd.print(" ");
    lcd.print(inputPassword[2]);
    lcd.print(" ");
    lcd.print(inputPassword[3]);
    if (nextPassword == 0 && readVal != 0) {
      inputPassword[nextPassword] = readVal;
      nextPassword++;
      delay(200);
    } else if (nextPassword == 1 && readVal != 0) {
      inputPassword[nextPassword] = readVal;
      nextPassword++;
      delay(200);
    } else if (nextPassword == 2 && readVal != 0) {
      inputPassword[nextPassword] = readVal;
      nextPassword++;
      delay(200);
    } else if (nextPassword == 3 && readVal != 0) {
      inputPassword[nextPassword] = readVal;
      nextPassword++;
      delay(200);
    } else if (nextPassword == 4) {
      for (int i = 0; i <= 3; i++) {
        if (inputPassword[i] != passwordArray[i]) {
          correctPassword = 0;
        }
      }
      if (correctPassword == 1) {
        programLevel = 0;
      }
      for (int i = 0; i <= 3; i++) {
        inputPassword[i] = 0;
      }
      nextPassword = 0;
    }

  } else if (programLevel == 0) {
    if (taskTimer - lastAction >= INTERVAL) {
      lastAction = taskTimer;
      readVal1 = read_input();
    }
    updateDrinkDisplay(drinkDisplayCode); //1 is passed to statusTag
    if (readVal1 != btnNONE) {
      if (readVal1 == btnTHREE) {
        brightness[2] = 255;
        if (currentSelection < NUMBER_OF_RECIPES) {
          currentSelection++;
        } else if (readVal1 != 0) {
          currentSelection = 1;
        }
        if (debug == 0) Serial.print("currentSelection: ");
        if (debug == 0) Serial.println(currentSelection);
        drinkDisplayCode = 1;
        delay(200);
      } else if (readVal1 == btnONE) {
        brightness[0] = 255;
        if (currentSelection > 1) {
          currentSelection--;
        } else {
          currentSelection = NUMBER_OF_RECIPES;
        }
        if (debug == 0) Serial.print("currentSelection: ");
        if (debug == 0) Serial.println(currentSelection);
        drinkDisplayCode = 1;
        delay(200);
      } else if (readVal1 == btnTWO) {
        brightness[1] = 255;
        if (debug == 0) Serial.println("Entering size selection loop.");
        drinkDisplayCode = 2;
        sizeDisplay = 0;
        programLevel = 1;
        recipeBook(0); // drinkSize is zero, dispenseFlag is 0 to just run the switch
        inactionTimer = 0;
        delay(200);
      } else { // Insert else code for WORKING LEVEL 0
      }
    }
  } else if (programLevel == 1) {
    if (taskTimer - lastAction >= INTERVAL) {
      lastAction = taskTimer;
      readVal2 = read_input();
      updateDrinkDisplay(drinkDisplayCode);
      if (readVal2 != btnNONE && canPressAgain == 1) {
        canPressAgain = 0;
        buttonTimer = 0;
        if (readVal2 == btnONE && shotFlag != 1) {
          brightness[0] = 255;
          if (drinkSize == 45.7) {
            drinkSize = 300;
          } else if (drinkSize == 50) {
            drinkSize = drinkSize - 4.3;
          } else {
            drinkSize = drinkSize - 50;
          }
          if (debug == 0) Serial.print("Size is now ");
          if (debug == 0) Serial.print(drinkSize);
          if (debug == 0) Serial.println(".");;
          drinkDisplayCode = 2;
          inactionTimer = 0;
          sizeDisplay = 0;
        } else if (readVal2 == btnTHREE && shotFlag != 1) {
          brightness[2] = 255;
          if (drinkSize == 45.7) {
            drinkSize = drinkSize + 4.3;
          } else if (drinkSize == 300) {
            drinkSize = 45.7;
          } else {
            drinkSize = drinkSize + 50;
          }
          if (debug == 0) Serial.print("Size value is now ");
          if (debug == 0) Serial.print(drinkSize);
          if (debug == 0) Serial.println(".");
          drinkDisplayCode = 2;
          inactionTimer = 0;
          sizeDisplay = 0;
        } else if (readVal2 == btnTWO) {
          brightness[1] = 255;
          drinkDisplayCode = 0;
          updateDrinkDisplay(drinkDisplayCode);
          brightness[1] = 255;
          for (int i = 0; i <= 2; i++) {
            while (brightness[i] > 50) {
              brightness[i] = brightness[i] - 3;
              analogWrite(btnONE, brightness[0]);
              analogWrite(btnTHREE, brightness[2]);
              delay(10);
            }
          }
          recipeBook(1); // 1 goes to dispenseFlag to dispense the drink
          shotFlag = 0;
          drinkDisplayCode = 1;
          currentSelection = 1;
          drinkSize = 45.7;
          programLevel = -1;
          lcd.clear();
        }
      } else { // Set else for WORKING LEVEL 1
        inactionTimer++;
        if (debug == 0) Serial.print("inactionTimer: ");
        if (debug == 0) Serial.println(inactionTimer);
        if (inactionTimer == 125) {
          programLevel = 0;
          shotFlag = 0;
          drinkDisplayCode = 1;
          currentSelection = 1;
          drinkSize = 45.7;
          if (debug == 0) Serial.print("inactionTimer triggered *__________________*\n");
        }
        buttonTimer++;
        if (buttonTimer > 1) {
          canPressAgain = 1;
        }
      }
    }
  } else if (programLevel == 2) {
    // "BACKGROUND" FUNCTIONALITY GOES HERE
  }
  if (iterations % 10 == 0) {
    for (int i = 0; i <= 2; i++) {
      if (brightness[i] > 50) {
        brightness[i] = brightness[i] - 3;
      }
    }
    if (decColour < 3) {
      if (decCounter < 255) {
        rgbColour[decColour] -= 1;
        rgbColour[incColour] += 1;
        setColourRgb(rgbColour[0], rgbColour[1], rgbColour[2]);
        decCounter++;
      } else {
        decColour++;
        decCounter = 0;
        incColour = decColour == 2 ? 0 : decColour + 1;
      }
    } else {
      decColour = 0;
      incColour = 1;
      rgbColour[0] = 255;
      rgbColour[1] = 0;
      rgbColour[2] = 0;
    }
    iterations = 0;
  }
  iterations++;
  analogWrite(btnONE, brightness[0]);
  analogWrite(btnTHREE, brightness[2]);
}
void updatePins() {
  int runThrough = 0;
  for (int i = 0; i <= 15; i++) {
    if (dataArray[i] == 1) {
      digitalWrite(transmit, HIGH);
    } else {
      digitalWrite(transmit, LOW);
    }
    runThrough++;
    if (runThrough == 1) {
      delayMicroseconds(bitDelay - (bitDelay * 0.6)); //delay
    } else {
      delayMicroseconds(bitDelay + (bitDelay * 0.0185)); //delay
    }
  }
  digitalWrite(transmit, LOW);
  delayMicroseconds(2000);
}

void digitalWriteExp(int pinNumber, uint8_t binVal) {
  if (binVal == HIGH) {
    dataArray[pinNumber] = 1;
  } else {
    dataArray[pinNumber] = 0;
  }
  updatePins();
}

void wireBegin(int pinOut) {
  transmit = pinOut;
  pinMode(transmit, OUTPUT);
  for (int i = 1; i <= 15; i++) {
    digitalWriteExp(i, LOW);
  }
  delayMicroseconds(1000);
}

void setColourRgb(unsigned int red, unsigned int green, unsigned int blue) {
  analogWrite(redPin, red);
  analogWrite(greenPin, green);
  analogWrite(bluePin, blue);
}

int read_input() {
  int analogValue = 0;
  int buttonPressed = btnNONE;
  int waitTimer = 0;
  int exitCode = 0;
  do {
    analogValue = analogRead(KEYPAD);
    waitTimer += 1;
    if (waitTimer > 10) exitCode = 1;
  } while (analogValue < 50 && exitCode != 1);

  if (analogValue >= 850 && (analogValue < 1500)) {
    buttonPressed = btnTWO;
  } else if (analogValue >= 200 && analogValue < 600) {
    buttonPressed = btnONE;
  } else if (analogValue >= 600 && analogValue < 850) {
    buttonPressed = btnTHREE;
  } else {
    buttonPressed = btnNONE;
  }
  return buttonPressed;
}
