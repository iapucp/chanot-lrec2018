package com.pucp.chanot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "corpus")
public class Corpus {

    private List<Sentence> sentences = new ArrayList<>();
    private Integer lastSentence;

    public Corpus() {

    }

    @XmlElement(name = "sentence")
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    @XmlElement(name = "lastSentence")
    public Integer getLastSentence() {
        return lastSentence;
    }

    public void setLastSentence(Integer lastSentence) {
        this.lastSentence = lastSentence;
    }

}
