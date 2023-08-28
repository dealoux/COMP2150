#include <string>

#include "a3comparable.h"
#include "a3list.h"

class StudentListByNumber;
class Student;
class Course : public Comparable {
private:
  int number;
  std::string name;
  StudentListByNumber *students;
public:
  Course(int number, std::string name);
  int compareTo(Comparable *other);
  int get_number();
  std::string get_name();
  void add_student(Student *student);
  void cancel();
  std::string to_string();
  ~Course();
};

class CourseListByNumber : public A3List {
public:
  void insert(Comparable *course);
};

class CompareCoursesByName;
class CourseListByName : public A3List {
private:
  CompareCoursesByName *comparator;
public:
  CourseListByName();
  void insert(Comparable *course);
  Course *find_by_number(int number);
  ~CourseListByName();
};
