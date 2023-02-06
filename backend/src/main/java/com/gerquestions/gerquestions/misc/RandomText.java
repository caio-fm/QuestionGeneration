package com.gerquestions.gerquestions.misc;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomText {

    LoremIpsum loremIpsum = new LoremIpsum();

    public String getText(int numWords) {
        String lorem = loremIpsum.getWords(numWords);
        String[] words = lorem.split(" ");
        List<String> list = Arrays.asList(words);
        Collections.shuffle(list);
        return StringUtils.collectionToDelimitedString(list, " ");
    }

}
