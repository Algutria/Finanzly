package com.finanzlymobile.finanzlymobile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Board implements Parcelable {
    private String id;
    private String name;
    private String description;
    private int image;
    private ArrayList<Operation> operations;

    public Board(){

    }

    public Board(String name, String description, int image, ArrayList<Operation> operations) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.operations = operations;
    }

    public Board(String id){this.id = id;}

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", operations=" + operations +
                '}';
    }

    protected Board(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        image = in.readInt();
        operations = in.createTypedArrayList(Operation.CREATOR);
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

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

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public void save(){Data.saveBoard(this);}
    public void edit(){Data.editBoard(this);}
    public void delete(){Data.deleteBoard(this);}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(image);
        dest.writeTypedList(operations);
    }
}
