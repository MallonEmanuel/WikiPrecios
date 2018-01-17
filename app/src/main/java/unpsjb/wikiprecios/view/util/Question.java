package unpsjb.wikiprecios.view.util;

import android.content.Context;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase Realiza preguntas al usuario.
 */
public class Question {

    Coordinator coordinator;
    Context context;

    public Question(Coordinator coordinator, Context context) {
        this.coordinator = coordinator;
        this.context = context;
    }
}
