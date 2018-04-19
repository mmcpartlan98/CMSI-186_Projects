#include "rgb_lcd.h"
#include <string.h>
#define BAUD_RATE 9600

#define RELAY_ONE 8
#define RELAY_TWO 7
#define RELAY_THREE 5
#define RELAY_FOUR 12 //Does not work (mechanical problem)
#define RELAY_FIVE 9
#define RELAY_SIX 10
#define RELAY_SEVEN 11
#define RELAY_EIGHT 4

#define RELAY_COLD RELAY_ONE
#define RELAY_HOT RELAY_TWO
#define RELAY_THERMO_BACK RELAY_THREE
#define RELAY_THERMO_MIDDLE RELAY_FIVE
#define RELAY_THERMO_FRONT RELAY_SIX

#define btnONE 1
#define btnTWO 2
#define btnTHREE 3
#define btnFOUR 4
#define btnFIVE 5
#define btnSIX 6
#define btnSEVEN 7
#define btnEIGHT 8
#define btnNINE 9
#define btnTEN 10
#define btnELEVEN 11
#define btnTWELVE 12
#define btnNONE 0

int coldPump = 1;
int hotPump = 1;
int thermoelectricPlateBack = 1;
int thermoelectricPlateMiddle = 1;
int thermoelectricPlateFront = 1;
int debug = 1;
String incommingCommand = "";
char incommingByte;


int read_button_values() // Gets joystick position
{
  if ((analogRead(1)) >= 20 && (analogRead(1) < 60)) return btnTWELVE;
  else if (analogRead(1) >= 60 && analogRead(1) < 100) return btnELEVEN;
  else if (analogRead(1) >= 100 && analogRead(1) < 140) return btnTEN;
  else if (analogRead(1) >= 140 && analogRead(1) < 180) return btnNINE;
  else if (analogRead(1) >= 180 && analogRead(1) < 220) return btnEIGHT;
  else if (analogRead(1) >= 220 && analogRead(1) < 260) return btnSEVEN;
  else if (analogRead(1) >= 260 && analogRead(1) < 300) return btnSIX;
  else if (analogRead(1) >= 300 && analogRead(1) < 340) return btnFIVE;
  else if (analogRead(1) >= 340 && analogRead(1) < 380) return btnFOUR;
  else if (analogRead(1) >= 380 && analogRead(1) < 420) return btnTHREE;
  else if (analogRead(1) >= 420 && analogRead(1) < 460) return btnTWO;
  else if (analogRead(1) >= 460) return btnONE;
  else return btnNONE;
}

int update_status() {
  switch (coldPump) {
    case (0): {
        digitalWrite(RELAY_COLD, LOW);
        break;
      }
    case (1): {
        digitalWrite(RELAY_COLD, HIGH);
        break;
      }
  }
  switch (hotPump) {
    case (0): {
        digitalWrite(RELAY_HOT, LOW);
        break;
      }
    case (1): {
        digitalWrite(RELAY_HOT, HIGH);
        break;
      }
  }
  switch (thermoelectricPlateBack) {
    case (0): {
        digitalWrite(RELAY_THERMO_BACK, LOW);
        break;
      }
    case (1): {
        digitalWrite(RELAY_THERMO_BACK, HIGH);
        break;
      }
  }
  switch (thermoelectricPlateMiddle) {
    case (0): {
        digitalWrite(RELAY_THERMO_MIDDLE, LOW);
        break;
      }
    case (1): {
        digitalWrite(RELAY_THERMO_MIDDLE, HIGH);
        break;
      }
  }
  switch (thermoelectricPlateFront) {
    case (0): {
        digitalWrite(RELAY_THERMO_FRONT, LOW);
        break;
      }
    case (1): {
        digitalWrite(RELAY_THERMO_FRONT, HIGH);
        break;
      }
  }
}


rgb_lcd lcd;

