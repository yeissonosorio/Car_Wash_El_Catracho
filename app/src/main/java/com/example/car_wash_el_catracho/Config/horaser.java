package com.example.car_wash_el_catracho.Config;

public class horaser {
    public horaser() {
    }

    public int hora(int Hora){
        int horaConver=0;

        if (Hora==1){
            horaConver=8;
        } else if (Hora==2) {
            horaConver=9;
        }
        else if (Hora==3) {
            horaConver=10;
        }
        else if (Hora==4) {
            horaConver=11;
        }
        else if (Hora==5) {
            horaConver=13;
        }
        else if (Hora==6) {
            horaConver=14;
        }
        else if (Hora==7) {
            horaConver=15;
        }
        else if (Hora==8) {
            horaConver=16;
        }
        else if (Hora==9) {
            horaConver=17;
        }
        return horaConver;
    }
}
