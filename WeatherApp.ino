#include <OneWire.h>
#include <DallasTemperature.h>

int const toggleBtn = 5;
int const STATUS_ONLINE = 4;
int const STATUS_OFFLINE = 3;
int const STATUS_MAINTENANCE = 2;

bool hasRun = false;
bool isOnline = false;
int lastSensor = 0;

float temp = 0.0;
int oneWireBus = 6;
OneWire oneWire(oneWireBus);
DallasTemperature sensors(&oneWire);

void setup() {
  Serial.begin(9600);
  sensors.begin();

  pinMode(4, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(2, OUTPUT);


}

void setStatus(char status[]) {
  if(status == "ONLINE") {
    digitalWrite(STATUS_ONLINE, HIGH);
    digitalWrite(STATUS_OFFLINE, LOW);
    digitalWrite(STATUS_MAINTENANCE, LOW);
    isOnline = true;

  }else if(status == "OFFLINE") {
    digitalWrite(STATUS_ONLINE, LOW);
    digitalWrite(STATUS_OFFLINE, HIGH);
    digitalWrite(STATUS_MAINTENANCE, LOW);
    isOnline = false;
    
  }else if(status == "MAINTENANCE") {
    digitalWrite(STATUS_ONLINE, LOW);
    digitalWrite(STATUS_OFFLINE, LOW);
    digitalWrite(STATUS_MAINTENANCE, HIGH);
  };
}

void loop() {
  if(digitalRead(toggleBtn) == HIGH) {
    if(!hasRun) {
      hasRun = true;
    }
    setStatus("ONLINE");

  }else {
    setStatus("OFFLINE");  
    hasRun = false;
  }
  if(!isOnline) return;

  // write code from here 

  sensors.requestTemperatures();
  temp = sensors.getTempCByIndex(0);
  
  Serial.println("T:" + String(temp, 2));
  delay(1000);

  int rainSensor = analogRead(A0);
  if(rainSensor < 1000) {
    int diff = floor(rainSensor / 100);

    if(lastSensor != diff) {
      Serial.println("R:" + String(diff));
      lastSensor = diff;
      delay(1000);
    }
    
  }

  

}
