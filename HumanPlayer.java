import java.util.List;
import java.util.Scanner;

/**
 * A sample human player so that the game can be played manually.
 * 
 * @author Mitch Parry
 * @version 2014-03-28
 * 
 */
public class HumanPlayer extends Player
{

    public static Scanner scan = new Scanner(System.in);

    /**
     * Constructor simply takes a name for the player.
     * 
     * @param name
     *            the name of the player.
     */
    public HumanPlayer(String name)
    {
        super(name);
    }

    /**
     * Returns the desired action for the current state of the MDP.
     * 
     * @param mdp
     *            The Markov Decision Process
     * @return the next desired action.
     */
    public String play(MarkovDecisionProcess mdp)
    {
        // return the index of the action that you want to take in the current
        // state
        List<String> actions = mdp.getActions();
        int index = -1;
        String command = null;
        GridWorld.display(mdp.getStates(), mdp.getCurrent());
        while (index < 0)
        {
            System.out.println(actions);
            System.out.println("Enter choice: ");
            command = scan.next().toUpperCase();
            index = actions.indexOf(command);
            if (index < 0 && command.toLowerCase().equals("quit"))
            {
                break;
            }
        }
        return command;
    }
}
