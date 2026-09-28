package mentalhealth;

// simple bean, lowercase getters still work with jsp el (${p.name}, ${p.id} ...)
public class patient {

    private String id;
    private String name;
    private int age;
    private String gender;
    private String mood;
    private String severity;
    private String symptoms;
    private String status;

    public String getid() { return id; }
    public void setid(String id) { this.id = id; }

    public String getname() { return name; }
    public void setname(String name) { this.name = name; }

    public int getage() { return age; }
    public void setage(int age) { this.age = age; }

    public String getgender() { return gender; }
    public void setgender(String gender) { this.gender = gender; }

    public String getmood() { return mood; }
    public void setmood(String mood) { this.mood = mood; }

    public String getseverity() { return severity; }
    public void setseverity(String severity) { this.severity = severity; }

    public String getsymptoms() { return symptoms; }
    public void setsymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getstatus() { return status; }
    public void setstatus(String status) { this.status = status; }
}
