#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

#include "a3object.h"
#include "a3collection.h"
#include "a3comparable.h"
#include "a3comparator.h"
#include "a3iterator.h"
#include "a3list.h"
#include "a3course.h"
#include "a3student.h"

static const char SEPARATOR = ',';

int main(int argc, char *argv[]) {
  if (argc < 2) {
    std::cout << "usage: " << argv[0] << " inputfile.txt\n";
    std::exit(0);
  }

  CourseListByName *courses = new CourseListByName;
  StudentListByName *students = new StudentListByName;
  std::string line;
  std::ifstream input(argv[1]);
  
  while (std::getline(input, line)) {
    std::istringstream stream(line);
    int number;
    std::string token, name;
    std::getline(stream, token, SEPARATOR);
    
    if (token == "course") {
      std::getline(stream, token, SEPARATOR);
      number = std::stoi(token);
      std::getline(stream, name, SEPARATOR);
      courses->insert(new Course(number, name));
    } else if (token == "student") {
      std::getline(stream, token, SEPARATOR);
      number = std::stoi(token);
      std::getline(stream, name, SEPARATOR);
      Student *student = new Student(number, name);
    
      while (std::getline(stream, token, SEPARATOR)) {
        int course_number = std::stoi(token);

        Course *course = courses->find_by_number(course_number);
        if (nullptr == course) {
          std::cout << "Unable to find course " << course_number << " when reading student " << number << std::endl;
        } else {
          student->add_course(course);
          course->add_student(student);
        }
      }

      students->insert(student);

    } else if (token == "drop") {
      std::getline(stream, token, SEPARATOR);
      number = std::stoi(token);
      std::getline(stream, token, SEPARATOR);
      int course_number = std::stoi(token);
      
      Student *student = students->find_by_number(number);
      if (nullptr == student) {
        std::cout << "Unable to find student " << number << " to drop course " << course_number << std::endl;
      } else {
        Course *course = courses->find_by_number(course_number);
        if (nullptr == course) {
          std::cout << "Unable to find course " << course_number << " to drop from student " << number << std::endl;
        } else {
          if (!student->drop_course(course)) {
            std::cout << "Unable to drop student " << number << " from course " << course_number << " because they are not registered.\n";
          }
        }
      }
    } else if (token == "cancel") {
      std::getline(stream, token, SEPARATOR);
      number = std::stoi(token);
      
      Course *course = courses->find_by_number(number);
      if (nullptr == course) {
        std::cout << "Unable to cancel non-existing course " << number << std::endl;
      } else {
        course->cancel();
        courses->remove(course);
        delete course;
      }
    }
  }

  Iterator *i;

  std::cout << "\nCourses:\n";
  std::cout << "---------\n\n";
  
  i = courses->start();
  while (i->has_next()) {
    Course *course = dynamic_cast<Course *>(i->next());
    std::cout << course->to_string() << std::endl;
  }
  courses->finish(i);

  std::cout << "\nStudents:\n";
  std::cout << "---------\n\n";
  
  i = students->start();
  while (i->has_next()) {
    Student *student = dynamic_cast<Student *>(i->next());
    std::cout << student->to_string() << std::endl;
  }
  students->finish(i);

  i = courses->start();
  while (i->has_next()) {
    // dynamic_cast<Course *>(data)->cancel();
    delete i->next();
  }
  courses->finish(i);
  
  delete courses;

  i = students->start();
  while (i->has_next()) {
    delete i->next();
  }
  students->finish(i);
  
  delete students;
  
  // This should be the last line of your program
  std::cout << "\nNumber of objects leaked: " << A3Object::countInstances() << std::endl;
}
