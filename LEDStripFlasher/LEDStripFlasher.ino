int brightness[3] = {1, 1, 1};
int flasherDirection = 1;
int theFlash = 0;
void setup() {
  // put your setup code here, to run once
analogWrite(3, brightness[0]);
analogWrite(9, brightness[1]);
analogWrite(10, brightness[2]);
}

void loop() {
  // put your main code here, to run repeatedly:
  
  while (theFlash <=2) {
    if (flasherDirection == 0) {
        if (brightness[theFlash] > 1) {
          brightness[theFlash] -= 1;
        } else {
          flasherDirection = 1;
          theFlash++;
        }

      } else if (flasherDirection == 1) {
        if (brightness[theFlash] < 255) {
          brightness[theFlash] += 1;
        } else {
          flasherDirection = 0;
        }
      }
    analogWrite(3, brightness[0]);
    analogWrite(9, brightness[1]);
    analogWrite(10, brightness[2]);
    delay(10);
  }
  if (theFlash == 2) {
    theFlash = 0;
  } else {
    theFlash++;
  }
}
