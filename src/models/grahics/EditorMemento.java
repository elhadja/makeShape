package models.grahics;

public class  EditorMemento {
    private final UIComponent toolBar;
    private final UIComponent whiteBoard;

    public EditorMemento(UIComponent toolBar, UIComponent whiteBoard) {
        this.toolBar = (UIComponent) toolBar.Clone();
        this.whiteBoard = (UIComponent) whiteBoard.Clone();
    }

    public UIComponent getToolBar() {
        return (UIComponent)toolBar.Clone();
    }

    public UIComponent getWhiteBoard() {
        return (UIComponent)whiteBoard.Clone();
    }
}
