import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void tag_instantiates_correctly() {
    Tag testTag = new Tag("yummy");
    assertTrue(testTag instanceof Tag);
  }

  @Test
  public void getName_tagInstantiatesWithName_String() {
    Tag testTag = new Tag("yummy");
    assertEquals("yummy", testTag.getName());
  }

  @Test
  public void equals_returnsTrueIfTagsAreTheSame_true() {
    Tag firstTag = new Tag("yummy");
    Tag secondTag = new Tag("yummy");
    assertTrue(firstTag.equals(secondTag));
  }

  @Test
  public void save_returnsTrueIfSavedTagsAreEqual_true() {
    Tag testTag = new Tag("yummy");
    testTag.save();
    assertTrue(Tag.all().get(0).equals(testTag));
  }

  @Test
  public void save_assignsIdToTag() {
    Tag testTag = new Tag("yummy");
    testTag.save();
    Tag savedTag = Tag.all().get(0);
    assertEquals(testTag.getId(), savedTag.getId());
  }

  @Test
  public void all_returnsAllInstancesOfTag_true() {
    Tag firstTag = new Tag("yummy");
    firstTag.save();
    Tag secondTag = new Tag("gross");
    secondTag.save();
    assertEquals(true, Tag.all().get(0).equals(firstTag));
    assertEquals(true, Tag.all().get(1).equals(secondTag));
  }

  @Test
  public void find_returnsTagWithSameId_secondTag() {
    Tag firstTag = new Tag("yummy");
    firstTag.save();
    Tag secondTag = new Tag("gross");
    secondTag.save();
    assertEquals(Tag.find(secondTag.getId()), secondTag);
  }
}
