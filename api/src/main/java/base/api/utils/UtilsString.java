package base.api.utils;

public class UtilsString {
  private UtilsString() {}
  public static boolean isBlankString(String string) {
    return string == null || string.isBlank();
  }
}