void refresh() {
  lcd.clear();
  lcd.print("HOT: ");
  if (hotPump == 1) lcd.print("N ");
  if (hotPump == 0) lcd.print("Y ");
  lcd.print("COLD: ");
  if (coldPump == 1) lcd.print("N ");
  if (coldPump == 0) lcd.print("Y ");
  lcd.setCursor(0, 1);
  lcd.print("F: ");
  if (thermoelectricPlateFront == 1) lcd.print("N ");
  if (thermoelectricPlateFront == 0) lcd.print("Y ");
  lcd.print("M: ");
  if (thermoelectricPlateMiddle == 1) lcd.print("N ");
  if (thermoelectricPlateMiddle == 0) lcd.print("Y ");
  lcd.print("B: ");
  if (thermoelectricPlateBack == 1) lcd.print("N ");
  if (thermoelectricPlateBack == 0) lcd.print("Y ");
}

void read_serial() {
  if (Serial.available() > 0) {
    while (Serial.available()) {
      incommingByte = Serial.read();
      incommingCommand.concat(incommingByte);
      delay(1);
    }
    Serial.println(incommingCommand);
    incommingCommand = "";
  }
}

int checkState () {
  switch (thermoelectricPlateBack + thermoelectricPlateFront + thermoelectricPlateMiddle + hotPump + coldPump) {
    case 0: {
        return 0;
      }
    case 5: {
        return 1;
      }
    default: {
        return 2;
      }
  }
}

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

void setup() {
  // Initialize communication (serial, 9600 baud)
  lcd.begin(16, 2);
  lcd.setCursor(0, 0);
  lcd.setRGB(255, 255, 255);

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

  delay(500);

  if (debug == 0) Serial.println("Relay one connected");
  pinMode(RELAY_ONE, OUTPUT);
  if (debug == 0) Serial.println("Relay two connected");
  pinMode(RELAY_TWO, OUTPUT);
  if (debug == 0) Serial.println("Relay three connected");
  pinMode(RELAY_THREE, OUTPUT);
  if (debug == 0) Serial.println("Relay four connected");
  pinMode(RELAY_FOUR, OUTPUT);
  if (debug == 0) Serial.println("Relay five connected");
  pinMode(RELAY_FIVE, OUTPUT);
  if (debug == 0) Serial.println("Relay six connected");
  pinMode(RELAY_SIX, OUTPUT);
  if (debug == 0) Serial.println("Relay seven connected");
  pinMode(RELAY_SEVEN, OUTPUT);
  if (debug == 0) Serial.println("Relay eight connected");
  pinMode(RELAY_EIGHT, OUTPUT);

  if (debug == 0) Serial.println("Press any button on keypad");

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Press any key...");

  while (analogRead(1) < 30) {
    delay(1);
  }

  if (debug == 0) Serial.println("Testing relay one");
  digitalWrite(RELAY_ONE, HIGH);
  if (debug == 0) Serial.println("Testing relay two");
  digitalWrite(RELAY_TWO, HIGH);
  if (debug == 0) Serial.println("Testing relay three");
  digitalWrite(RELAY_THREE, HIGH);
  if (debug == 0) Serial.println("Testing relay four");
  digitalWrite(RELAY_FOUR, HIGH);
  if (debug == 0) Serial.println("Testing relay five");
  digitalWrite(RELAY_FIVE, HIGH);
  if (debug == 0) Serial.println("Testing relay six");
  digitalWrite(RELAY_SIX, HIGH);
  if (debug == 0) Serial.println("Testing relay seven");
  digitalWrite(RELAY_SEVEN, HIGH);
  if (debug == 0) Serial.println("Testing relay eight");
  digitalWrite(RELAY_EIGHT, HIGH);

  if (debug == 0) Serial.println("Launching UI...");
  if (debug == 0) Serial.println("");
  if (debug == 0) Serial.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

  refresh();
}

