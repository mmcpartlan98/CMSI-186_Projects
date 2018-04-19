#include <LiquidCrystal.h>


// Min 795
// Max 895
LiquidCrystal lcd(16, 14, 5, 6, 7, 8);
double rawResistance1, rawResistance2, rawResistance3;
double seconds = 0, minutes = 40, hours = 18;
double sat1, sat2, sat3, dataArray1[10], dataArray2[10], dataArray3[10];

int brightness;
int index = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  lcd.begin(16, 2);
  pinMode(3, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(2, OUTPUT);
  pinMode(A3, INPUT);

}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(2, HIGH);
  delay(1000);
  rawResistance1 = analogRead(A3);
  rawResistance2 = analogRead(A2);
  rawResistance3 = analogRead(A1);
  digitalWrite(2, LOW);
  Serial.println(rawResistance2);
  lcd.setCursor(0, 0);
  lcd.print("RAND  SCCL  SPDR");
  if (((hours + (minutes / 60)) >= 22.5) || ((hours + (minutes / 60)) <= 7)) {
    brightness = 1;
    dataArray1[index] = constrain(map(rawResistance1, 490, 890, 0, 100), 0, 100);
    dataArray2[index] = constrain(map(rawResistance2, 490, 890, 0, 100), 0, 100);
    dataArray3[index] = constrain(map(rawResistance3, 490, 890, 0, 100), 0, 100);
    index++;
    if (index == 10) {
      index = 0;
      // Find average of array
      for (int i = 0; i < 10; i++) {
        sat1 = sat1 + dataArray1[i];
      }
      sat1 = sat1 / 10;

      for (int i = 0; i < 10; i++) {
        sat2 = sat2 + dataArray2[i];
      }
      sat2 = sat2 / 10;

      for (int i = 0; i < 10; i++) {
        sat3 = sat3 + dataArray3[i];
      }
      sat3 = sat3 / 10;

      // Light RGB Control
      if ((sat1 <= 50) || (sat2 <= 50) || (sat3 <= 50)) {
        analogWrite(9, brightness);
        analogWrite(10, 0);
        analogWrite(3, 0);
      } else if (((sat1 >= 50) && sat1 <= 75) || ((sat2 >= 50) && sat2 <= 75) || ((sat3 >= 50) && sat3 <= 75)) {
        analogWrite(9, brightness);
        analogWrite(10, brightness);
        analogWrite(3, brightness);
      } else {
        analogWrite(9, 0);
        analogWrite(10, 0);
        analogWrite(3, brightness);
      }

      lcd.setCursor(0, 1);
      lcd.print((int) sat1);
      lcd.print("%  ");
      lcd.setCursor(6, 1);
      lcd.print((int) sat2);
      lcd.print("%  ");
      lcd.setCursor(12, 1);
      lcd.print((int) sat3);
      lcd.print("%   ");

      sat1 = 0;
      sat2 = 0;
      sat3 = 0;
    }
  } else {
    brightness = 200;
    dataArray1[index] = constrain(map(rawResistance1, 490, 890, 0, 100), 0, 100);
    dataArray2[index] = constrain(map(rawResistance2, 490, 890, 0, 100), 0, 100);
    dataArray3[index] = constrain(map(rawResistance3, 490, 890, 0, 100), 0, 100);
    index++;
    if (index == 10) {
      index = 0;
      // Find average of array
      for (int i = 0; i < 10; i++) {
        sat1 = sat1 + dataArray1[i];
      }
      sat1 = sat1 / 10;

      for (int i = 0; i < 10; i++) {
        sat2 = sat2 + dataArray2[i];
      }
      sat2 = sat2 / 10;

      for (int i = 0; i < 10; i++) {
        sat3 = sat3 + dataArray3[i];
      }
      sat3 = sat3 / 10;

      // Light RGB Control
      if ((sat1 <= 50) || (sat2 <= 50) || (sat3 <= 50)) {
        analogWrite(9, brightness);
        analogWrite(10, 0);
        analogWrite(3, 0);
      } else if (((sat1 >= 50) && sat1 <= 75) || ((sat2 >= 50) && sat2 <= 75) || ((sat3 >= 50) && sat3 <= 75)) {
        analogWrite(9, brightness);
        analogWrite(10, brightness);
        analogWrite(3, brightness);
      } else {
        analogWrite(9, 0);
        analogWrite(10, 0);
        analogWrite(3, brightness);
      }

      lcd.setCursor(0, 1);
      lcd.print((int) sat1);
      lcd.print("%  ");
      lcd.setCursor(6, 1);
      lcd.print((int) sat2);
      lcd.print("%  ");
      lcd.setCursor(12, 1);
      lcd.print((int) sat3);
      lcd.print("%   ");

      sat1 = 0;
      sat2 = 0;
      sat3 = 0;
    }
  }

  // Clock
  delay(999);
  seconds = seconds + 2;

  if (seconds == 60) {
    seconds = 0;
    minutes = minutes + 1;
  }
  if (minutes == 60) {
    minutes = 0;
    hours = hours + 1;
  }
  if (hours == 24) {
    hours = 0;
  }
}
