#include "a3student.h"
#include "a3course.h"

Student::Student(int number, std::string name) {
  this->number = number;
  this->name = name;

  courses = new CourseListByNumber();
}

int Student::compareTo(Comparable *other) {
  return number - dynamic_cast<Student *>(other)->number;
}

int Student::get_number() {
  return number;
}

std::string Student::get_name() {
  return name;
}

void Student::add_course(Course *course) {
  courses->insert(course);
}

bool Student::drop_course(Course *course) {
  return nullptr != courses->remove(course);
}

std::string Student::to_string() {
  std::string result = std::to_string(number) + " " + name + " is taking courses:";
  
  Iterator *i = courses->start();
  while (i->has_next()) {
    Course *course = dynamic_cast<Course *>(i->next());
    result += " " + std::to_string(course->get_number());
  }
  courses->finish(i);
  
  return result;
}

Student::~Student() {
  delete courses;
}

void StudentListByNumber::insert(Comparable *course) {
  insert_natural(course);
}

class CompareStudentsByName : public Comparator {
public:
  int compare(Comparable *a, Comparable *b);
  ~CompareStudentsByName();
};

int CompareStudentsByName::compare(Comparable *a, Comparable *b) {
  return dynamic_cast<Student *>(a)->get_name().compare(dynamic_cast<Student *>(b)->get_name());
}

CompareStudentsByName::~CompareStudentsByName() {
}

StudentListByName::StudentListByName() {
  comparator = new CompareStudentsByName();
}

void StudentListByName::insert(Comparable *student) {
  insert_comparator(student, comparator);
}

Student *StudentListByName::find_by_number(int number) {
  Student *match = nullptr;
  Iterator *i = start();
  while (i->has_next() && nullptr == match) {
    Student *curr = dynamic_cast<Student *>(i->next());
    if (curr->get_number() == number) {
      match = curr;
    }
  }
  finish(i);
  return match;
}

StudentListByName::~StudentListByName() {
  delete comparator;
}
