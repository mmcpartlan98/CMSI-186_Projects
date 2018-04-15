//
//  SingleWireSerial.cpp
//  SingleWireSerial
//
//  Created by Matt Mcpartlan on 9/5/17.
//  Copyright © 2017 Matt Mcpartlan. All rights reserved.
//
// This library goes with the Drink Machine 2.0 main logic board. It adds shift register functionalty for the two onboard registers ONLY

#include "Arduino.h"
#include "Shift.h"

Shift::Shift(int SER, int SRCLK, int RCLK) {
  _SER = SER;
  _SRCLK = SRCLK;
  _RCLK = RCLK;
  pinMode(_SER, OUTPUT);
  pinMode(_SRCLK, OUTPUT);
  pinMode(_RCLK, OUTPUT);
}

void Shift::begin() {
  updatePins();
}

void Shift::updatePins() {
  digitalWrite(_RCLK, LOW);

  for (int i = 0; i < 16; i++) {
    digitalWrite(_SRCLK, LOW);

    int val = _dataArray[i];

    digitalWrite(_SER, val);
    digitalWrite(_SRCLK, HIGH);

  }
  digitalWrite(_RCLK, HIGH);
}

void Shift::updateArray(int arrayIndex, int state) {
  if ((state == HIGH) && (arrayIndex >= 0) && arrayIndex < 16) {
    _dataArray[arrayIndex] = HIGH;
  }
  if ((state == LOW) && (arrayIndex >= 0) && arrayIndex < 16) {
    _dataArray[arrayIndex] = LOW;
  }
}

void Shift::shiftWrite(int arrayIndex, int state) {
  updateArray(arrayIndex, state);
  updatePins();
}
