package ua.knu.gui.components;

import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import ua.knu.mi.lexer.Lexeme;


public class LexemeText extends Text {
    public static final String SELECTED="selected";
    public static final String COMMON_CLASS="lexeme";
    private Lexeme lexeme;
    public LexemeText(Lexeme lexeme) {
        super(lexeme.value());
        this.lexeme=lexeme;
        getStyleClass().add(lexeme.className());
        getStyleClass().add(COMMON_CLASS);
    }

    public int getLexemeId() {
        return lexeme.id();
    }

    public boolean isInRange(int first, int last){
        return getLexemeId()>=first && getLexemeId()<=last;
    }

    public void updateWithSelectionRange(int first, int last){
        if (isInRange(first,last)){
            if (!getStyleClass().contains(SELECTED))
                getStyleClass().add(SELECTED);
        } else {
            getStyleClass().remove(SELECTED);
        }
    }
}
