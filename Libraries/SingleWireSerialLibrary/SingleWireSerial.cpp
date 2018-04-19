//
//  SingleWireSerial.cpp
//  SingleWireSerial
//
//  Created by Matt Mcpartlan on 9/5/17.
//  Copyright © 2017 Matt Mcpartlan. All rights reserved.
//
// This library goes with the OneWire board.

#include "SingleWireSerial.h"
#include "Arduino.h"

SingleWireSerial::SingleWireSerial(int transmit, int numberOfRegisters)
{
    pinMode(transmit, OUTPUT);
    _transmit = transmit;
    _numberOfRegisters = numberOfRegisters;
    _bitDelay = 115;
    //_bitDelay = 60;
    _bits = numberOfRegisters * 8;
    int tempArray[64] = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    for (int i = 0; i < 64; i++) {
        _dataArray[i] = tempArray[i];
    }
}

void SingleWireSerial::updatePins()
{
    int runThrough = 0;
    for (int i = 0; i < _bits; i++) {
        if (_dataArray[i] == 1) {
            digitalWrite(_transmit, HIGH);
        } else {
            digitalWrite(_transmit, LOW);
        }
        runThrough++;
        if (runThrough == 1) {
            delayMicroseconds(_bitDelay - (_bitDelay * 0.6));    // Taking into account time to reset the board
            //delay(bitDelay - (bitDelay * 0.6));                // Uncomment for low frequency demo mode
        } else {
            delayMicroseconds(_bitDelay + (_bitDelay * 0.0185)); // Slowly 'catch up' after initial delay
            //delay(bitDelay + (bitDelay * 0.0185));             // Uncomment for low frequency demo mode
        }
    }
    digitalWrite(_transmit, LOW);
    delayMicroseconds(250);
}

void SingleWireSerial::updateArray(int arrayIndex, int state)
{
    if ((state == HIGH) && (arrayIndex > 0) && arrayIndex <= _bits) {
        _dataArray[arrayIndex] = HIGH;
    }
    if ((state == LOW) && (arrayIndex > 0) && arrayIndex <= _bits) {
        _dataArray[arrayIndex] = LOW;
    }
}

void SingleWireSerial::digitalWrite(int arrayIndex, int state)
{
    updateArray(arrayIndex, state);
    updatePins();
}
