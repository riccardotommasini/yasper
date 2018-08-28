package it.polimi.yasper.core.spe.tick;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.operators.s2r.execution.instance.Window;

public interface Ticker<E> {
    void setWa(WindowAssigner<E> wa);
    Content<E> tick(long t_e, Window w);
}
