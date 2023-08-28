/**
* LeTomA2Q2.cpp
*
* COMP 2150 SECTION A01
* INSTRUCTOR Riley Wall
* ASSIGNMENT Assignment 2, question 2
* @author Tom Le, 7871324
* @version July 3rd 2022
*
* REMARKS: implementation of the Person/Student/Employee/Instructor/Administrator hierarchy of classes
*/

#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <algorithm>
#include <list> // include list for Student class

using namespace std;

static const char SEPARATOR = ',';

class A2ListItem {
private:
  static int instances;
public:
  A2ListItem() {
    instances++;
  }
  virtual int get_id() = 0;
  virtual void print() = 0;
  virtual void print_all() = 0;
  virtual ~A2ListItem() {
    instances--;
  }
  static int countInstances() {
    return instances;
  }
};
int A2ListItem::instances = 0;

class A2List {
private:
  std::vector<A2ListItem *> data;
  static bool compareItems(A2ListItem *a, A2ListItem *b) {
    return a->get_id() < b->get_id();
  }
  bool sorted;
  void sort() {
    std::sort(data.begin(), data.end(), compareItems);
    sorted = true;
  }
protected:
  void destroy_contents() {
    for (auto i = data.begin(); i != data.end(); ++i) {
      delete (*i);
    }
  }
public:
  A2List() {
    sorted = true;
  }
  void add(A2ListItem *value) {
    data.push_back(value);
    sorted = false;
  }
  A2ListItem *find_by_key(int key) {
    auto result = find_if(data.begin(), data.end(),
      [key](A2ListItem *item) {
        return item->get_id() == key;
      }
    );
    if (result == data.end())
      return nullptr;
    else
      return *result;
  }
  void print() {
    if (!sorted)
      sort();
    for (auto i = data.begin(); i != data.end(); ++i) {
      (*i)->print();
    }
  }
  void print_all() {
    if (!sorted)
      sort();
    for (auto i = data.begin(); i != data.end(); ++i) {
      (*i)->print_all();
    }
  }
  virtual ~A2List() = 0;
};
A2List::~A2List() {}

/*** Your classes go here ***/
class A2ListMemoryFree : public A2List{
  public:
    ~A2ListMemoryFree(){
      destroy_contents();
    }
}; // end of A2ListMemoryFree class

class A2ListMemoryLeak : public A2List{
  public:
    ~A2ListMemoryLeak(){
      cout << "No objects were deleted, possible memory leak" << endl;
    }
}; // end of A2ListMemoryLeak class

// Ultilities base class for A2ListItem child classes
class BaseListItem : public A2ListItem{
  private:
    int id;
    string name;

  protected:
    void print_helper(const string& title){
      cout << title << " id: " << id << ", name: " << name << endl;
    }

  public:
    int get_id() { return id; }
    string get_name() { return name; }

    void set_id(int id) { this->id = id; }
    void set_name(string& name) { this->name = move(name); }

    void print(){ 
      cout << id << ", " << name << endl; 
    }

    void print_all(){ print_helper("Item"); }
}; // end of BaseListItem class

class Person : public BaseListItem{
  public:
    void print_all(){ print_helper("Person"); }
}; // end of Person abstract class

class Course : public BaseListItem{
  private:
    A2ListMemoryFree *students;

  public:
    Course() : BaseListItem(){
      students = new A2ListMemoryFree();
    }

    ~Course(){
      //free(students);
      delete students;
    }

    A2ListMemoryFree *get_students(){ return students; }

    void print(){ 
      BaseListItem::print();
      // cout << "List of students:" << endl;
      // students->print();
    }

    void print_all(){ 
      print_helper("Course"); 
      cout << "List of students detailed:" << endl;
      students->print_all();
    }
}; // end of Course class

class Student : public Person{
    private:
    vector<int> *courses_registered;

    void print_courses(){
      cout << "List of courses number:" << endl;

      for(int i : *courses_registered){
        cout << i << " ";
      }

      cout << endl;
    }

  public:
    Student() : Person(){
      courses_registered = new vector<int>();
    }

    ~Student(){
      // free(courses_registered);
      delete courses_registered;
    }

    vector<int>* get_courses(){
      return courses_registered;
    }

    void print(){ 
      BaseListItem::print();
      print_courses();
    }

    void print_all(){ 
      print_helper("Student");
      print_courses();
    }
}; // end of Student class

class Employee : public Person{
  protected:
    virtual int weekly_pay() { return 0; };

  public:
    void print_all(){ print_helper("Employee"); }
}; // end of Employee abstract class

class Instructor : public Employee{
  private:
    A2ListMemoryFree *courses_teaching;
    int annual_salary;

  public:
    Instructor() : Employee(){
      courses_teaching = new A2ListMemoryFree();
    }

    ~Instructor(){
      delete courses_teaching;
      //free(courses_teaching);
    }

