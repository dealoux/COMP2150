
#pragma once // Header/Include  Guard -> replaces #ifndef stmts


class Circle {
private:
    double rad;
    double x;
    double y;


public:
    Circle();
    Circle(double, double, double); // set up circle with radius, centre
    double getArea();
    double getRadius();
    void setRadius(double);
    bool intersects(Circle*);
};


