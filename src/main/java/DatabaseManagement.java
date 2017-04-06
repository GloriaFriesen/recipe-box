import java.util.List;

public interface DatabaseManagement {
  boolean equals(Object otherObject);
  void save();
  void update();
  // static List<Object> all() {};
  // static Object find(int id) {};
  void delete();
  int getId();
}
