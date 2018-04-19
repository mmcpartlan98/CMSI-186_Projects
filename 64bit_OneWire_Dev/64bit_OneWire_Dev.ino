int bitDelay = 115;
//int bitDelay = 31;
int transmit = 3;
int dataArray[64] = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                      
int arrayToWrite[63] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
                          27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 
                          49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64};

void updatePins() {
  int runThrough = 0;
  for (int i = 0; i <= 31; i++) {
    if (dataArray[i] == 1) {
      digitalWrite(transmit, HIGH);
    } else {
      digitalWrite(transmit, LOW);
    }
    runThrough++;
    if (runThrough == 1) {
      delayMicroseconds(bitDelay - (bitDelay * 0.6)); // Configured for
      //delay(bitDelay - (bitDelay * 0.6));
    } else {
      delayMicroseconds(bitDelay + (bitDelay * 0.0185)); //delay
      //delay(bitDelay + (bitDelay * 0.0185));
    }
  }
  digitalWrite(transmit, LOW);
  delayMicroseconds(250);
}

void digitalWriteExp(int pinNumber, uint8_t binVal) {
  if (binVal == HIGH) {
    dataArray[pinNumber - 1] = 1;
  } else {
    dataArray[pinNumber - 1] = 0;
  }
  updatePins();
}

void wireBegin(int pinOut) {
  transmit = pinOut;
  pinMode(transmit, OUTPUT);
  for (int i = 1; i <= 31; i++) {
    digitalWriteExp(i, LOW);
  }
  delayMicroseconds(1000);
}

void analogReader(int pinIn) {
  int buttonRead = analogRead(pinIn);
  while (buttonRead >= 100) {
    buttonRead = analogRead(pinIn);
    delay(10);
  }
}

void setup() {
  // put your setup code here, to run once:
  //wireBegin(transmit);
  Serial.begin(9600);
  pinMode(transmit, OUTPUT);
  digitalWriteExp(3, HIGH);
  delay(1500);
  digitalWriteExp(3, LOW);
  delay(1500);

}

void loop() {
  for (int i = 62; i >= 0; i -= 1) {
    int randInt = random(0, i);
    int temp = arrayToWrite[i];
    arrayToWrite[i] = arrayToWrite[randInt];
    arrayToWrite[randInt] = temp;
  }

  for (int i = 0; i <= 62; i += 1) {
    digitalWriteExp(arrayToWrite[i], HIGH);
    delay(400);
  }

  for (int i = 62; i >= 0; i -= 1) {
    int randInt = random(0, i);
    int temp = arrayToWrite[i];
    arrayToWrite[i] = arrayToWrite[randInt];
    arrayToWrite[randInt] = temp;
  }

  for (int i = 62; i >= 0; i -= 1) {
    digitalWriteExp(arrayToWrite[i], LOW);
    delay(400);
  }

}
