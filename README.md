# Weather App Arduino Code & Java

### This project is a part from the complete project Weather Reading from Sensor made by [Poienariu Vlad](https://www.linkedin.com/in/poienariu-vlad/).
---

![Java](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXwGgGqRwXje3GuBu6ZpUxyrm5PBf3euNGfw&s)
![Arduion](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_RfCfQNLgLxSltAMn6N3AyGfsF0vNRScauQ&s)

---

# Wire connection
The Sensors connection is being made using [Arduino Uno](https://docs.arduino.cc/hardware/uno-rev3/) and a breadboard for connecting the sensors and certain LEDs. Check the below image for reference

![Poienariu Vlad](https://github.com/user-attachments/assets/cb7a7f11-7545-48b3-b9d4-5bd4684d5fec)


# Using java
To read the data from the Arduino board from Java, I've read the USB port data, on a rate of 9600 ( same rate as the arduino transmists ), and after reaing the data I've send it over to the API using Http Requests.


