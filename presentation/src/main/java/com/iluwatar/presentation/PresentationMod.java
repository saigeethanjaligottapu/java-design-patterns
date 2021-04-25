package com.iluwatar.presentation;

import lombok.extern.slf4j.Slf4j;

/**
 * The class between view and albums, it is used to control the data.
 */
@Slf4j
public class PresentationMod {
  /**
   * the data of all albums that will be shown.
   */
  private final DsAlbum data;
  /**
   * the no of selected album.
   */
  private int selectedAlbumNumber;
  /**
   * the selected album.
   */
  private Album selectedAlbum;

  /**
   * Generates a set of data for testing.
   *
   * @return a instance of DsAlbum which store the data.
   */
  public static DsAlbum albumDataSet() {
    String[] titleList = {"HQ", "The Rough Dancer and Cyclical Night",
                          "The Black Light", "Symphony No.5"};
    String[] artistList = {"Roy Harper", "Astor Piazzola",
                           "The Black Light", "CBSO"};
    boolean[] isClassicalList = {false, false, false, true};
    String[] composerList = {null, null, null, "Sibelius"};

    var result = new DsAlbum();
    for (int i = 1; i <= titleList.length; i++) {
      result.addAlbums(titleList[i - 1], artistList[i - 1],
              isClassicalList[i - 1], composerList[i - 1]);
    }
    return result;
  }

  /**
   * constructor method.
   *
   * @param dataOfAlbums the data of all the albums
   */
  public PresentationMod(final DsAlbum dataOfAlbums) {
    this.data = dataOfAlbums;
    this.selectedAlbumNumber = 1;
    this.selectedAlbum = this.data.getAlbums().get(0);
  }

  /**
   * Changes the value of selectedAlbumNumber.
   *
   * @param albumNumber the number of album which is shown on the view.
   */
  public void setSelectedAlbumNumber(final int albumNumber) {
    LOGGER.info("Change select number from {} to {}",
            this.selectedAlbumNumber, albumNumber);
    this.selectedAlbumNumber = albumNumber;
    this.selectedAlbum = data.getAlbums().get(this.selectedAlbumNumber - 1);
  }

  /**
   * get the title of selected album.
   *
   * @return the tile of selected album.
   */
  public String getTitle() {
    return selectedAlbum.getTitle();
  }

  /**
   * set the title of selected album.
   *
   * @param value the title which user want to user.
   */
  public void setTitle(final String value) {
    LOGGER.info("Change album title from {} to {}",
            selectedAlbum.getTitle(), value);
    selectedAlbum.setTitle(value);
  }

  /**
   * get the artist of selected album.
   *
   * @return the artist of selected album.
   */
  public String getArtist() {
    return selectedAlbum.getArtist();
  }

  /**
   * set the name of artist.
   *
   * @param value the name want artist to be.
   */
  public void setArtist(final String value) {
    LOGGER.info("Change album artist from {} to {}",
            selectedAlbum.getArtist(), value);
    selectedAlbum.setArtist(value);
  }

  /**
   * Gets a boolean value which represents whether the album is classical.
   *
   * @return is the album classical.
   */
  public boolean getIsClassical() {
    return selectedAlbum.isClassical();
  }

  /**
   * set the isClassical of album.
   *
   * @param value is the album classical.
   */
  public void setIsClassical(final boolean value) {
    LOGGER.info("Change album isClassical from {} to {}",
            selectedAlbum.isClassical(), value);
    selectedAlbum.setClassical(value);
  }

  /**
   * get is classical of the selected album.
   *
   * @return is the album classical.
   */
  public String getComposer() {
    return selectedAlbum.isClassical() ? selectedAlbum.getComposer() : "";
  }

  /**
   * Sets the name of composer when the album is classical.
   *
   * @param value the name of composer.
   */
  public void setComposer(final String value) {
    if (selectedAlbum.isClassical()) {
      LOGGER.info("Change album composer from {} to {}",
              selectedAlbum.getComposer(), value);
      selectedAlbum.setComposer(value);
    } else {
      LOGGER.info("Composer can not be changed");
    }
  }

  /**
   * Gets a list of albums.
   *
   * @return the names of all the albums.
   */
  public String[] getAlbumList() {
    var result = new String[data.getAlbums().size()];
    for (var i = 0; i < result.length; i++) {
      result[i] = data.getAlbums().get(i).getTitle();
    }
    return result;
  }
}
