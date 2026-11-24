package coach.zander.chronicler.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** Pins how model objects compare: activities by id, objects by name, never across types. */
class ModelIdentityTest {

  private static Endeavor endeavor(Integer id, String title) {
    Endeavor endeavor = new Endeavor();
    endeavor.setId(id);
    endeavor.setTitle(title);
    return endeavor;
  }

  private static Location location(String name) {
    Location location = new Location();
    location.setName(name);
    return location;
  }

  @Test
  void activitiesAreEqualByIdAlone() {
    assertThat(endeavor(1, "Der verlorene Zehnt"))
        .isEqualTo(endeavor(1, "Anderer Titel"))
        .hasSameHashCodeAs(endeavor(1, "Anderer Titel"))
        .isNotEqualTo(endeavor(2, "Der verlorene Zehnt"))
        .isNotEqualTo(endeavor(null, "Der verlorene Zehnt"));
    assertThat(endeavor(null, "a")).isEqualTo(endeavor(null, "b"));
  }

  @Test
  void activitiesOfDifferentTypesAreNeverEqual() {
    Event event = new Event();
    event.setId(1);
    assertThat(endeavor(1, "Der verlorene Zehnt")).isNotEqualTo(event).isNotEqualTo(null);
  }

  @Test
  void objectsAreEqualByNameAlone() {
    Location withDescription = location("Nebelmoor");
    withDescription.setDescription("Ein Moor.");
    withDescription.setWikiPage("Moor");
    assertThat(location("Nebelmoor"))
        .isEqualTo(withDescription)
        .hasSameHashCodeAs(withDescription)
        .isNotEqualTo(location("Zehntscheuer"))
        .isNotEqualTo(location(null));
  }

  @Test
  void objectsOfDifferentTypesAreNeverEqual() {
    Creature creature = new Creature();
    creature.setName("Nebelmoor");
    assertThat(location("Nebelmoor")).isNotEqualTo(creature).isNotEqualTo(null);
  }

  @Test
  void toStringNamesTypeAndKey() {
    assertThat(endeavor(1, "Der verlorene Zehnt"))
        .hasToString("Endeavor[title=Der verlorene Zehnt]");
    assertThat(location("Nebelmoor")).hasToString("Location[name=Nebelmoor]");
  }
}
