/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.mobileconnectors.amazonmobileanalytics.internal.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.amazonaws.util.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class StringUtilTest {

    @Test
    public void isNullOrEmpty_null_string() {
        assertTrue(StringUtil.isNullOrEmpty(null));
    }

    @Test
    public void isNullOrEmpty_empty_string() {
        assertTrue(StringUtil.isNullOrEmpty(""));
    }

    @Test
    public void isNullOrEmpty_blank_string() {
        assertFalse(StringUtil.isNullOrEmpty(" "));
    }

    @Test
    public void isNullOrEmpty_non_blank_string() {
        assertFalse(StringUtil.isNullOrEmpty("abcde"));
    }

    @Test
    public void isBlank_empty_string() {
        assertTrue(StringUtil.isBlank(""));
    }

    @Test
    public void isBlank_blank_string() {
        assertTrue(StringUtil.isBlank(" "));
    }

    @Test
    public void isBlank_non_blank_string() {
        assertFalse(StringUtil.isBlank("abcde"));
    }

    @Test
    public void convertStreamToString_test() throws IOException {
        String text = "soijweoirj2k3kllsfl";
        ByteArrayInputStream stream = new ByteArrayInputStream(text.getBytes(StringUtils.UTF8));

        assertEquals(text + "\n", StringUtil.convertStreamToUTF8String(stream));
    }

    @Test
    public void convertStreamToString_multiple_lines_test() throws IOException {
        String text = "soijweoirj2k3kllsfl\n2932kljldfjoi2\n";
        ByteArrayInputStream stream = new ByteArrayInputStream(text.getBytes(StringUtils.UTF8));

        assertEquals(text, StringUtil.convertStreamToUTF8String(stream));
    }

    @Test
    public void trimOrPadString_trimsString() {
        assertEquals(StringUtil.trimOrPadString("abcdefg", 7, ' '), "abcdefg");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 6, ' '), "bcdefg");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 5, ' '), "cdefg");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 4, ' '), "defg");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 3, ' '), "efg");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 2, ' '), "fg");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 1, ' '), "g");
        assertEquals(StringUtil.trimOrPadString("abcdefg", 0, ' '), "");
    }

    @Test
    public void trimOrPadString_padsString() {
        assertEquals(StringUtil.trimOrPadString("abc", 7, '_'), "____abc");
        assertEquals(StringUtil.trimOrPadString("abc", 6, '-'), "---abc");
        assertEquals(StringUtil.trimOrPadString("abc", 5, '\\'), "\\\\abc");
        assertEquals(StringUtil.trimOrPadString("abc", 4, '$'), "$abc");

        assertEquals(StringUtil.trimOrPadString("", 10, '&'), "&&&&&&&&&&");
        assertEquals(StringUtil.trimOrPadString("", 5, '"'), "\"\"\"\"\"");
        assertEquals(StringUtil.trimOrPadString("\b\b", 5, '\n'), "\n\n\n\b\b");

        int l = 100;
        String s = StringUtil.trimOrPadString("", l, '\\');
        assertEquals(s.length(), l);
        for (int i = 0; i < s.length(); i++) {
            assertEquals(s.charAt(i), '\\');
        }
    }

    @Test
    public void trimOrPadString_worksWith_invalidArgs() {
        // len < 0
        assertEquals(StringUtil.trimOrPadString("abcdefg", -1, ' '), "");
        assertEquals(StringUtil.trimOrPadString("abcdefg", -2, ' '), "");
        // null string
        assertEquals(StringUtil.trimOrPadString(null, 5, '+'), "+++++");
    }
}
