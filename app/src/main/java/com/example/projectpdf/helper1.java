package com.example.projectpdf;

public class helper1 {
    String editname,editrollno,editemail,editpassword;

    public helper1(String editname, String editrollno, String editemail, String editpassword) {
        this.editname = editname;
        this.editrollno = editrollno;
        this.editemail = editemail;
        this.editpassword = editpassword;
    }

    public String getEditname() {
        return editname;
    }

    public void setEditname(String editname) {
        this.editname = editname;
    }

    public String getEditrollno() {
        return editrollno;
    }

    public void setEditrollno(String editrollno) {
        this.editrollno = editrollno;
    }

    public String getEditemail() {
        return editemail;
    }

    public void setEditemail(String editemail) {
        this.editemail = editemail;
    }

    public String getEditpassword() {
        return editpassword;
    }

    public void setEditpassword(String editpassword) {
        this.editpassword = editpassword;
    }
}
