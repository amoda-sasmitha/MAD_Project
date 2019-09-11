package Models;

public class CategoryModel {

    private int ID;
    private String Name;
    private String Description;
    private String Type;
    private String Icon;

    //overloaded constructor
    public CategoryModel(int ID, String name, String description, String type, String icon) {
        this.ID = ID;
        Name = name;
        Description = description;
        Type = type;
        Icon = icon;
    }

    public CategoryModel(String name, String description, String type, String icon) {
        Name = name;
        Description = description;
        Type = type;
        Icon = icon;
    }

    public CategoryModel(String name , String icon) {
        Name = name;
        Icon = icon;

    }
    public CategoryModel() { }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}
