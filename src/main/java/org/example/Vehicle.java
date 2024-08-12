package org.example;
import java.util.ArrayList;
import java.util.List;
public class Vehicle {
        private final String type;
        private final String number;

        public Vehicle(String type, String number) {
            this.type = type;
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public String getNumber() {
            return number;
        }
    }
