package com.finanzlymobile.finanzlymobile;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

class Operation implements Parcelable {
    private static final String TAG = "==============";
    private String id;
    private String name;
    private double value;
    private int image;
    private Type type;

    private boolean paid;

    public Operation(){}

    public Operation(String name, double value, int image, Type type, boolean paid) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.image = image;
        this.type = type;
        this.paid = paid;
    }

    public Operation(String id){this.id = id;}

    protected Operation(Parcel in) {
        id = in.readString();
        name = in.readString();
        value = in.readDouble();
        image = in.readInt();
        type = Type.valueOf(in.readString());
        paid = in.readInt() != 0;
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };

    @Override
    public String toString() {
        return "Operation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", image=" + image +
                ", type=" + type +
                ", paid=" + paid +
                '}';
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }

    public boolean isPaid() { return paid; }

    public void setPaid(boolean paid) { this.paid = paid; }

    public void save(String boardId){Data.saveOperation(this, boardId);}
    public void edit(){Data.editOperation(this);}
    public void delete(String boardId){

        Log.w(TAG, "delete: " + boardId );
        Data.deleteOperation(this, boardId);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(value);
        dest.writeInt(image);
        dest.writeString(type.name());
        dest.writeInt(paid ? 1 : 0);
    }

    public enum Type {
        INCOME, EXPENSE
    }
}
