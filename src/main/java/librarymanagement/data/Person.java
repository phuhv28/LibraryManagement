package librarymanagement.data;

class Person {
    private String name;
    private String bio;

    public Person() {}

    public Person(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getInfo() {
        return "Name: " + name + "\n" +
                "Bio: " + bio;
    }
}

