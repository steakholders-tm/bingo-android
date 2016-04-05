package eu.steakholders.bingo.classroombingo;

public abstract class BaseTile {
    private String textField;

    public BaseTile() {

    }

    private String getTextField (){
        return textField;
    }

    private void setTextField(String textField) {
        this.textField = textField;
    }

}
