#include <SoftwareSerial.h>

SoftwareSerial mySerial(10, 11);

void setup()
{
    pinMode(2, OUTPUT);
    pinMode(3, OUTPUT);
    pinMode(4, OUTPUT);
    pinMode(5, OUTPUT);

    digitalWrite(2, LOW);
    digitalWrite(3, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    Serial.begin(9600);
    mySerial.begin(9600);
}

void loop()
{

    if (mySerial.available())
    {
        char r = mySerial.read();
        switch (r)
        {
        case '0':
            setmotor(0, 0, 0, 0);
            break;
        case '1':
            setmotor(0, 1, 0, 1);
            break;
        case '2':
            setmotor(0, 1, 0, 0);
            break;
        case '3':
            setmotor(0, 1, 1, 0);
            break;
        case '4':
            setmotor(0, 0, 1, 0);

            break;
        case '5':
            setmotor(1, 0, 1, 0);
            break;
        case '6':
            setmotor(1, 0, 0, 0);
            break;
        case '7':
            setmotor(1, 0, 0, 1);
            break;
        case '8':
            setmotor(0, 0, 0, 1);
            break;
        }
    }
}

void setmotor(boolean a, boolean b, boolean c, boolean d)
{
    digitalWrite(2, a);
    digitalWrite(3, b);
    digitalWrite(4, c);
    digitalWrite(5, d);
}