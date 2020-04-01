package ui;

public class CharacterNameInputScreen {


    private boolean nameRetrieved;
    private GameConsoleInterface mainInterface;


    public CharacterNameInputScreen(GameConsoleInterface mainInterface) {
        nameRetrieved = false;
        this.mainInterface = mainInterface;

    }

    //getter
    public boolean isNameRetrieved() {
        return nameRetrieved;
    }





}