    A2ListMemoryFree *get_courses(){ return courses_teaching; }
    int get_salary() { return annual_salary; }
    int weekly_pay(){ return annual_salary/52; }

    void  set_salary(int salary) { annual_salary = salary; }
    
    void print(){ 
      BaseListItem::print();
      cout << annual_salary << ", " << weekly_pay() << endl;
      cout << "List of teaching courses:" << endl;
      courses_teaching->print();
    }

    void print_all(){ 
      print_helper("Instructor");
      cout << "Annual salary: " << annual_salary << ", Weekly pay: " << weekly_pay() << endl;
      cout << "List of teaching courses detailed:" << endl;
      courses_teaching->print_all();
    }
}; // end of Instructor class

class Administrator : public Employee{
  private:
    int wage;
    int hours;

  public:
    // Administrator() : Employee(){
    //   wage = 0;
    //   hours = 0;
    // }

    int get_wage() { return wage; }
    int get_hours() { return hours; }
    int weekly_pay(){ return wage*hours; }

    void set_wage(int wage) { this->wage = wage; }
    void set_hours(int hours) { this->hours = hours; }

    void print(){ 
      BaseListItem::print();
      cout << wage << ", " << hours << ", " << weekly_pay() << endl;
    }

    void print_all(){ 
      print_helper("Administrator"); 
      cout << "Hourly wage: " << wage << ", Hours per week: " << hours << ", Weekly pay: " << weekly_pay() << endl;
    }
}; // end of Administrator class

// modify the main program as necessary
int main(int argc, char *argv[]) {
  A2ListMemoryFree *student_list = new A2ListMemoryFree();
  A2ListMemoryFree *course_list = new A2ListMemoryFree();
  A2ListMemoryFree *instructor_list = new A2ListMemoryFree();
  A2ListMemoryFree *admin_list = new A2ListMemoryFree();

  if (argc < 2) {
    cout << "usage: " << argv[0] << " inputfile.txt\n";
    exit(0);
  }

  string line;
  ifstream input(argv[1]);
  
  while (getline(input, line)) {
    istringstream stream(line);
    int number;
    string token;
    getline(stream, token, SEPARATOR);

    // do not print any output until you've read everything in!
    if (token == "student") {
      Student *stu = new Student();

      // id number
      getline(stream, token, SEPARATOR);
      stu->set_id(stoi(token)); 

      // name
      getline(stream, token, SEPARATOR);
      // stu->name = token;
      stu->set_name(token);

      // adding courses
      while (getline(stream, token, SEPARATOR)) {
        int key = stoi(token);

        stu->get_courses()->push_back(key);

        // add to course
        if(Course* c = dynamic_cast<Course*>(course_list->find_by_key(key))){
          // Student* temp = new Student(*stu);
          // c->get_students()->add(temp);
          c->get_students()->add(stu);
        }
      }

      student_list->add(stu);
    } else if (token == "course"){
      Course *course = new Course();

      // id number
      getline(stream, token, SEPARATOR);
      course->set_id(stoi(token)); 

      // name
      getline(stream, token, SEPARATOR);
      course->set_name(token);

      course_list->add(course);
    } else if (token == "instructor"){
      Instructor *instructor = new Instructor();

      // id number
      getline(stream, token, SEPARATOR);
      instructor->set_id(stoi(token)); 

      // name
      getline(stream, token, SEPARATOR);
      instructor->set_name(token);

      // salary
      getline(stream, token, SEPARATOR);
      instructor->set_salary(stoi(token));

      // adding courses
      while (getline(stream, token, SEPARATOR)) {
        if(Course* c = dynamic_cast<Course*>(course_list->find_by_key(stoi(token)))){
          // Course *temp = new Course(*c);
          // instructor->get_courses()->add(temp);
          instructor->get_courses()->add(c);
        }
      }

      instructor_list->add(instructor);
    } else if (token == "administrator"){
      Administrator *admin = new Administrator();

       // id number
      getline(stream, token, SEPARATOR);
      admin->set_id(stoi(token)); 

      // name
      getline(stream, token, SEPARATOR);
      admin->set_name(token);

      // wage
      getline(stream, token, SEPARATOR);
      admin->set_wage(stoi(token));

      // hours
      getline(stream, token, SEPARATOR);
      admin->set_hours(stoi(token));

      admin_list->add(admin);
    }

    // print everything
    
    // course_list->print();
    // student_list->print();
    instructor_list->print();
    // admin_list->print();

    // course_list->print_all();
    // student_list->print_all();
    // instructor_list->print_all();
    // admin_list->print_all();
  }

  
  delete student_list;
  delete admin_list;
  // delete course_list;
  // delete instructor_list;
  
  if(A2ListItem::countInstances() == 0)
    cout << "Objects were freed properly, no memory leak" << endl;

  // This should be the last line of your program
  cout << "\nNumber of objects leaked: " << A2ListItem::countInstances() << endl;
}