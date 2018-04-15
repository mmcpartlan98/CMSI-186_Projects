//
//  SingleWireSerial.h
//  SingleWireSerial
//
//  Created by Matt Mcpartlan on 9/5/17.
//  Copyright © 2017 Matt Mcpartlan. All rights reserved.
//

#ifndef Shift_h
#define Shift_h

#include "Arduino.h"

class Shift {
public:
    Shift(int SER, int SRCLK, int RCLK);
    void begin();
    void updatePins();
    void updateArray(int arrayIndex, int state);
    void shiftWrite(int arrayIndex, int state);
private:
    int _SER;
    int _RCLK;
    int _SRCLK;
    int _dataArray[16] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
};

#endif /* SingleWireSerial_h */
