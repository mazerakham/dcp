package dcp.hackerrank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class Student {
     private int id;
     public int getId() {
         return this.id;
     }
     
     private String name;
     public String getName() {
         return this.name;
     }
     
     private double cgpa;
     public double getCGPA() {
         return this.cgpa;
     }
     
     public Student(int id, String name, double cgpa) {
         this.id = id;
         this.name = name;
         this.cgpa = cgpa;
     }
 }
 
 class Priorities {
     private static final Comparator<Student> STUDENT_ORDER = (a,b) -> { 
         if (a.getCGPA() != b.getCGPA()) {
            return a.getCGPA() > b.getCGPA() ? -1 : 1;
         } else if (!a.getName().equals(b.getName())) {
            return a.getName().compareTo(b.getName());   
         } else {
            return a.getId() - b.getId();
         }
             
     };

     public List<Student> getStudents(List<String> events) {
         PriorityQueue<Student> queue = new PriorityQueue<Student>(events.size(), STUDENT_ORDER);
         for (String event : events) {
             String[] eventTokens = event.split(" ");
             if (eventTokens[0].equals("ENTER")) {
                 queue.add(parseStudent(event));
             } else {
                 Student servedStudent = queue.poll();
             }
         }
         
//         Student maria = parseStudent("ENTER Maria 3.6 46");
//         Student shafaet = parseStudent("ENTER SHAFEET 3.7 50");
//         int compResult = STUDENT_ORDER.compare(maria, shafaet); 
//         System.out.println("comp result: " + compResult);
         
         List<Student> ret = new ArrayList<>();
         Student s;
         while ((s = queue.poll()) != null) {
           ret.add(s);
         }
         return ret;
     }
     
     private static Student parseStudent(String enteredEvent) { 
         String[] tokens = enteredEvent.split(" ");
         assert tokens[0].equals("ENTER");
         return new Student(Integer.parseInt(tokens[3]), tokens[1], Double.parseDouble(tokens[2]));
     }
 }


public class PracticeProblem1 {
    private final static Scanner scan = new Scanner(System.in);
    private final static Priorities priorities = new Priorities();
    
    public static void main(String[] args) {
        int totalEvents = Integer.parseInt(scan.nextLine());    
        List<String> events = new ArrayList<>();
        
        while (totalEvents-- != 0) {
            String event = scan.nextLine();
            events.add(event);
        }
        
        List<Student> students = priorities.getStudents(events);
        
        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName());
            }
        }
    }
}