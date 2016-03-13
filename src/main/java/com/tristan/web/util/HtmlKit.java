 package com.tristan.web.util;

import com.jfinal.kit.StrKit;


/**
 * HTML解析基本函数
 */
public final class HtmlKit {
  public static String SINGLEQUOT = "'";
  public static String DOUBLEQUOT = "\"";
  public static String BLACK = " ";
  public static String SKASH = "\\";
  public static String SBlACKETL = "<";
  public static String SBlACKETR = ">";
  public static String HREF = "href=";
  public static String URL1 = "URL=";
  public static String URL2 = "url=";
  public static String SRC2 = "src=";
  public static String SRC1 = "SRC=";
  public static String NBSP = "&nbsp;";
  public static String BR = "<br>";


  /**
   * 删除HTML标签
   *
   * @param content String
   * @return String
   */
  public static String trimHtmlTag(String content) {
    if (content == null)
      return null;

    content = content.replaceAll("&nbsp;"," ");
    content = content.replaceAll("&amp;"," ");

    return trimByStartAndEnd(content, SBlACKETL, SBlACKETR);
  }
  /**
   * 从内容中删去以strStart开头,strEnd结尾的部分
   *
   * @param content String
   * @param start String
   * @param end String
   * @return String
   */
  public static String trimByStartAndEnd(String sText, String start, String end) {
    if (StrKit.isBlank(sText))
      return sText;

    int iStart = -1, iEnd = -1;
    try {
      while (true) {
        iStart = sText.indexOf(start);
        iEnd = sText.indexOf(end, iStart +start.length() + 1);

        if (iStart == -1 || iEnd == -1)
          break;
        else
          sText = sText.substring(0, iStart) + sText.substring(iEnd + end.length());
      }
      return sText;
    } catch (Exception e) {
      throw new StringIndexOutOfBoundsException("content:" + sText + " start:" + start + " strEnd:" + end + " iStart:" +
                                                iStart +
                                                " iEnd:" + iEnd);
    }
  }


}


