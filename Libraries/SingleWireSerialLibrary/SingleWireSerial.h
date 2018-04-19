//
//  SingleWireSerial.h
//  SingleWireSerial
//
//  Created by Matt Mcpartlan on 9/5/17.
//  Copyright © 2017 Matt Mcpartlan. All rights reserved.
//

#ifndef SingleWireSerial_h
#define SingleWireSerial_h

#include "Arduino.h"

class SingleWireSerial
{
public:
    SingleWireSerial(int transmit, int numberOfRegisters);
    int begin(int transmit);
    void updatePins();
    void updateArray(int arrayIndex, int state);
    void digitalWrite(int arrayIndex, int state);
private:
    int _transmit;
    int _numberOfRegisters;
    int _bitDelay;
    int _dataArray[64];
    int _bits;
};

#endif /* SingleWireSerial_h */
