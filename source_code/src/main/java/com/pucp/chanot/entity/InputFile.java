package com.pucp.chanot.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "inputFile")
public class InputFile {

    private Integer id;
    private String name;
    private Integer sentences;
    private Integer words;
    private Integer processedWords = 0;
    private Integer time = 0;

    @XmlAttribute(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "sentences")
    public Integer getSentences() {
        return sentences;
    }

    public void setSentences(Integer sentences) {
        this.sentences = sentences;
    }

    @XmlAttribute(name = "words")
    public Integer getWords() {
        return words;
    }

    public void setWords(Integer words) {
        this.words = words;
    }

    @XmlAttribute(name = "processedWords")
    public Integer getProcessedWords() {
        return processedWords;
    }

    public void setProcessedWords(Integer processedWords) {
        this.processedWords = processedWords;
    }

    @XmlAttribute(name = "time")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
