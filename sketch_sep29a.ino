#include <SoftwareSerial.h>

SoftwareSerial Serial1(10, 11);
int ledPinArriba = 2;
int ledPinAbajo = 3;
int ledPinDerecha = 4;
int ledPinIzquierda = 5;
int ledPinSeleccionar = 6;
int buttonLED = 8; // Nuevo pin para el LED adicional.

void setup() {
  Serial1.begin(9600);
  
  pinMode(ledPinArriba, OUTPUT);
  pinMode(ledPinAbajo, OUTPUT);
  pinMode(ledPinDerecha, OUTPUT);
  pinMode(ledPinIzquierda, OUTPUT);
  pinMode(ledPinSeleccionar, OUTPUT);
  pinMode(buttonLED, OUTPUT); // Nuevo LED para indicar que se presionó cualquier botón.

  pinMode(7, OUTPUT); // LED general para indicar ejecución general.
  
  pinMode(2, INPUT_PULLUP);
  pinMode(3, INPUT_PULLUP);
  pinMode(4, INPUT_PULLUP);
  pinMode(5, INPUT_PULLUP);
  pinMode(6, INPUT_PULLUP);
}

void blinkLED(int pin) {
  digitalWrite(pin, HIGH);
  delay(500);
  digitalWrite(pin, LOW);
}

void loop() {
  if (digitalRead(2) == LOW) {
    Serial1.println("Arriba");
    blinkLED(ledPinArriba);
    digitalWrite(buttonLED, HIGH); // Enciende el LED de botón.
    digitalWrite(7, HIGH); // Enciende el LED general.
  }

  if (digitalRead(3) == LOW) {
    Serial1.println("Abajo");
    blinkLED(ledPinAbajo);
    digitalWrite(buttonLED, HIGH);
    digitalWrite(7, HIGH);
  }

  if (digitalRead(4) == LOW) {
    Serial1.println("Derecha");
    blinkLED(ledPinDerecha);
    digitalWrite(buttonLED, HIGH);
    digitalWrite(7, HIGH);
  }

  if (digitalRead(5) == LOW) {
    Serial1.println("Izquierda");
    blinkLED(ledPinIzquierda);
    digitalWrite(buttonLED, HIGH);
    digitalWrite(7, HIGH);
  }

  if (digitalRead(6) == LOW) {
    Serial1.println("Seleccionar");
    blinkLED(ledPinSeleccionar);
    digitalWrite(buttonLED, HIGH);
    digitalWrite(7, HIGH);
  }

  digitalWrite(buttonLED, LOW); // Apaga el LED de botón.
  digitalWrite(7, LOW); // Apaga el LED general.
}
