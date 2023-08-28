public class midterm {
    class Student{
        protected int stuID;
        protected double gpa;

        public Student(int id, double gpa){
            stuID = id;
            this.gpa = gpa;
        }

        // Accessors
        public int getStuID() {
            return stuID;
        }

        public double getGpa() {
            return gpa;
        }

        // Setters
        public void setGpa(double gpa) {
            this.gpa = gpa;
        }

        public void setStuID(int stuID) {
            this.stuID = stuID;
        }

        public void print(){
            System.out.println("Student ID: " + stuID);
            System.out.println("GPA: " + gpa);
        }
    }

    class UndergradStudent extends Student{
        private int completedCourses;
        private String major;

        public UndergradStudent(int id, double gpa, int completedCourses, String major){
            super(id, gpa);
            this.completedCourses = completedCourses;
            this.major = major;
        }

        // Accessors
        public int getCompletedCourses() {
            return completedCourses;
        }

        public String getMajor() {
            return major;
        }

        // Setters
        public void setCompletedCourses(int completedCourses) {
            this.completedCourses = completedCourses;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        @Override
        public void print(){
            super.print();
            System.out.println("Number of completed courses: " + completedCourses);
            System.out.println("Current major: " + major);
        }
    }

    class GradStudent extends Student{
        private int publications;
        private String labName;

        public GradStudent(int id, double gpa, int publications, String labName){
            super(id, gpa);
            this.publications = publications;
            this.labName = labName;
        }

        // Accessors
        public int getPublications() {
            return publications;
        }

        public String getLabName() {
            return labName;
        }

        // Setters
        public void setPublications(int publications) {
            this.publications = publications;
        }

        public void setLabName(String labName) {
            this.labName = labName;
        }

        @Override
        public void print(){
            super.print();
            System.out.println("Number of publications: " + publications);
            System.out.println("Research lab: " + labName);
        }
    }
}
