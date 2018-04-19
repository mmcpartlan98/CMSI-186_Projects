#include <Adafruit_CharacterOLED.h>

#define btnONE 5
#define btnTWO 3
#define btnTHREE 6
#define btnNONE 0
#define KEYPAD A1
#define INTERVAL 100

int brightness = 1;
int flasherDirection = 1;
unsigned long previousMillis = 0;

Adafruit_CharacterOLED lcd(OLED_V2, 2, 3, 4, 5, 6, 7, 8);

void keyFlasher(int key) {
  analogWrite(key, brightness);
}

int read_input() {
   int analogValue = 0;
   int buttonPressed = btnNONE;
   int waitTimer = 0;
   int exitCode = 0;
    do {
     analogValue = analogRead(KEYPAD);
     waitTimer += 1;
     if (waitTimer > 5) exitCode = 1;
    } while (analogValue < 50 && exitCode != 1);
    
    if (analogValue >= 800 && (analogValue < 1500)) {
      buttonPressed = btnTWO;
    } else if (analogValue >= 200 && analogValue < 600) {
      buttonPressed = btnONE;
    } else if (analogValue >= 600 && analogValue < 800) {
      buttonPressed = btnTHREE;
    } else {
      buttonPressed = btnNONE;
    }
    return buttonPressed;
}


void setup() {
  // put your setup code here, to run once:

lcd.begin(16, 2);
lcd.setCursor(0, 0);
  lcd.print("Analog Input: ");
  Serial.begin(9600);
}

void loop() {
  unsigned long currentMillis = millis();
  int keyPress;
  if (currentMillis - previousMillis >=  INTERVAL) {
    previousMillis = currentMillis;
    lcd.setCursor(0, 1);
    keyPress = read_input();
    lcd.print(keyPress);
    lcd.print(" ");
    Serial.println(read_input());
  } else {
    if (flasherDirection == 0) {
      if (brightness > 1) {
        brightness -= 1;
      } else {
        flasherDirection = 1;
      }
      
    } else if (flasherDirection == 1) {
      if (brightness < 100) {
        brightness += 1;
      } else {
        flasherDirection = 0;
      }
    }
    unsigned long millis2 = millis();
    analogWrite(btnONE, brightness);
    analogWrite(btnTWO, brightness);
    analogWrite(btnTHREE, brightness);
    lcd.setCursor(5, 1);
    lcd.print(analogRead(KEYPAD));
    lcd.print(" ");
    lcd.setCursor(10, 1);
    lcd.print(currentMillis);
}
}