void loop() {

  while (read_button_values() != btnNONE) {
    // 0 is ON
    // 1 is OFF
    /*
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Analog Input: ");
      lcd.setCursor(0, 1);
      lcd.print(analogRead(1));
    */
    switch (read_button_values()) {
      case (btnONE):
        {
          if (debug == 0) Serial.println("Recieved input: 1");
          if (coldPump == 0) {
            coldPump = 1;
            if (debug == 0) Serial.println("Hot pump is: OFF");
          } else {
            coldPump = 0;
            if (debug == 0) Serial.println("Hot pump is: ON");
          }
          update_status();
          delay(250);
          break;
        }
      case (btnTWO):
        {
          if (debug == 0) Serial.println("Recieved input: 2");
          if (hotPump == 0) {
            hotPump = 1;
            if (debug == 0) Serial.println("Cold pump is: OFF");
          } else {
            hotPump = 0;
            if (debug == 0) Serial.println("Hot pump is: ON");
          }
          update_status();
          delay(250);
          break;
        }
      case (btnTHREE):
        {
          if (debug == 0) Serial.println("Recieved input: 3");
          if (thermoelectricPlateBack == 0) {
            thermoelectricPlateBack = 1;
            if (debug == 0) Serial.println("Back thermoelectric plate is: OFF");
          } else {
            thermoelectricPlateBack = 0;
            if (debug == 0) Serial.println("Back thermoelectric plate is: ON");
          }
          update_status();
          delay(250);
          break;
        }
      case (btnFOUR):
        {
          if (debug == 0) Serial.println("Recieved input: 4");
          if (thermoelectricPlateMiddle == 0) {
            thermoelectricPlateMiddle = 1;
            if (debug == 0) Serial.println("Middle thermoelectric plate is: OFF");
          } else {
            thermoelectricPlateMiddle = 0;
            if (debug == 0) Serial.println("Middle thermoelectric plate is: ON");
          }
          update_status();
          delay(250);
          break;
        }
      case (btnFIVE):
        {
          if (debug == 0) Serial.println("Recieved input: 5");
          if (thermoelectricPlateFront == 0) {
            thermoelectricPlateFront = 1;
            if (debug == 0) Serial.println("Front thermoelectric plate is: OFF");
          } else {
            thermoelectricPlateFront = 0;
            if (debug == 0) Serial.println("Front thermoelectric plate is: ON");
          }
          update_status();
          delay(250);
          break;
        }
      case (btnSIX):
        {
          if (debug == 0) Serial.println("Recieved input: 6");
          if (checkState() != 2) {
            if (coldPump == 0) {
              coldPump = 1;
              if (debug == 0) Serial.println("Hot pump is: OFF");
            } else {
              coldPump = 0;
              if (debug == 0) Serial.println("Hot pump is: ON");
            }
            if (hotPump == 0) {
              hotPump = 1;
              if (debug == 0) Serial.println("Cold pump is: OFF");
            } else {
              hotPump = 0;
              if (debug == 0) Serial.println("Hot pump is: ON");
            }
            if (thermoelectricPlateBack == 0) {
              thermoelectricPlateBack = 1;
              if (debug == 0) Serial.println("Back thermoelectric plate is: OFF");
            } else {
              thermoelectricPlateBack = 0;
              if (debug == 0) Serial.println("Back thermoelectric plate is: ON");
            }
            if (thermoelectricPlateMiddle == 0) {
              thermoelectricPlateMiddle = 1;
              if (debug == 0) Serial.println("Middle thermoelectric plate is: OFF");
            } else {
              thermoelectricPlateMiddle = 0;
              if (debug == 0) Serial.println("Middle thermoelectric plate is: ON");
            }
            if (thermoelectricPlateFront == 0) {
              thermoelectricPlateFront = 1;
              if (debug == 0) Serial.println("Front thermoelectric plate is: OFF");
            } else {
              thermoelectricPlateFront = 0;
              if (debug == 0) Serial.println("Front thermoelectric plate is: ON");
            }
          }
          update_status();
          delay(250);
          break;
        }
      case (btnSEVEN):
        {
          break;
        }
      case (btnEIGHT):
        {
          break;
        }
      default: {
          update_status();
        }
    }

    refresh();
  }
}

