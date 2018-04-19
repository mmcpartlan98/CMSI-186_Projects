#include <SingleWireSerial.h>

int transmit = 3;
int arrayToWrite[32];

// Call constructor from library file
// SingleWireSerial SWS(int TRANSMIT_PIN, int NUMBER_OF_BITS)
SingleWireSerial SWS(transmit, 4);

void setup() {
  // Generates order array for random light sequence
  for (int i = 0; i < 31; i++) {
    arrayToWrite[i] = i + 1;
  }
}

void loop() {
    // Controls light order in various patterns
    // Randomizes pin ON order
    for (int i = 31; i >= 0; i -= 1) {
      int randInt = random(0, i);
      int temp = arrayToWrite[i];
      arrayToWrite[i] = arrayToWrite[randInt];
      arrayToWrite[randInt] = temp;
    }
    // Turns pins ON in order
    for (int i = 0; i < 16; i += 1) {
      SWS.digitalWrite(arrayToWrite[i], HIGH);
      delay(100);
    }
    // Randomizes pin OFF order
    for (int i = 31; i >= 0; i -= 1) {
      int randInt = random(0, i);
      int temp = arrayToWrite[i];
      arrayToWrite[i] = arrayToWrite[randInt];
      arrayToWrite[randInt] = temp;
    }
    // Turns pins OFF in order
    for (int i = 31; i > 0; i -= 1) {
      SWS.digitalWrite(arrayToWrite[i], LOW);
      delay(100);
    }
    // Scroll up with ON
    for (int i = 1; i < 32; i++) {
      SWS.digitalWrite(i, HIGH);
      delay(100);
    } 
    // Scroll up with OFF
    for (int i = 1; i < 32; i++) {
      SWS.digitalWrite(i, LOW);
      delay(100);
    }
    delay(500);
    // Scroll down with ON
    for (int i = 31; i > 0; i--) {
      SWS.digitalWrite(i, HIGH);
      delay(100);
    }
    // Scroll down with OFF
    for (int i = 31
    ; i > 0; i--) {
      SWS.digitalWrite(i, LOW);
      delay(100);
    }
    delay(500);
}
