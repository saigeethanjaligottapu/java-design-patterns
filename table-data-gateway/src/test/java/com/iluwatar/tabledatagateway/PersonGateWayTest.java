package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type Person gate way test.
 * CS304 Issue link: github.com/iluwatar/java-design-patterns/issues/1318
 */
class PersonGateWayTest {
  /**
   * Test find.
   */
  @Test
void testFind() {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.find(0).getFirstName());
    assertEquals("Romanoff", personGateWay.find(0).getLastName());
    assertEquals("F", personGateWay.find(0).getGender());
    assertEquals(28, personGateWay.find(0).getAge());
  }

  /**
   * Test find by first name.
   */
  @Test
void testFindByFirstName() {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.findByFirstName("Natasha").get(0).getFirstName());
    assertEquals("Romanoff", personGateWay.findByFirstName("Natasha").get(0).getLastName());
    assertEquals("F", personGateWay.findByFirstName("Natasha").get(0).getGender());
    assertEquals(28, personGateWay.findByFirstName("Natasha").get(0).getAge());
  }

  /**
   * Test update.
   */
  @Test
void testUpdate() {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Tony", "Stark", "M", 36);
    personGateWay.update(0, "Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.find(0).getFirstName());
    assertEquals("Romanoff", personGateWay.find(0).getLastName());
    assertEquals("F", personGateWay.find(0).getGender());
    assertEquals(28, personGateWay.find(0).getAge());
  }

  /**
   * Test insert.
   */
  @Test
void testInsert() {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    assertEquals("Natasha", personGateWay.find(0).getFirstName());
    assertEquals("Romanoff", personGateWay.find(0).getLastName());
    assertEquals("F", personGateWay.find(0).getGender());
    assertEquals(28, personGateWay.find(0).getAge());
  }

  /**
   * Test delete.
   */
  @Test
void testDelete() {
    var personGateWay = new PersonGateWay();
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    personGateWay.delete(0);
    assertNull(personGateWay.find(0));
  }

}
