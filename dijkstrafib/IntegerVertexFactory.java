package dijkstrafib;

/**
 *
 * @author amitskatti
 */

import org.jgrapht.*;


public class IntegerVertexFactory
    implements VertexFactory<Integer>
{

    private int counter;

    public IntegerVertexFactory()
    {
        this(0);
    }

    public IntegerVertexFactory(int oneBeforeFirstValue)
    {
        this.counter = oneBeforeFirstValue;
    }

    public Integer createVertex()
    {
        this.counter++;
        return new Integer(this.counter);
    }
}
