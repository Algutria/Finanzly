package com.finanzlymobile.finanzlymobile;

public class Board {
    private String id;
    private String name;
    private String description;
    private int image;

    public Board(){

    }

    public Board(String id, String name, String description, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Board(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Board(String id){this.id = id;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void save(){Data.saveBoard(this);}
    public void edit(){Data.editBoard(this);}
    public void delete(){Data.deleteBoard(this);}
}
