#include "Circle.h"
#include <math.h>
using namespace std;
#include <iostream>


 Circle::Circle()
{
    rad = 0;
    x= 0;
    y = 0;
}

Circle::Circle(double radius, double inputX, double inputY) : rad(radius), x(inputX), y(inputY)
{ }

double Circle::getRadius() {
    return rad;
}

void Circle::setRadius(double inputR) {
    rad = inputR;
}

double Circle::getArea() {
    return rad*rad*M_PI;
}

bool Circle::intersects(Circle* other) {
    return (  hypot ( x - other->x , y - other->y ) < rad + other->rad);
}





