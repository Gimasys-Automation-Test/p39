public class Person {
    String firstName;
    String lastName;
    Double due;

    public Person(String firstName, String lastName, Double due) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.due = due;
    }


    public String getFullName() {
        return String.format("%s %s",this.firstName,this.lastName);
    }

    public Double getDue() {
        return due;
    }


}


