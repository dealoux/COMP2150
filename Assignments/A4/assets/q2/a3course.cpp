#include "a3course.h"
#include "a3student.h"

Course::Course(int number, std::string name) {
  this->number = number;
  this->name = name;

  students = new StudentListByNumber();
}

int Course::compareTo(Comparable *other) {
  return number - dynamic_cast<Course *>(other)->number;
}

int Course::get_number() {
  return number;
}

std::string Course::get_name() {
  return name;
}

void Course::add_student(Student *student) {
  students->insert(student);
}

void Course::cancel() {
  Iterator *i = students->start();
  while (i->has_next()) {
    Student *student = dynamic_cast<Student *>(i->next());
    student->drop_course(this);
  }
  students->finish(i);
}

std::string Course::to_string() {
  std::string result = std::to_string(number) + " " + name + " has students:";
  
  Iterator *i = students->start();
  while (i->has_next()) {
    Student *student = dynamic_cast<Student *>(i->next());
    result += " " + std::to_string(student->get_number());
  }
  students->finish(i);
  
  return result;
}

Course::~Course() {
  delete students;
}

void CourseListByNumber::insert(Comparable *course) {
  insert_natural(course);
}

class CompareCoursesByName : public Comparator {
public:
  int compare(Comparable *a, Comparable *b);
  ~CompareCoursesByName();
};

int CompareCoursesByName::compare(Comparable *a, Comparable *b) {
  return dynamic_cast<Course *>(a)->get_name().compare(dynamic_cast<Course *>(b)->get_name());
}

CompareCoursesByName::~CompareCoursesByName() {
}

CourseListByName::CourseListByName() {
  comparator = new CompareCoursesByName();
}

void CourseListByName::insert(Comparable *course) {
  insert_comparator(course, comparator);
}

Course *CourseListByName::find_by_number(int number) {
  Course *match = nullptr;
  Iterator *i = start();
  while (i->has_next() && nullptr == match) {
    Course *curr = dynamic_cast<Course *>(i->next());
    if (curr->get_number() == number) {
      match = curr;
    }
  }
  finish(i);
  return match;
}

CourseListByName::~CourseListByName() {
  delete comparator;
}
