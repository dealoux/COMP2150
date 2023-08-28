#include <string>

#include "a3comparable.h"
#include "a3list.h"

class CourseListByNumber;
class Course;
class Student : public Comparable {
private:
  int number;
  std::string name;
  CourseListByNumber *courses;
public:
  Student(int number, std::string name);
  int compareTo(Comparable *other);
  int get_number();
  std::string get_name();
  void add_course(Course *course);
  bool drop_course(Course *course);
  std::string to_string();
  ~Student();
};

class StudentListByNumber : public A3List {
public:
  void insert(Comparable *course);
};

class CompareStudentsByName;
class StudentListByName : public A3List {
private:
  CompareStudentsByName *comparator;
public:
  StudentListByName();
  void insert(Comparable *student);
  Student *find_by_number(int number);
  ~StudentListByName();
};
