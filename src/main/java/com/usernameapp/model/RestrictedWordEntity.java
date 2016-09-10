package com.usernameapp.model;

import javax.persistence.*;

/**
 * Created by daniel.tutila on 9/9/16.
 */
@Entity
@Table(name = "restrictedWord")
public class RestrictedWordEntity {
    private int id;
    private String restrictedWord;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "restricted_word")
    public String getRestrictedWord() {
        return restrictedWord;
    }

    public void setRestrictedWord(String restrictedWord) {
        this.restrictedWord = restrictedWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestrictedWordEntity that = (RestrictedWordEntity) o;

        if (id != that.id) return false;
        if (restrictedWord != null ? !restrictedWord.equals(that.restrictedWord) : that.restrictedWord != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (restrictedWord != null ? restrictedWord.hashCode() : 0);
        return result;
    }
}
