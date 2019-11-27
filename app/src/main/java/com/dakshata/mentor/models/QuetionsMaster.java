
package com.dakshata.mentor.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuetionsMaster implements Serializable
{

    @SerializedName("f1")
    @Expose
    private List<F1> f1 = null;
    @SerializedName("f2")
    @Expose
    private List<F2> f2 = null;
    @SerializedName("f3")
    @Expose
    private List<F3> f3 = null;
    @SerializedName("f4")
    @Expose
    private List<F4> f4 = null;
    @SerializedName("f5")
    @Expose
    private List<F5> f5 = null;
    @SerializedName("f6")
    @Expose
    private List<F6> f6 = null;
    @SerializedName("f7")
    @Expose
    private List<F7> f7 = null;
    @SerializedName("f8")
    @Expose
//    private List<F8> f8 = null;
//    @SerializedName("f9")
//    @Expose
//    private List<F9> f9 = null;
    private final static long serialVersionUID = -3899338660814844302L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public QuetionsMaster() {
    }

    /**
     * 
     * @param f6
     * @param f7
//     * @param f8
//     * @param f9
     * @param f1
     * @param f3
     * @param f2
     * @param f5
     * @param f4
     */
    public QuetionsMaster(List<F1> f1, List<F2> f2, List<F3> f3, List<F4> f4, List<F5> f5, List<F6> f6, List<F7> f7) {
        super();
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        this.f6 = f6;
        this.f7 = f7;
//        this.f8 = f8;
//        this.f9 = f9;
    }

    public List<F1> getF1() {
        return f1;
    }

    public void setF1(List<F1> f1) {
        this.f1 = f1;
    }

    public QuetionsMaster withF1(List<F1> f1) {
        this.f1 = f1;
        return this;
    }

    public List<F2> getF2() {
        return f2;
    }

    public void setF2(List<F2> f2) {
        this.f2 = f2;
    }

    public QuetionsMaster withF2(List<F2> f2) {
        this.f2 = f2;
        return this;
    }

    public List<F3> getF3() {
        return f3;
    }

    public void setF3(List<F3> f3) {
        this.f3 = f3;
    }

    public QuetionsMaster withF3(List<F3> f3) {
        this.f3 = f3;
        return this;
    }

    public List<F4> getF4() {
        return f4;
    }

    public void setF4(List<F4> f4) {
        this.f4 = f4;
    }

    public QuetionsMaster withF4(List<F4> f4) {
        this.f4 = f4;
        return this;
    }

    public List<F5> getF5() {
        return f5;
    }

    public void setF5(List<F5> f5) {
        this.f5 = f5;
    }

    public QuetionsMaster withF5(List<F5> f5) {
        this.f5 = f5;
        return this;
    }

    public List<F6> getF6() {
        return f6;
    }

    public void setF6(List<F6> f6) {
        this.f6 = f6;
    }

    public QuetionsMaster withF6(List<F6> f6) {
        this.f6 = f6;
        return this;
    }

    public List<F7> getF7() {
        return f7;
    }

    public void setF7(List<F7> f7) {
        this.f7 = f7;
    }

    public QuetionsMaster withF7(List<F7> f7) {
        this.f7 = f7;
        return this;
    }

//    public List<F8> getF8() {
//        return f8;
//    }

//    public void setF8(List<F8> f8) {
//        this.f8 = f8;
//    }

//    public QuetionsMaster withF8(List<F8> f8) {
//        this.f8 = f8;
//        return this;
//    }

//    public List<F9> getF9() {
//        return f9;
//    }
//
//    public void setF9(List<F9> f9) {
//        this.f9 = f9;
//    }

//    public QuetionsMaster withF9(List<F9> f9) {
//        this.f9 = f9;
//        return this;
//    }

}
