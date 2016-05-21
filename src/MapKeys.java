import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lanev_000 on 21.05.2016.
 */
public class MapKeys {
    public static void main(String[] args) {
        Map<Company, Object> companies = new HashMap<>();

        Company c1 = new Company("sample");
        companies.put(c1, null);
        c1.addCustomer("the client");

        Company c2 = new Company("sample");
        companies.containsKey(c2); // don't really care
        c2.addCustomer("the client");

        Company c3 = new Company("sample");
        c3.addCustomer("the client");

        if (!c1.equals(c2) || !c1.equals(c3) || !c2.equals(c3))
            throw new IllegalStateException("companies must equal");
        System.out.println("contains c1: " + companies.containsKey(c1));
        System.out.println("contains c2: " + companies.containsKey(c2));
        System.out.println("contains c3: " + companies.containsKey(c3));

       Map<Person, Object> people = new HashMap<>();
        Person bob = new Person("bob", "the developer", 1234);
        people.put(bob, null);
        bob.setLastName("the manager");
        for (Person person : people.keySet()) {
            if (!people.containsKey(person))
                throw new IllegalStateException("should find all keys in keySet");
        }
    }
}

class Company {

    private String name;
    private List<String> customerNames = new ArrayList<>();

    //Class does not have constructor. But it is more logical to have company name before you get customers. Even if it is
    // a FIE, the company should have person's name.

    public Company(String name) {
        if (name != null) this.name = name;
        else throw new IllegalArgumentException("Company name cannot be null");
    }

    //Company can change its name, so getter and setter is ok, But it shouldn't let name to be null.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) this.name = name;
        else throw new IllegalArgumentException("Company name cannot be null");
    }

    //In is not necessary tha in the beginning company should have customers. The come and go.
    //But there is required method for customers removal.

    public void addCustomer(String name) {
        customerNames.add(name);
    }

    public void removeCustomer(String name){
        customerNames.remove(name);
    }

    //This method does not take into account all instances when comparing different objects.
    //Particularly it does't compare customer's lists.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;
        if (!name.equals(company.name)) return false;
        //---------------------------------------------------------------------------------------------------------
        if (customerNames != null ? !customerNames.equals(company.customerNames) : company.customerNames != null)
            return false;
        //---------------------------------------------------------------------------------------------------------
        return true;
    }

    //As with equals, the hachCode method is independent from customerNames.'

    @Override
    public int hashCode() {
        int result = 0;
        int nameHashCode = name.hashCode();
        //int customerHash = customerNames.hashCode();
        if (result == 0){
            result = 17;
            result = 31 * result + nameHashCode;
            //result = 31 * result + (customerNames != null ? customerHash : 0);
        }
        //System.out.println(result);
        return result;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }*/
}


class Newspaper {

    private final String name;
    private final int yearOfFounding;
    private final int registrationCode;

    public Newspaper(String name, int yearOfFounding, int registrationCode) {
        if (name != null) this.name = name;
        else throw new IllegalArgumentException("Company name cannot be null");
        this.registrationCode = registrationCode;
        this.yearOfFounding = yearOfFounding;
    }

    public int getRegistrationCode() {
        return registrationCode;
    }

    public int getYearOfFounding() {
        return yearOfFounding;
    }

    public String getName() {
        return name;
    }

    //Not all nulls checked as well as instances.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Newspaper newspaper = (Newspaper) o;
        //---------------------------------------------------------------------------------------------------------
        if (name.equals(newspaper.name)) return false;
        if (yearOfFounding == newspaper.yearOfFounding) return false;
        if (registrationCode == newspaper.registrationCode) return false;
        //---------------------------------------------------------------------------------------------------------
        /*/if (registrationCode != newspaper.registrationCode) return false;
        return name != null ? name.equals(newspaper.name) : newspaper.name == null;*/
        return true;
    }

    //HashCode method seems to be quite good.
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + yearOfFounding;
        result = 31 * result + registrationCode;
        return result;
    }
}

class Person {

    private String firstName;
    private String lastName;
    private final int idCode;

    public Person(String firstName, String lastName, int idCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idCode = idCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //Comparison insufficient, as class has more instances. Although it is quite logical that there aren't any persons
    //with the same idCode. at least if we talk about passports. Nevertheless lets assume, that idCode isn't unique.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Person person = (Person) o;
        //---------------------------------------------------------------------------------------------------------
        if (!firstName.equals(person.firstName)) return false;
        if (!lastName.equals(person.lastName)) return false;
        //---------------------------------------------------------------------------------------------------------
        return idCode == person.idCode;
    }

    //That's one crap hashCode that produces very degenerate hashCodes wich take only values of 0,1,2,3!
    @Override
    /*public int hashCode() {
        return idCode % 4;
    }*/
    //This one should be better.
    //ID is used as it should be probably most constant parameter of the object.
    public int hashCode() {
        int result = 0;
        result = 31 * result + idCode;
        return result;
    }
}