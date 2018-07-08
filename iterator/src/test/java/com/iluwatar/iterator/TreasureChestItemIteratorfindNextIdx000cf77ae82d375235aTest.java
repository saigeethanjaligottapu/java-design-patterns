package com.iluwatar.iterator;

import org.junit.Assert;
import org.junit.rules.ExpectedException;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.diffblue.deeptestutils.CompareWithFieldList;
import com.diffblue.deeptestutils.Reflector;

/** Test class */
public class TreasureChestItemIteratorfindNextIdx000cf77ae82d375235aTest {

  @org.junit.Rule
  public ExpectedException thrown = ExpectedException.none();

  /* testedClasses: com/iluwatar/iterator/TreasureChestItemIterator.java */
  /*
   * Test generated by Diffblue Deeptest.
   * This test case covers:

   * conditional line 68 branch to line 68
   * conditional line 68 branch to line 72
   * conditional line 72 branch to line 72
   * conditional line 72 branch to line 66

   * conditional line 68 branch to line 68
   * conditional line 68 branch to line 69
   */

  @Test
  public void comiluwatariteratorTreasureChestItemIteratorfindNextIdx000cf77ae82d375235a() throws Throwable {

    int retval;
    {
      /* Arrange */
      com.iluwatar.iterator.TreasureChestItemIterator param9 =
          (com.iluwatar.iterator.TreasureChestItemIterator)
          Reflector.getInstance("com.iluwatar.iterator.TreasureChestItemIterator");
      Reflector.setField(param9, "idx", -1);
      com.iluwatar.iterator.ItemType itemType =
          (com.iluwatar.iterator.ItemType) Reflector.getInstance("com.iluwatar.iterator.ItemType");
      Reflector.setField(itemType, "name", "");
      Reflector.setField(itemType, "ordinal", 0);
      Reflector.setField(param9, "type", itemType);
      com.iluwatar.iterator.TreasureChest treasureChest =
          (com.iluwatar.iterator.TreasureChest) Reflector.getInstance("com.iluwatar.iterator.TreasureChest");
      java.util.ArrayList arrayList = new java.util.ArrayList();
      com.iluwatar.iterator.Item item =
          (com.iluwatar.iterator.Item) Reflector.getInstance("com.iluwatar.iterator.Item");
      Reflector.setField(item, "name", null);
      com.iluwatar.iterator.ItemType itemType1 =
          (com.iluwatar.iterator.ItemType) Reflector.getInstance("com.iluwatar.iterator.ItemType");
      Reflector.setField(itemType1, "name", null);
      Reflector.setField(itemType1, "ordinal", 0);
      Reflector.setField(item, "type", itemType1);
      arrayList.add(item);
      Reflector.setField(treasureChest, "items", arrayList);
      Reflector.setField(param9, "chest", treasureChest);

      /* Act */
      Class<?> c = Reflector.forName("com.iluwatar.iterator.TreasureChestItemIterator");
      Method m = c.getDeclaredMethod("findNextIdx");
      m.setAccessible(true);
      retval = (int) m.invoke(param9);
    }
    {
      /* Assert result */
      Assert.assertEquals(-1, retval);
    }
  }
}
