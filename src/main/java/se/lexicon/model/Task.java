package se.lexicon.model;

public class Task {

  private Integer id;
  private String title;
  private String description;
  private Person person;

  public Task(String title, String description, Person person) {
    this.title = title;
    this.description = description;
    this.person = person;
  }

  public Task(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public Task(Integer id, String title, String description, Person person) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.person = person;
  }

  public Task(Integer id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @Override
  public String toString() {
    return "Task{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", person=" + person +
            '}';
  }
}
