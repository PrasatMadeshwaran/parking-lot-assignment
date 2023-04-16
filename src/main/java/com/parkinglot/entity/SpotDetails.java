package com.parkinglot.entity;

import com.parkinglot.enums.Spot;
import lombok.Value;

@Value
public class SpotDetails {
    Spot spot;
    int spotNumber;
}
