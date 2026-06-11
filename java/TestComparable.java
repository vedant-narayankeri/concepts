package java;

import java.util.Comparator;

// Comparable - Natural ordering built into the class

public class TestComparable implements Comparable<TestComparable>{
    String name;
    int score;

    @Override
    public int compareTo(TestComparable other){
        return Integer.compare(this.score, other.score);
    }
}

//Usage:
/**
 * List<Student> students = new ArrayList<>();
 * Collections.sort(students);// uses compareTo method for sorting
 * TreeMap<Student, String> map = new TreeMap<>();// uses compareTo method internally for sorting again
*/

// Comparator - Custom/External ordering outside the class

Comparator<Student> byName = Comparator.comparing(s -> s.name);

/**
 * Usage:
 * students.sort(byName);
 * Collections.sort(students, byName);
 * new TreeMap<>(byName);
*/

