package com.pucp.chanot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "word")
public class Word {

    private Integer id;
    private String token;
    private String lemma;
    private String posTag;
    private String subPosTag;
    private String lemmaPred;
    private String posTagPred;
    private String entityTag;
    private String subCatTag;
    private String entityTagPred;

    private List<Affix> affixs = new ArrayList<>();

    public Word() {

    }

    @XmlAttribute(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlAttribute(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlAttribute(name = "lemma")
    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    @XmlAttribute(name = "posTag")
    public String getPosTag() {
        return posTag;
    }

    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }

    @XmlAttribute(name = "subPosTag")
    public String getSubPosTag() {
        return subPosTag;
    }

    public void setSubPosTag(String subPosTag) {
        this.subPosTag = subPosTag;
    }

    @XmlElement(name = "affix")
    public List<Affix> getAffixs() {
        return affixs;
    }

    public void setAffix(List<Affix> affixs) {
        this.affixs = affixs;
    }

    @XmlAttribute(name = "entityTag")
    public String getEntityTag() {
        return entityTag;
    }

    public void setEntityTag(String entityTag) {
        this.entityTag = entityTag;
    }

    @XmlAttribute(name = "subCatTag")
    public String getSubCatTag() {
        return subCatTag;
    }

    public void setSubCatTag(String subCatTag) {
        this.subCatTag = subCatTag;
    }

    @XmlAttribute(name = "lemmaPred")
    public String getLemmaPred() {
        return lemmaPred;
    }

    public void setLemmaPred(String lemmaPred) {
        this.lemmaPred = lemmaPred;
    }

    @XmlAttribute(name = "posTagPred")
    public String getPosTagPred() {
        return posTagPred;
    }

    public void setPosTagPred(String posTagPred) {
        this.posTagPred = posTagPred;
    }

    @XmlAttribute(name = "entityTagPred")
    public String getEntityTagPred() {
        return entityTagPred;
    }

    public void setEntityTagPred(String entityTagPred) {
        this.entityTagPred = entityTagPred;
    }

}
