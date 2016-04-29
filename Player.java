import java.util.HashMap;

/**
 * Abstract player class.
 * 
 * @author Mitch Parry
 * @version 2014-03-28
 * 
 */
public abstract class Player
{
    protected HashMap<State, Double> utility;
    protected HashMap<State, String> policy;
    private String name;

    /**
     * Constructor takes the name.
     * 
     * @param name
     *            the name of the player.
     */
    public Player(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Player [name=" + name + "]";
    }

    /**
     * @return the utility of the states.
     */
    public HashMap<State, Double> getU()
    {
        return utility;
    }

    /**
     * @return the policy for the states.
     */
    public HashMap<State, String> getPi()
    {
        return policy;
    }

    /**
     * Returns the desired action for the current state of the MDP.
     * 
     * @param mdp
     *            the MDP.
     * @return the desired action.
     */
    public abstract String play(MarkovDecisionProcess mdp);

}
