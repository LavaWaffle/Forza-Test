package frc.robot;

public class RomiUtils {
  public static double round(double number, int decimalPlace) {
    double powerOfTen = 1;
    while(decimalPlace-- > 0) {
      powerOfTen *= 10.0;
    }    
    return Math.round(number * powerOfTen) / powerOfTen;
  }
}